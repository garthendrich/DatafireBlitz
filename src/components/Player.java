package components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.GameChat;
import main.Lobby;

public class Player extends MovableEntity {
    static int WIDTH = 48;
    static int HEIGHT = 48;
    private static int MOVEMENT_SPEED = 256;
    private int JUMP_HEIGHT = 720;
    private double GRAVITY = 16;
    private double BPS = 4.0;
    private double SECONDS_PER_BULLET = 1.0 / BPS;
    private double KNOCKBACK_FRICTION = 0.1;
    private static final double DAMAGE_AMPLIFIER_PERCENTAGE = 0.15;

    private int userId;
    private String userName;
    private char userTeam;
    private boolean onPlatform = false;
    private boolean canJump = true;
    private boolean willJump = false;
    private boolean willDrop = false;
    private boolean willFireBullet = false;
    private double nextBulletFireSeconds = 0.0;
    private Bullet.Direction nextBulletDirection = Bullet.Direction.right;
    private double knockback;
    private int damageTaken;
    private int lives = 3;

    Image player1 = new ImageIcon("src/assets/player1.gif").getImage();
    Image player2 = new ImageIcon("src/assets/player2.gif").getImage();
    Image player3 = new ImageIcon("src/assets/player3.gif").getImage();
    Image player4 = new ImageIcon("src/assets/player4.gif").getImage();

    private Image[] playerGifs = { player1, player2, player3, player4 };
    private Image playerGif;

    public Player(int userId, String userName, char userTeam, double x, double y, int playerGifIndex) {
        super(x, y, WIDTH, HEIGHT, MOVEMENT_SPEED);

        this.userId = userId;
        this.userName = userName;
        this.userTeam = userTeam;

        playerGif = playerGifs[playerGifIndex];
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        nextBulletDirection = Bullet.Direction.left;
    }

    @Override
    public void moveRight() {
        super.moveRight();
        nextBulletDirection = Bullet.Direction.right;
    }

    public void jumps() {
        willJump = true;
    }

    public void drops() {
        willDrop = true;
    }

    public void stopVerticalMovement() {
        willJump = false;
        willDrop = false;
    }

    private void jump() {
        if (canJump) {
            dy = -JUMP_HEIGHT;
            canJump = false;
        }
    }

    private void drop() {
        if (onPlatform) {
            onPlatform = false;
            this.y++;
        }
    }

    public void startFiringBullets() {
        willFireBullet = true;
    }

    public void stopFiringBullets() {
        willFireBullet = false;
    }

    public void updatePosition(ArrayList<Entity> platforms) {
        if (willJump) {
            jump();
        }

        if (willDrop) {
            drop();
        }

        manageGravity(platforms);
        manageKnockback();

        super.updatePosition();
    }

    public Bullet fireBullet() {
        if (!willFireBullet) {
            return null;
        }

        double currentTimeSeconds = System.nanoTime() / 1_000_000_000.0;

        if (currentTimeSeconds >= nextBulletFireSeconds) {
            nextBulletFireSeconds = currentTimeSeconds + SECONDS_PER_BULLET;
            Bullet bullet = new Bullet(this.x, this.y, this.userTeam);

            if (nextBulletDirection == Bullet.Direction.right) {
                bullet.moveRight();
            } else {
                bullet.moveLeft();
            }

            return bullet;
        }

        return null;
    }

    private void manageGravity(ArrayList<Entity> platforms) {
        boolean willApplyGravity = true;
        onPlatform = false;

        for (Entity platform : platforms) {
            if (willStandOn(platform)) {
                y = platform.y - height;
                dy = 0;
                willApplyGravity = false;
                canJump = true;
                onPlatform = true;
            }
        }

        if (willApplyGravity) {
            dy += GRAVITY;
        }
    }

    private void manageKnockback() {
        x += knockback;

        if (knockback < 0 && knockback < -KNOCKBACK_FRICTION) {
            knockback += KNOCKBACK_FRICTION;
        } else if (knockback > 0 && knockback > KNOCKBACK_FRICTION) {
            knockback -= KNOCKBACK_FRICTION;
        } else {
            knockback = 0;
        }
    }

    public boolean willStandOn(Entity entity) {
        return isAbove(entity) && !isCollidingWith(entity) && willCollideWith(entity);
    }

    public boolean willCollideWith(Entity anotherEntity) {
        double[] nextPosition = getNextPosition();
        Rectangle2D.Double entityRect = new Rectangle2D.Double(nextPosition[0] + 1, nextPosition[1] + 1, width, height);
        Rectangle2D.Double anotherEntityRect = new Rectangle2D.Double(anotherEntity.x, anotherEntity.y,
                anotherEntity.width,
                anotherEntity.height);

        return entityRect.intersects(anotherEntityRect);
    }

    private boolean isAbove(Entity entity) {
        return y + height <= entity.y;
    }

    public void hitBy(Bullet bullet) {
        int bulletDamage = bullet.getDamage();
        int impact = bullet.getImpact();

        damageTaken += bulletDamage + (damageTaken * DAMAGE_AMPLIFIER_PERCENTAGE);
        knockback += impact + (impact * (damageTaken / 100.0));
    }

    public void respawn() {
        x = (Lobby.WINDOW_WIDTH - GameChat.WIDTH - width) / 2;
        y = -height;
        dx = 0;
        dy = 0;
        damageTaken = 0;
    }

    public int getUserId() {
        return userId;
    }

    public char getUserTeam() {
        return userTeam;
    }

    public void loseLife(){
        this.lives--;
    }

    public boolean hasLife(){
        return this.lives > 0;

    @Override
    public void draw(Graphics graphics) {
        if (nextBulletDirection == Bullet.Direction.right) {
            graphics.drawImage(playerGif, (int) x, (int) y - 2, width, height, null);
        } else {
            graphics.drawImage(playerGif, (int) x + width, (int) y - 2, -width, height, null);
        }
    }
}
