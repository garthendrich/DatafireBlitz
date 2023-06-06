package components;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import main.Lobby;

public class Bullet extends MovableEntity {
    static enum Direction {
        left, right
    }

    private static int WIDTH = 16;
    private static int HEIGHT = 4;
    private static int MOVEMENT_SPEED = 1600;

    private final int impact = 4;
    private final int damage = 2;

    private final char team;

    Image bullet = new ImageIcon("src/assets/bullet.png").getImage();

    public Bullet(double x, double y, char team) {
        super(x + (Player.WIDTH / 2), y + (Player.HEIGHT / 2), WIDTH, HEIGHT, MOVEMENT_SPEED);

        this.team = team;
    }

    public char getTeam() {
        return this.team;
    }

    public int getImpact() {
        if (dx > 0) {
            return impact;
        }

        return -impact;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isOutsideWindow() {
        Boolean pastLeft = x + width < 0;
        Boolean pastRight = x > Lobby.WINDOW_WIDTH;
        return pastLeft || pastRight;
    }

    @Override
    public void draw(Graphics graphics) {
        if (this.dx > 0) {
            graphics.drawImage(bullet, (int) x, (int) y - 2, width, height, null);
        } else {
            graphics.drawImage(bullet, (int) x + width, (int) y - 2, -width, height, null);
        }
    }
}
