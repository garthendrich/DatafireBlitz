package components;

public class Bullet extends MovableEntity {
    private static int WIDTH = 16;
    private static int HEIGHT = 4;
    private static int MOVEMENT_SPEED = 8;

    private char team;
    private int impact = 4;

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
}
