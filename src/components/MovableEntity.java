package components;

class MovableEntity extends Entity {
    private int movementSpeed;

    protected double dx = 0;
    protected double dy = 0;

    private double lastPositionUpdate = System.nanoTime();

    MovableEntity(double x, double y, int width, int height, int movementSpeed) {
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

    protected double[] getNextPosition() {
        double deltaTime = (System.nanoTime() - lastPositionUpdate) / 1_000_000_000.0;
        double[] nextPosition = { x + (dx * deltaTime), y + (dy * deltaTime) };
        return nextPosition;
    }

    public void updatePosition() {
        double[] nextPosition = getNextPosition();
        x = nextPosition[0];
        y = nextPosition[1];

        lastPositionUpdate = System.nanoTime();
    }
}
