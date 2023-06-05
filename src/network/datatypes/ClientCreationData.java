package network.datatypes;

public class ClientCreationData extends Data {
    private String userName;

    public ClientCreationData(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
