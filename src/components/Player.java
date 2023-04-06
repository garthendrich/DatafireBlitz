package components;

import java.util.ArrayList;

public class Player extends MovableEntity {
    private static int WIDTH = 48;
    private static int HEIGHT = 48;
    private static int MOVEMENT_SPEED = 3;
    private int JUMP_HEIGHT = 6;
    private double GRAVITY = 0.12;

    private boolean canJump = true;

    public Player(int x, int y) {
        super(x, y, WIDTH, HEIGHT, MOVEMENT_SPEED);
    }

    public void jump() {
        if (canJump) {
            dy = -JUMP_HEIGHT;
            canJump = false;
        }
    }

    public void updatePosition(ArrayList<Entity> platforms) {
        boolean willApplyGravity = true;

        for (Entity platform : platforms) {
            if (willBeOnTopOf(platform)) {
                willApplyGravity = false;
                dy = 0;
                canJump = true;
            }
        }

        if (willApplyGravity) {
            this.dy += GRAVITY;
        }

        super.updatePosition();
    }

    public boolean willBeOnTopOf(Entity entity) {
        return willCollideWith(entity) &&
                isAbove(entity) &&
                !isToTheLeftOf(entity) &&
                !isToTheRightOf(entity);
    }

    public boolean willCollideWith(Entity entity) {
        if (willBeToTheLeftOf(entity) || willBeToTheRightOf(entity)) {
            return false;
        }

        if (willBeAbove(entity) || willBeBelow(entity)) {
            return false;
        }

        return true;
    }

    private boolean isAbove(Entity entity) {
        return y + height <= entity.y;
    }

    private boolean isToTheLeftOf(Entity entity) {
        return x + width <= entity.x;
    }

    private boolean isToTheRightOf(Entity entity) {
        return x >= entity.x + entity.width;
    }

    private boolean willBeAbove(Entity entity) {
        return y + dy + height <= entity.y;
    }

    private boolean willBeBelow(Entity entity) {
        return y + dy >= entity.y + entity.height;
    }

    private boolean willBeToTheLeftOf(Entity entity) {
        return x + dx + width <= entity.x;
    }

    private boolean willBeToTheRightOf(Entity entity) {
        return x + dx >= entity.x + entity.width;
    }
}
