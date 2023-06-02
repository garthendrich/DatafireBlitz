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

    private Player player = new Player(400, 0, 'A');
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

    void start() {
        gameThread.start();
        gamePanel.addKeyListener(keyInputs);
        gamePanel.requestFocusInWindow();
    }

    @Override
    public void run() {
        double currentTime = System.nanoTime();
        double nextFrameNanoSeconds = currentTime + NANO_SECONDS_PER_FRAME;

        while (true) {
            if (currentTime >= nextFrameNanoSeconds) {
                updatePlayerMovement();

                spawnPlayerBullets();
                updateBulletMovement();

                manageBulletCollision();

                updateCanvas();

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
        } else if (keyInputs.moveDown) {
            player.moveDown();
        }

        if (player.getPosY() >= 700) {
            player.respawn();
        }

        player.updatePosition(platforms);
    }

    private void spawnPlayerBullets() {
        if (keyInputs.bulletLeft || keyInputs.bulletRight) {
            Bullet bullet = player.fireBullet();

            if (bullet == null) {
                return;
            }

            if (keyInputs.bulletLeft) {
                bullet.moveLeft();
            } else if (keyInputs.bulletRight) {
                bullet.moveRight();
            }

            bullets.add(bullet);
        }
    }

    private void updateBulletMovement() {
        for (Bullet bullet : bullets) {
            bullet.updatePosition();
        }
    }

    private void manageBulletCollision() {
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player);

        ArrayList<Bullet> collidedBullets = new ArrayList<Bullet>();
        for (Bullet bullet : bullets) {
            for (Player player : players) {
                if (bullet.isCollidingWith(player) && bullet.getTeam() != player.getTeam()) {
                    player.knockback(bullet.getImpact());
                    collidedBullets.add(bullet);
                    break;
                }
            }
        }
        bullets.removeAll(collidedBullets);
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
