package components;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Player extends MovableEntity {
    static int WIDTH = 48;
    static int HEIGHT = 48;
    private static int MOVEMENT_SPEED = 3;
    private int JUMP_HEIGHT = 8;
    private double GRAVITY = 0.16;
    private double BPS = 4.0;
    private double SECONDS_PER_BULLET = 1.0 / BPS;
    private double KNOCKBACK_FRICTION = 0.1;
    private int lives = 3;

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

    public Player(int userId, String userName, char userTeam, int x, int y) {
        super(x, y, WIDTH, HEIGHT, MOVEMENT_SPEED);

        this.userId = userId;
        this.userName = userName;
        this.userTeam = userTeam;
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
                // y = platform.y - height; // remove subtle bounce
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
        Rectangle2D.Double entityRect = new Rectangle2D.Double(x + dx, y + dy, width, height);
        Rectangle anotherEntityRect = new Rectangle(anotherEntity.x, anotherEntity.y, anotherEntity.width,
                anotherEntity.height);

        return entityRect.intersects(anotherEntityRect);
    }

    private boolean isAbove(Entity entity) {
        return y + height <= entity.y;
    }

    public void knockback(int magnitude) {
        this.knockback += magnitude;
    }

    public int getUserId() {
        return userId;
    }

    public char getUserTeam() {
        return userTeam;
    }

    public void setLives(int change){
        this.lives += change;
    }

    public int getLives(){
        return this.lives;
    }
}
