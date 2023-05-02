package components;

public class Bullet extends MovableEntity {
    private static int WIDTH = 16;
    private static int HEIGHT = 4;
    private static int MOVEMENT_SPEED = 8;

    public Bullet(int x, int y) {
        super(x + (Player.WIDTH / 2), y + (Player.HEIGHT / 2), WIDTH, HEIGHT, MOVEMENT_SPEED);
    }

    // TODO: handle dispawning when bullet is outside canvas
}
