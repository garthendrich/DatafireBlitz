package components;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import main.Lobby;

public class Bullet extends MovableEntity {
    static enum Direction {
        left, right
    }

    private static int WIDTH = 12;
    private static int HEIGHT = 8;
    private static int MOVEMENT_SPEED = 16;

    private char team;
    private int impact = 4;

    Image bullet = new ImageIcon("src/assets/bullet.png").getImage();

    public Bullet(int x, int y, char team) {
        super(x + (Player.WIDTH / 2), y + (Player.HEIGHT / 2), WIDTH, HEIGHT, MOVEMENT_SPEED);

        this.team = team;
    }

    public char getTeam() {
        return this.team;
    }

    public int getImpact() {
        if (this.dx > 0) {
            return this.impact;
        }

        return -this.impact;
    }

    public boolean isOutsideWindow() {
        Boolean pastLeft = x + width < 0;
        Boolean pastRight = x > Lobby.WINDOW_WIDTH;
        return pastLeft || pastRight;
    }

    @Override
    public void draw(Graphics graphics) {
        if (this.dx > 0) {
            graphics.drawImage(bullet, x, y - 2, width, height, null);
        } else {
            graphics.drawImage(bullet, x + width, y - 2, -width, height, null);
        }
    }
}
