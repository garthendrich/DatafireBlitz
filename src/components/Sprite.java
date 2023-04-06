package components;

import java.awt.Graphics;

public class Sprite {
    int x;
    int y;
    int width;
    int height;
    int movementSpeed;

    int dx = 0;
    int dy = 0;

    public Sprite(int x, int y, int width, int height, int movementSpeed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.movementSpeed = movementSpeed;
    }

    public void moveLeft() {
        this.dx = -movementSpeed;
    }

    public void moveRight() {
        this.dx = movementSpeed;
    }

    public void stopHorizontalMovement() {
        this.dx = 0;
    }

    public void updatePosition() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics graphics) {
        graphics.fillRect(x, y, width, height);
    }
}
