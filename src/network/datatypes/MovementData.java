package network.datatypes;

public class MovementData extends PositionData {
    public static enum Movement {
        left, right, jump, drop, stopHorizontal, stopVertical
    }

    private Movement direction;

    // for data serialization
    public MovementData(int userId, Movement direction, double x, double y) {
        this(userId, direction);
        setPlayerPosition(x, y);
    }

    public MovementData(int userId, Movement direction) {
        setUserId(userId);
        this.direction = direction;
    }

    public Movement getDirection() {
        return direction;
    }
}
