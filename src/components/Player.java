package components;

public class Player extends Sprite {
    static int WIDTH = 48;
    static int HEIGHT = 48;

    int x;
    int y;

    public Player(int x, int y) {
        super(x, y, WIDTH, HEIGHT);
    }
}
