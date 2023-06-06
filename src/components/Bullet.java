package components;

import main.Lobby;

public class Bullet extends MovableEntity {
    static enum Direction {
        left, right
    }

    private static int WIDTH = 16;
    private static int HEIGHT = 4;
    private static int MOVEMENT_SPEED = 1600;

    private char team;
    private int impact = 4;

    public Bullet(double x, double y, char team) {
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
}
