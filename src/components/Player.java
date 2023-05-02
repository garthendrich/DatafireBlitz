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
    private double nextBulletFireSeconds = 0.0;

    private boolean canJump = true;
    private boolean onPlatform = false;

    public Player(int x, int y) {
        super(x, y, WIDTH, HEIGHT, MOVEMENT_SPEED);
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
            return new Bullet(this.getPosX(), this.getPosY());
        }

        return null;
    }

    public void updatePosition(ArrayList<Entity> platforms) {
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
            this.dy += GRAVITY;
        }

        super.updatePosition();
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

    public int getPosX() {
        return this.x;
    }

    public int getPosY() {
        return this.y;
    }

    public void moveDown() {
        if (onPlatform) {
            onPlatform = false;
            this.y++;
        }
    }
}
