package main;

import java.util.ArrayList;

import components.Bullet;
import components.Entity;
import components.Player;
import network.datatypes.PlayerMovementData;

public class GameState {
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Entity> platforms = new ArrayList<Entity>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    public GameState() {
        createPlatforms();
    }

    private void createPlatforms() {
        platforms.add(new Entity(256, 256, 256, 64));
        platforms.add(new Entity(128, 503 - 96, 721 - 256, 32));
    }

    public void createPlayer(int userId, String userName) {
        players.add(new Player(userId, userName, 400, 0));
    }

    void setVaryingTeams() {
        char nextTeam = 'A';
        for (Player player : players) {
            player.setTeam(nextTeam);
            nextTeam++;
        }
    }

    public Player findPlayer(int userId) {
        for (Player player : players) {
            if (player.getUserId() == userId) {
                return player;
            }
        }
        return null;
    }

    public void movePlayer(PlayerMovementData userMovementData) {
        int userId = userMovementData.getUserId();
        int x = userMovementData.getX();
        int y = userMovementData.getY();

        Player player = findPlayer(userId);

        player.setPosition(x, y);

        PlayerMovementData.Direction direction = userMovementData.getDirection();

        if (direction == PlayerMovementData.Direction.left) {
            player.moveLeft();
        } else if (direction == PlayerMovementData.Direction.right) {
            player.moveRight();
        } else if (direction == PlayerMovementData.Direction.stopHorizontal) {
            player.stopHorizontalMovement();
        }

        if (direction == PlayerMovementData.Direction.up) {
            player.jumps();
        } else if (direction == PlayerMovementData.Direction.down) {
            player.drops();
        } else if (direction == PlayerMovementData.Direction.stopHorizontal) {
            player.stopVerticalMovement();
        }
    }

    void updateEntityPositions() {
        for (Player player : players) {
            player.updatePosition(platforms);
        }

        for (Bullet bullet : bullets) {
            bullet.updatePosition();
        }
    }

    // void spawnPlayerBullets() {
    // if (keyInputs.bulletLeft || keyInputs.bulletRight) {
    // Bullet bullet = player.fireBullet();

    // if (bullet == null) {
    // return;
    // }

    // if (keyInputs.bulletLeft) {
    // bullet.moveLeft();
    // } else if (keyInputs.bulletRight) {
    // bullet.moveRight();
    // }

    // bullets.add(bullet);
    // }
    // }

    void manageBulletCollision() {
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

    void respawnDeadPlayers() {
        for (Player player : players) {
            if (player.getY() >= 700) {
                player.respawn();
            }
        }
    }

    ArrayList<Entity> getEntities() {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        entities.addAll(players);
        entities.addAll(platforms);
        entities.addAll(bullets);

        return entities;
    }
}
