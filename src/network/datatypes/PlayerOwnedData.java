package network.datatypes;

abstract public class PlayerOwnedData extends Data {
    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
