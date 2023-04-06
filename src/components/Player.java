package components;

public class Player extends MovableEntity {
    private static int WIDTH = 48;
    private static int HEIGHT = 48;
    private static int MOVEMENT_SPEED = 2;
    private double GRAVITY = 0.1;

    private boolean isFalling = true;

    public Player(int x, int y) {
        super(x, y, WIDTH, HEIGHT, MOVEMENT_SPEED);
    }

    @Override
    public void updatePosition() {
        if (isFalling) {
            this.dy += GRAVITY;
        }

        super.updatePosition();
    }
}
