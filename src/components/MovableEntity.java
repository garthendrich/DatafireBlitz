package components;

class MovableEntity extends Entity {
    private int movementSpeed;

    protected int dx = 0;
    protected double dy = 0;

    MovableEntity(int x, int y, int width, int height, int movementSpeed) {
        super(x, y, width, height);

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

    public int getPosY(){
        return this.y;
    }

    public void respawn(){
        x = 400;
        y = 0;
    }
}
