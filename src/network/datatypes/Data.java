package network.datatypes;

abstract public class Data {
    private static enum DataType {
        clientCreation, startGame, playerCreation, gameInput, movement, fire, message, stats
    }

    public String getSerialized() {
        if (this instanceof ClientCreationData) {
            ClientCreationData data = (ClientCreationData) this;
            return String.join(",", DataType.clientCreation.toString(), data.getUserName());
        }

        if (this instanceof StartGameData) {
            return String.join(",", DataType.startGame.toString());
        }

        if (this instanceof MessageData) {
            MessageData data = (MessageData) this;
            return String.join(",", DataType.message.toString(), data.getSenderName(), data.getMessage());
        }

        if (this instanceof PlayerCreationData) {
            PlayerCreationData data = (PlayerCreationData) this;
            return String.join(",", DataType.playerCreation.toString(), Integer.toString(data.getUserId()),
                    data.getUserName(), String.valueOf(data.getUserTeam()));
        }

        if (this instanceof GameInputData) {
            GameInputData data = (GameInputData) this;
            return String.join(",", DataType.gameInput.toString(), data.getInput().toString());
        }

        if (this instanceof MovementData) {
            MovementData data = (MovementData) this;
            return String.join(",", DataType.movement.toString(), Integer.toString(data.getUserId()),
                    data.getDirection().toString(), Double.toString(data.getPlayerX()),
                    Double.toString(data.getPlayerY()));
        }

        if (this instanceof ToggleFireData) {
            ToggleFireData data = (ToggleFireData) this;
            return String.join(",", DataType.fire.toString(), Integer.toString(data.getUserId()),
                    data.getStatus().toString(), Double.toString(data.getPlayerX()),
                    Double.toString(data.getPlayerY()));
        }

        if (this instanceof LivesData) {
            LivesData data = (LivesData) this;
            return String.join(",", DataType.stats.toString(), Integer.toString(data.getUserId()),
                    Integer.toString(data.getLives()));
        }

        System.out.println("Error serializing data " + this.getClass().getSimpleName());
        return null;
    }

    public static Data fromSerialized(String serializedData) {
        String[] dataChunks = serializedData.split(",");

        if (dataChunks[0].equals(DataType.clientCreation.toString())) {
            return new ClientCreationData(dataChunks[1]);
        }

        if (dataChunks[0].equals(DataType.startGame.toString())) {
            return new StartGameData();
        }

        if (dataChunks[0].equals(DataType.message.toString())) {
            return new MessageData(dataChunks[1], dataChunks[2]);
        }

        if (dataChunks[0].equals(DataType.playerCreation.toString())) {
            return new PlayerCreationData(Integer.parseInt(dataChunks[1]), dataChunks[2], dataChunks[3].charAt(0));
        }

        if (dataChunks[0].equals(DataType.gameInput.toString())) {
            return new GameInputData(GameInputData.Input.valueOf(dataChunks[1]));
        }

        if (dataChunks[0].equals(DataType.movement.toString())) {
            return new MovementData(Integer.parseInt(dataChunks[1]), MovementData.Movement.valueOf(dataChunks[2]),
                    Double.parseDouble(dataChunks[3]), Double.parseDouble(dataChunks[4]));
        }

        if (dataChunks[0].equals(DataType.fire.toString())) {
            return new ToggleFireData(Integer.parseInt(dataChunks[1]), ToggleFireData.Status.valueOf(dataChunks[2]),
                    Double.parseDouble(dataChunks[3]), Double.parseDouble(dataChunks[4]));
        }

        if (dataChunks[0].equals(DataType.stats.toString())) {
            return new LivesData(Integer.parseInt(dataChunks[1]), Integer.parseInt(dataChunks[2]));
        }

        System.out.println("Error deserializing data " + serializedData);
        return null;
    }
}
