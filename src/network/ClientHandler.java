package network;

import java.net.Socket;

import network.datatypes.Data;
import network.datatypes.MessageData;
import network.datatypes.GameInputData;
import network.datatypes.ClientCreationData;
import network.datatypes.PlayerOwnedData;

class ClientHandler extends NetworkNode {
    private static int nextUserId = 0;

    private Server server;

    private int userId;
    private String userName;

    ClientHandler(Server server, Socket clientSocket) {
        userId = nextUserId;
        nextUserId++;

        this.server = server;
        start(clientSocket);

        receiveUserData();
    }

    private void receiveUserData() {
        try {
            ClientCreationData data = (ClientCreationData) awaitData();
            this.userName = data.getUserName();
        } catch (ClassCastException error) {
            error.printStackTrace();
            System.out.println("Error receiving new user data: " + error.getMessage());
        }
    }

    @Override
    void handleData(Data data) {
        if (data instanceof MessageData) {
            ((MessageData) data).setSenderName(userName);
        } else if (data instanceof GameInputData) {
            ((PlayerOwnedData) data).setUserId(userId);
        }

        server.update(data);
    }

    int getUserId() {
        return userId;
    }

    String getUserName() {
        return userName;
    }
}
