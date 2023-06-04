package main;

import java.util.ArrayList;

import components.Bullet;
import components.Entity;
import components.Player;
import network.datatypes.ToggleFireData;
import network.datatypes.MovementData;
import network.datatypes.PositionData;

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

    public void updatePlayerPosition(PositionData positionData) {
        int userId = positionData.getUserId();
        Player player = findPlayer(userId);

        int x = positionData.getPlayerX();
        int y = positionData.getPlayerY();
        player.setPosition(x, y);
    }

    public void movePlayer(MovementData movementData) {
        int userId = movementData.getUserId();
        Player player = findPlayer(userId);

        MovementData.Movement direction = movementData.getDirection();

        if (direction == MovementData.Movement.left) {
            player.moveLeft();
        } else if (direction == MovementData.Movement.right) {
            player.moveRight();
        } else if (direction == MovementData.Movement.stopHorizontal) {
            player.stopHorizontalMovement();
        }

        if (direction == MovementData.Movement.jump) {
            player.jumps();
        } else if (direction == MovementData.Movement.drop) {
            player.drops();
        } else if (direction == MovementData.Movement.stopVertical) {
            player.stopVerticalMovement();
        }
    }

    public void toggleFire(ToggleFireData toggleFireData) {
        int userId = toggleFireData.getUserId();
        Player player = findPlayer(userId);

        ToggleFireData.Status status = toggleFireData.getStatus();
        if (status == ToggleFireData.Status.start) {
            player.startFiringBullets();
        } else if (status == ToggleFireData.Status.stop) {
            player.stopFiringBullets();
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

    void spawnBullets() {
        for (Player player : players) {
            Bullet bullet = player.fireBullet();
            if (bullet != null) {
                bullets.add(bullet);
            }
        }
    }

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

    void respawnKnockedOutPlayers() {
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
