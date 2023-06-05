package components;

import java.awt.Graphics;
import java.awt.Rectangle;

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

    public boolean isCollidingWith(Entity anotherEntity) {
        Rectangle entityRect = new Rectangle(x, y, width, height);
        Rectangle anotherEntityRect = new Rectangle(anotherEntity.x, anotherEntity.y, anotherEntity.width,
                anotherEntity.height);

        return entityRect.intersects(anotherEntityRect);
    }

    public void draw(Graphics graphics) {
        graphics.fillRect(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
