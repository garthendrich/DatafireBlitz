package components;

import java.awt.Graphics;

public class Player {
    int WIDTH = 48;
    int HEIGHT = 48;

    int x;
    int y;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics graphics) {
        graphics.fillRect(x, y, WIDTH, HEIGHT);
    }
}
