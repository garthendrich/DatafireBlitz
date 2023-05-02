package components;

public class Bullet extends MovableEntity {
    private static int WIDTH = 2;
    private static int HEIGHT = 2;
    private static int MOVEMENT_SPEED = 5;

    private static int PLAYER_HEIGHT = 48;
    private static int PLAYER_WIDTH = 48;

    public Bullet(int x, int y) {
        super(x + (PLAYER_WIDTH/2), y + (PLAYER_HEIGHT/2), WIDTH, HEIGHT, MOVEMENT_SPEED);
    }

    public void moveUp(){
        this.dy = -MOVEMENT_SPEED;
    }

    public void moveDown(){
        this.dy = MOVEMENT_SPEED;
    }

    // TODO: handle dispawning when bullet is outside canvas
}
