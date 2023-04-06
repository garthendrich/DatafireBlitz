package components;

import java.awt.Graphics;

public class Entity {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public Entity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics graphics) {
        graphics.fillRect(x, y, width, height);
    }
}
