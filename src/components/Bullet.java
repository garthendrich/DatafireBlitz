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
    private static int MOVEMENT_SPEED = 16;

    private char team;
    private int impact = 4;

    Image bullet_left = new ImageIcon("src/assets/bullet_left.png").getImage();
    Image bullet_right = new ImageIcon("src/assets/bullet_right.png").getImage();

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
            graphics.drawImage(bullet_right, x, y - 2, null);
        } else {
            graphics.drawImage(bullet_left, x, y - 2, null);
        }
        // graphics.setColor(new Color(0, 0, 0, 100));
        // graphics.fillRect(x, y, width, height);
    }
}
