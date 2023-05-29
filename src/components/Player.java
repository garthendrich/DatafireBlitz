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

    private char team;
    private boolean onPlatform = false;
    private boolean canJump = true;
    private double nextBulletFireSeconds = 0.0;
    private double knockback;

    public Player(int x, int y, char team) {
        super(x, y, WIDTH, HEIGHT, MOVEMENT_SPEED);

        this.team = team;
    }

    public void jump() {
        if (canJump) {
            dy = -JUMP_HEIGHT;
            canJump = false;
        }
    }

    public Bullet fireBullet() {
        double currentTimeSeconds = System.nanoTime() / 1_000_000_000.0;

        if (currentTimeSeconds >= nextBulletFireSeconds) {
            nextBulletFireSeconds = currentTimeSeconds + SECONDS_PER_BULLET;
            return new Bullet(this.getPosX(), this.getPosY(), this.getTeam());
        }

        return null;
    }

    public void updatePosition(ArrayList<Entity> platforms) {
        manageGravity(platforms);
        manageKnockback();

        super.updatePosition();
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

    public void moveDown() {
        if (onPlatform) {
            onPlatform = false;
            this.y++;
        }
    }

    public void knockback(int magnitude) {
        this.knockback += magnitude;
    }

    public int getPosX() {
        return this.x;
    }

    public int getPosY() {
        return this.y;
    }

    public char getTeam() {
        return this.team;
    }
}
