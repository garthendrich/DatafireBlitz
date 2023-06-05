package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

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

    static BufferedImage TILE_MAP;
    static BufferedImage buffer;

    static int TILE_SIZE = 18;
    static int TILE_MAP_WIDTH = 20;

    static int GAME_WIDTH = 40;
    static int GAME_HEIGHT = 24;

    static int[][] LEVEL_DATA = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 153, 154, 155, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 
        {0, 0, 0, 153, 154, 155, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 153, 155, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 153, 155, 0, 0, 0, 0, 0, 0}, 
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 101, 102, 102, 103, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 121, 122, 122, 123, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 17, 18, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 101, 102, 122, 122, 122, 122, 102, 102, 103, 0, 0, 0},
        {0, 0, 17, 19, 38, 39, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 121, 122, 122, 122, 122, 122, 122, 122, 123, 0, 0, 0},
        {0, 0, 57, 59, 38, 77, 78, 79, 0, 0, 0, 0, 0, 0, 0, 101, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 122, 122, 122, 122, 122, 122, 122, 122, 123, 0, 0, 0},
        {0, 0, 0, 57, 97, 59, 0, 0, 0, 0, 0, 0, 0, 0, 0, 121, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 123, 0, 0, 0},
        {0, 0, 0, 0, 137, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 121, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 123, 0, 0, 0}, 
        {0, 21, 22, 22, 22, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 121, 122, 122, 122, 122, 122, 122, 101, 102, 102, 102, 102, 102, 102, 102, 103, 122, 122, 122, 122, 122, 123, 0, 0, 0},
        {0, 121, 122, 122, 122, 123, 0, 0, 0, 0, 0, 0, 0, 0, 0, 121, 122, 122, 122, 122, 122, 122, 121, 122, 122, 122, 122, 122, 122, 122, 123, 122, 122, 122, 122, 122, 123, 0, 0, 0},
        {0, 121, 122, 122, 122, 122, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 23, 122, 122, 121, 122, 122, 122, 122, 122, 122, 122, 123, 122, 122, 122, 122, 122, 123, 0, 0, 0},
        {0, 121, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 122, 123, 122, 122, 121, 122, 122, 122, 122, 122, 122, 122, 123, 122, 122, 122, 122, 122, 123, 0, 0, 0},
    };

    static Image player1 = new ImageIcon("src/assets/player1.gif").getImage();

    public GameState() {
        createPlatforms();
    }

    public static void loadTileMap() {
        try {
            TILE_MAP = ImageIO.read(new File("src/assets/tiles_packed.png"));
        } catch (Exception e) {
            
        }
    }

    public static int getXComponent(int num){
        return num % TILE_MAP_WIDTH;
    }

    public static int getYComponent(int num){
        return num / TILE_MAP_WIDTH;
    }

    public static void drawTiles(Graphics graphics) {
        loadTileMap();
        int tileTypeX, tileTypeY, mapValue;

        for(int i=0; i < GAME_WIDTH; i++){
            for(int j=0; j< GAME_HEIGHT; j++){
                mapValue = LEVEL_DATA[j][i];
                
                if(mapValue == 0) continue;

                tileTypeX = getXComponent(mapValue);
                tileTypeY = getYComponent(mapValue);

                // System.out.println(tileTypeX + " " + tileTypeY);
                
                buffer = TILE_MAP.getSubimage(TILE_SIZE * tileTypeX, TILE_SIZE * tileTypeY, TILE_SIZE, TILE_SIZE);
                graphics.drawImage(buffer, TILE_SIZE * (i), TILE_SIZE * (j), null);
            }
        }
    }

    private void createPlatforms() {
        platforms.add(new Entity(TILE_SIZE*3, TILE_SIZE*8, TILE_SIZE*3, TILE_SIZE));
        platforms.add(new Entity(TILE_SIZE*18, TILE_SIZE*7, TILE_SIZE*3, TILE_SIZE));
        platforms.add(new Entity(TILE_SIZE*32, TILE_SIZE*9, TILE_SIZE*2, TILE_SIZE));
        platforms.add(new Entity(TILE_SIZE*14, TILE_SIZE*9, TILE_SIZE*2, TILE_SIZE));
        platforms.add(new Entity(TILE_SIZE*15, TILE_SIZE*17, TILE_SIZE*13, TILE_SIZE));
        platforms.add(new Entity(TILE_SIZE*1, TILE_SIZE*20, TILE_SIZE*5, TILE_SIZE));
        platforms.add(new Entity(TILE_SIZE*22, TILE_SIZE*20, TILE_SIZE*9, TILE_SIZE));
        platforms.add(new Entity(TILE_SIZE*6, TILE_SIZE*22, TILE_SIZE*14, TILE_SIZE));
        platforms.add(new Entity(TILE_SIZE*28, TILE_SIZE*15, TILE_SIZE*2, TILE_SIZE));
        platforms.add(new Entity(TILE_SIZE*34, TILE_SIZE*15, TILE_SIZE*3, TILE_SIZE));
        platforms.add(new Entity(TILE_SIZE*30, TILE_SIZE*13, TILE_SIZE*4, TILE_SIZE));
    }

    public void createPlayer(int userId, String userName, char userTeam) {
        players.add(new Player(userId, userName, userTeam, 400, 0));
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

    void removeOffscreenBullets() {
        ArrayList<Bullet> offscreenBullets = new ArrayList<Bullet>();
        for (Bullet bullet : bullets) {
            if (bullet.isOutsideWindow()) {
                offscreenBullets.add(bullet);
            }
        }
        bullets.removeAll(offscreenBullets);
    }

    void manageBulletCollisions() {
        ArrayList<Bullet> collidedBullets = new ArrayList<Bullet>();
        for (Bullet bullet : bullets) {
            for (Player player : players) {
                if (bullet.isCollidingWith(player) && bullet.getTeam() != player.getUserTeam()) {
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
        entities.addAll(platforms);
        entities.addAll(bullets);
        entities.addAll(players);

        return entities;
    }
}
