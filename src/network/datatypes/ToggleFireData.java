package network.datatypes;

public class ToggleFireData extends PositionData {
    public static enum Status {
        start, stop
    }

    private Status status;

    // for data serialization
    public ToggleFireData(int userId, Status status, int playerX, int playerY) {
        this(userId, status);
        setPlayerPosition(playerX, playerY);
    }

    public ToggleFireData(int userId, Status status) {
        setUserId(userId);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
