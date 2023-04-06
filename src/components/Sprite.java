package components;

import java.awt.Graphics;

public class Sprite {
    int x;
    int y;
    int width;
    int height;

    int dx = 0;
    int dy = 0;

    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setMovementToRight(int dx) {
        this.dx = dx;
    }

    public void updatePosition() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics graphics) {
        graphics.fillRect(x, y, width, height);
    }
}
