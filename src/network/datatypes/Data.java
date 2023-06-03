package network.datatypes;

abstract public class Data {
    static enum DataTypes {
        userCreation, message
    }

    String data;

    public String getSerialized() {
        if (this instanceof UserCreationData) {
            UserCreationData data = (UserCreationData) this;
            return String.join(",", DataTypes.userCreation.toString(), data.getName());
        }

        if (this instanceof MessageData) {
            MessageData data = (MessageData) this;
            return String.join(",", DataTypes.message.toString(), data.getSenderName(), data.getMessage());
        }

        return null;
    }

    public static Data fromSerialized(String serializedData) {
        String[] dataChunks = serializedData.split(",");

        if (dataChunks[0].equals(DataTypes.userCreation.toString())) {
            return new UserCreationData(dataChunks[1]);
        }

        if (dataChunks[0].equals(DataTypes.message.toString())) {
            return new MessageData(dataChunks[1], dataChunks[2]);
        }

        return null;
    }
}
