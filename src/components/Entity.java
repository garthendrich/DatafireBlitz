package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class Entity {
    protected double x;
    protected double y;
    protected int width;
    protected int height;

    public Entity(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isCollidingWith(Entity anotherEntity) {
        Rectangle2D.Double entityRect = new Rectangle2D.Double((int) x, (int) y, width, height);
        Rectangle2D.Double anotherEntityRect = new Rectangle2D.Double((int) anotherEntity.x, (int) anotherEntity.y,
                anotherEntity.width,
                anotherEntity.height);

        return entityRect.intersects(anotherEntityRect);
    }

    public void draw(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 0));
        graphics.fillRect((int) x, (int) y, width, height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
