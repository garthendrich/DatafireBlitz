package network.datatypes;

public class PlayerCreationData extends PlayerOwnedData {
    private String userName;
    private char userTeam;

    public PlayerCreationData(int userId, String userName, char userTeam) {
        setUserId(userId);
        this.userName = userName;
        this.userTeam = userTeam;
    }

    public String getUserName() {
        return userName;
    }

    public char getUserTeam() {
        return userTeam;
    }
}
