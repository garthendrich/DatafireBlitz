package network.datatypes;

public class PlayerCreationData extends Data {
    private int userId;
    private String userName;

    public PlayerCreationData(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
