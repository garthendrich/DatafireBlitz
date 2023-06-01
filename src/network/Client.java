package network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import main.GameChat;

public class Client extends NetworkNode {
    GameChat chat;

    public Client(String ipAddress, int portNumber) {
        connect(ipAddress, portNumber);

        start();
    }

    private void connect(String ipAddress, int portNumber) {
        try {
            clientSocket = new Socket(ipAddress, portNumber);
        } catch (UnknownHostException exception) {
            exception.printStackTrace();
            System.out.println("Can't find IP Address: " + exception.getMessage());
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("Error connecting to server: " + exception.getMessage());
        }
    }

    @Override
    void handleData(String data) {
        chat.updateChat(data);
    }

    public void attachChat(GameChat chat) {
        this.chat = chat;
    }
}
