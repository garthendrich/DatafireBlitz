package network.datatypes;

public class PlayerMovementData extends Data {
    public static enum Direction {
        left, right, up, down, stopHorizontal, stopVertical
    }

    private int userId;
    private Direction direction;

    // data to receive by client handlers / server
    public PlayerMovementData(Direction direction) {
        this.direction = direction;
    }

    // data to receive by clients
    public PlayerMovementData(int userId, Direction direction) {
        this.userId = userId;
        this.direction = direction;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public Direction getDirection() {
        return direction;
    }
}
