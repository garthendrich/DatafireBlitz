package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import main.GameLoop;
import main.GameState;
import network.datatypes.Data;
import network.datatypes.PlayerCreationData;
import network.datatypes.PlayerMovementData;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();

    private Thread serverThread;
    private boolean isSearchingForConnections = true;

    GameState gameState;

    public Server() {
        initializeServerSocket();

        serverThread = new Thread(this);
        serverThread.start();
    }

    @Override
    public void run() {
        startConnectionSearch();
    }

    private void initializeServerSocket() {
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("Error creating server socket: " + exception.getMessage());
        }
    }

    public String getIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException exception) {
            exception.printStackTrace();
            System.out.println("Error getting IP address: " + exception.getMessage());
            return null;
        }
    }

    public int getPortNumber() {
        return serverSocket.getLocalPort();
    }

    private void startConnectionSearch() {
        while (isSearchingForConnections) {
            try {
                Socket newClientSocket = serverSocket.accept();
                ClientHandler newClientHandler = new ClientHandler(this, newClientSocket);

                clientHandlers.add(newClientHandler);
            } catch (IOException exception) {
                exception.printStackTrace();
                System.out.println("Error accepting client connection: " + exception.getMessage());
            }
        }
    }

    public void stopConnectionSearch() {
        isSearchingForConnections = false;
        serverThread.interrupt();
        shutdown();
    }

    public void startGame() {
        gameState = new GameState();
        new GameLoop(gameState);

        for (ClientHandler clientHandler : clientHandlers) {
            int userId = clientHandler.getUserId();
            String userName = clientHandler.getUserName();

            gameState.createPlayer(userId, userName);

            PlayerCreationData playerCreationData = new PlayerCreationData(userId, userName);
            broadcast(playerCreationData);
        }

    }

    void update(Data data) {
        if (data instanceof PlayerMovementData) {
            gameState.movePlayer((PlayerMovementData) data);
        }

        broadcast(data);
    }

    void broadcast(Data data) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler != null) {
                clientHandler.send(data);
            }
        }
    }

    void shutdown() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException exception) {
                exception.printStackTrace();
                System.out.println("Error shutting down server socket: " + exception.getMessage());
            }
        }
    }
}
