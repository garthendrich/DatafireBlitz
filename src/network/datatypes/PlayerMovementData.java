package network.datatypes;

public class PlayerMovementData extends Data {
    public static enum Direction {
        left, right, up, down, stopHorizontal, stopVertical
    }

    private int userId;
    private Direction direction;
    private int x;
    private int y;

    // for data serialization
    public PlayerMovementData(int userId, Direction direction, int x, int y) {
        this(direction);
        this.userId = userId;
        this.x = x;
        this.y = y;
    }

    public PlayerMovementData(Direction direction) {
        this.direction = direction;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getUserId() {
        return userId;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
