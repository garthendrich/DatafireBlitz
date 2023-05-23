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

    private Player user = new Player(400, 0, 'A');
    private ArrayList<Player> otherPlayers = new ArrayList<Player>();

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
        user.stopHorizontalMovement();

        if (keyInputs.moveLeft && !keyInputs.moveRight) {
            user.moveLeft();
        } else if (keyInputs.moveRight && !keyInputs.moveLeft) {
            user.moveRight();
        }

        if (keyInputs.moveUp) {
            user.jump();
        } else if (keyInputs.moveDown) {
            user.moveDown();
        }

        if (user.getPosY() >= 700) {
            user.respawn();
        }

        user.updatePosition(platforms);
    }

    private void spawnPlayerBullets() {
        if (keyInputs.bulletLeft || keyInputs.bulletRight) {
            Bullet bullet = user.fireBullet();

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
        players.add(user);

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
        entities.add(user);
        entities.addAll(platforms);
        entities.addAll(bullets);

        gamePanel.setEntities(entities);
        gamePanel.repaint();
    }
}
