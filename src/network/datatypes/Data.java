package network.datatypes;

abstract public class Data {
    private static enum DataTypes {
        clientCreation, playerCreation, playerMovement, message
    }

    public String getSerialized() {
        if (this instanceof ClientCreationData) {
            ClientCreationData data = (ClientCreationData) this;
            return String.join(",", DataTypes.clientCreation.toString(), data.getUserName());
        }

        if (this instanceof MessageData) {
            MessageData data = (MessageData) this;
            return String.join(",", DataTypes.message.toString(), data.getSenderName(), data.getMessage());
        }

        if (this instanceof PlayerCreationData) {
            PlayerCreationData data = (PlayerCreationData) this;
            return String.join(",", DataTypes.playerCreation.toString(), Integer.toString(data.getUserId()),
                    data.getUserName());
        }

        if (this instanceof PlayerMovementData) {
            PlayerMovementData data = (PlayerMovementData) this;
            return String.join(",", DataTypes.playerMovement.toString(), Integer.toString(data.getUserId()),
                    data.getDirection().toString());
        }

        System.out.println("Error serializing data " + this.getClass().getSimpleName());
        return null;
    }

    public static Data fromSerialized(String serializedData) {
        String[] dataChunks = serializedData.split(",");

        if (dataChunks[0].equals(DataTypes.clientCreation.toString())) {
            return new ClientCreationData(dataChunks[1]);
        }

        if (dataChunks[0].equals(DataTypes.message.toString())) {
            return new MessageData(dataChunks[1], dataChunks[2]);
        }

        if (dataChunks[0].equals(DataTypes.playerCreation.toString())) {
            return new PlayerCreationData(Integer.parseInt(dataChunks[1]), dataChunks[2]);
        }

        if (dataChunks[0].equals(DataTypes.playerMovement.toString())) {
            return new PlayerMovementData(Integer.parseInt(dataChunks[1]),
                    PlayerMovementData.Direction.valueOf(dataChunks[2]));
        }

        System.out.println("Error deserializing data " + serializedData);
        return null;
    }
}
