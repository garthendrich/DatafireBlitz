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
        if(keyInputs.bulletLeft || keyInputs.bulletRight){

            Bullet b = new Bullet(player.getPosX(), player.getPosY());
            if(keyInputs.bulletLeft){
                b.moveLeft();
            }else if(keyInputs.bulletRight){
                b.moveRight();
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

        int timer = 20;

        while (true) {
            if (currentTime >= nextFrameNanoSeconds) {
                updatePlayerMovement();

                if (timer == 0){
                    createBullet();
                    timer = 20;
                }

                updateBulletMovement();
                updateCanvas();

                timer--;
                nextFrameNanoSeconds += NANO_SECONDS_PER_FRAME;
            }

            currentTime = System.nanoTime();
        }
    }

    private void updatePlayerMovement() {
        player.stopHorizontalMovement();

        if (keyInputs.moveLeft && !keyInputs.moveRight) {
            player.moveLeft();
        } else if (keyInputs.moveRight && !keyInputs.moveLeft) {
            player.moveRight();
        }

        if (keyInputs.moveUp) {
            player.jump();
        } else if(keyInputs.moveDown){
            player.moveDown();
        }

        player.updatePosition(platforms);
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
