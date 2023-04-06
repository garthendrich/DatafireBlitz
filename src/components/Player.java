package components;

public class Player extends MovableEntity {
    private static int WIDTH = 48;
    private static int HEIGHT = 48;
    private static int MOVEMENT_SPEED = 2;

    int x;
    int y;

    public Player(int x, int y) {
        super(x, y, WIDTH, HEIGHT, MOVEMENT_SPEED);
    }
}
