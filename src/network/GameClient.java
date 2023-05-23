package network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import main.GameChat;
import main.GameLoop;

public class GameClient extends NetworkDataManager {
    private GameLoop gameLoop;
    private GameChat gameChat;
    private String displayName;

    public GameClient(String ipAddress, int portNumber, String displayName) {
        this.displayName = displayName;
        connect(ipAddress, portNumber);
        start();
    }

    private void connect(String ipAddress, int portNumber) {
        try {
            clientSocket = new Socket(ipAddress, portNumber);
        } catch (UnknownHostException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    void handleData(String data) {
        System.out.println("Received: " + data);
        gameChat.updateChat(data);
    }

    public void attachGameLoop(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void attachGameChat(GameChat gameChat) {
        this.gameChat = gameChat;
    }
}
