package network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import main.GameChat;
import network.datatypes.Data;
import network.datatypes.MessageData;
import network.datatypes.UserCreationData;

public class Client extends NetworkNode {
    private GameChat chat;

    public Client(String userName, String ipAddress, int portNumber) {
        connect(ipAddress, portNumber);

        UserCreationData userCreationData = new UserCreationData(userName);
        send(userCreationData);
    }

    private void connect(String ipAddress, int portNumber) {
        try {
            Socket clientSocket = new Socket(ipAddress, portNumber);
            start(clientSocket);
        } catch (UnknownHostException exception) {
            exception.printStackTrace();
            System.out.println("Can't find IP Address: " + exception.getMessage());
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("Error connecting to server: " + exception.getMessage());
        }
    }

    @Override
    void handleData(Data data) {
        if (data instanceof MessageData) {
            String formattedMessage = ((MessageData) data).getFormattedMessage();
            chat.updateChat(formattedMessage);
        }
    }

    public void attachChat(GameChat chat) {
        this.chat = chat;
    }
}
