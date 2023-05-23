package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer implements Runnable {
    private static int nextPlayerId = 0;

    private Thread gameServerThread;

    private ServerSocket serverSocket;
    private ArrayList<GameClientHandler> clientHandlers = new ArrayList<GameClientHandler>();

    private boolean isSearchingForConnections = false;

    public GameServer(int portNumber) {
        gameServerThread = new Thread(this);

        initializeServerSocket(portNumber);

        gameServerThread.start();
    }

    @Override
    public void run() {
        startConnectionSearch();
    }

    private void initializeServerSocket(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("Error creating server socket: " + exception.getMessage());
        }
    }

    private void startConnectionSearch() {
        isSearchingForConnections = true;

        while (isSearchingForConnections) {
            try {
                Socket newClientSocket = serverSocket.accept();
                GameClientHandler newClientHandler = new GameClientHandler(this, newClientSocket);

                broadcast("player" + nextPlayerId);
                nextPlayerId++;

                clientHandlers.add(newClientHandler);
            } catch (IOException exception) {
                exception.printStackTrace();
                System.out.println("Error accepting client connection: " + exception.getMessage());
            }
        }
    }

    public void stopConnectionSearch() {
        isSearchingForConnections = false;
    }

    void broadcast(String data) {
        for (GameClientHandler clientHandler : clientHandlers) {
            if (clientHandler != null) {
                clientHandler.send(data);
            }
        }
    }

    public void shutdown() {

    }
}
