package network.datatypes;

public class UserCreationData extends Data {
    private String name;

    public UserCreationData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
