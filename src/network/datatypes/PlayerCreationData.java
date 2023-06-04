package network.datatypes;

public class PlayerCreationData extends PlayerOwnedData {
    private String userName;

    public PlayerCreationData(int userId, String userName) {
        setUserId(userId);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
