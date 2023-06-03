package network;

import java.net.Socket;

import network.datatypes.Data;
import network.datatypes.MessageData;
import network.datatypes.UserCreationData;

class ClientHandler extends NetworkNode {
    private Server server;

    private String userName;

    ClientHandler(Server server, Socket clientSocket) {
        this.server = server;
        start(clientSocket);

        receiveUserData();
    }

    private void receiveUserData() {
        try {
            UserCreationData data = (UserCreationData) awaitData();
            this.userName = data.getName();
        } catch (ClassCastException error) {
            error.printStackTrace();
            System.out.println("Error receiving new user data: " + error.getMessage());
        }
    }

    @Override
    void handleData(Data data) {
        if (data instanceof MessageData) {
            ((MessageData) data).setSenderName(userName);
        }

        server.broadcast(data);
    }
}
