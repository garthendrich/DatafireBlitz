package main;

import java.util.ArrayList;

import components.Entity;
import components.Player;
import components.Bullet;

public class GameLoop implements Runnable {
    private int FPS = 120;
    private double NANO_SECONDS_PER_FRAME = 1_000_000_000.0 / FPS;

    private GamePanel gamePanel;
    private Thread gameThread;
    private KeyInputs keyInputs = new KeyInputs();

    private Player player = new Player(400, 0);
    private ArrayList<Entity> platforms = new ArrayList<Entity>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>(); 

    GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        gameThread = new Thread(this);

        createPlatforms();
    }

    private void createPlatforms() {
        platforms.add(new Entity(256, 256, 256, 64));
        platforms.add(new Entity(128, gamePanel.getHeight() - 96, gamePanel.getWidth() - 256, 32));
    }

    private void createBullet(){
        if(keyInputs.bulletDown || keyInputs.bulletLeft || keyInputs.bulletRight || keyInputs.bulletUp){

            Bullet b = new Bullet(player.getX(), player.getY());
            if(keyInputs.bulletDown){
                b.moveDown();
            }else if(keyInputs.bulletLeft){
                b.moveLeft();
            }else if(keyInputs.bulletRight){
                b.moveRight();
            }else if(keyInputs.bulletUp){
                b.moveUp();
            }
            bullets.add(b);

        }
    }

    void start() {
        gameThread.start();
        gamePanel.addKeyListener(keyInputs);
        gamePanel.requestFocusInWindow();
    }

    @Override
    public void run(){
        double currentTime = System.nanoTime();
        double nextFrameNanoSeconds = currentTime + NANO_SECONDS_PER_FRAME;

        double nextBulletInterval = 4000000;

        while (true) {
            if (currentTime >= nextFrameNanoSeconds) {
                updatePlayerMovement();

                if (nextBulletInterval <= 0){
                    createBullet();
                    nextBulletInterval = 4000000;
                }
                
                updateBulletMovement();
                
                updateCanvas();

                nextBulletInterval -= NANO_SECONDS_PER_FRAME;
                nextFrameNanoSeconds += NANO_SECONDS_PER_FRAME;
            }

            currentTime = System.nanoTime();
        }
    }

    private void updatePlayerMovement() {
        boolean down = false;
        
        player.stopHorizontalMovement();

        if (keyInputs.moveLeft && !keyInputs.moveRight) {
            player.moveLeft();
        } else if (keyInputs.moveRight && !keyInputs.moveLeft) {
            player.moveRight();
        }

        if (keyInputs.moveUp) {
            player.jump();
        } else if(keyInputs.moveDown){
            down = true;
        }

        player.updatePosition(platforms, down);
    }

    private void updateBulletMovement(){
        for (Bullet b: bullets){
            b.updatePosition();
        }
    }

    private void updateCanvas() {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        entities.add(player);
        entities.addAll(platforms);
        entities.addAll(bullets);

        gamePanel.setEntities(entities);
        gamePanel.repaint();
    }
}
