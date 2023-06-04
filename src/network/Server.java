package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import components.Player;
import main.GameLoop;
import main.GameState;
import network.datatypes.Data;
import network.datatypes.PlayerCreationData;
import network.datatypes.PositionData;
import network.datatypes.ToggleFireData;
import network.datatypes.GameInputData;
import network.datatypes.MovementData;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();

    private Thread serverThread;
    private boolean isSearchingForConnections = true;

    GameState serverGameState;

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
        serverGameState = new GameState();
        new GameLoop(serverGameState);

        for (ClientHandler clientHandler : clientHandlers) {
            int userId = clientHandler.getUserId();
            String userName = clientHandler.getUserName();
            char userTeam = clientHandler.getUserTeam();

            serverGameState.createPlayer(userId, userName, userTeam);

            PlayerCreationData playerCreationData = new PlayerCreationData(userId, userName, userTeam);
            broadcast(playerCreationData);
        }
    }

    public void setVaryingTeams() {
        char nextTeam = 'A';
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.setUserTeam(nextTeam);
            nextTeam++;
        }
    }

    void update(Data data) {
        if (data instanceof GameInputData) {
            GameInputData gameInputData = (GameInputData) data;

            PositionData actionData = null;

            int userId = gameInputData.getUserId();
            GameInputData.Input input = gameInputData.getInput();

            if (input == GameInputData.Input.moveLeft) {
                actionData = new MovementData(userId, MovementData.Movement.left);
            } else if (input == GameInputData.Input.moveRight) {
                actionData = new MovementData(userId, MovementData.Movement.right);
            } else if (input == GameInputData.Input.jump) {
                actionData = new MovementData(userId, MovementData.Movement.jump);
            } else if (input == GameInputData.Input.stopHorizontal) {
                actionData = new MovementData(userId, MovementData.Movement.stopHorizontal);
            } else if (input == GameInputData.Input.stopVertical) {
                actionData = new MovementData(userId, MovementData.Movement.stopVertical);
            } else if (input == GameInputData.Input.drop) {
                actionData = new MovementData(userId, MovementData.Movement.drop);
            } else if (input == GameInputData.Input.startShoot) {
                actionData = new ToggleFireData(userId, ToggleFireData.Status.start);
            } else if (input == GameInputData.Input.stopShoot) {
                actionData = new ToggleFireData(userId, ToggleFireData.Status.stop);
            }

            if (actionData instanceof MovementData) {
                serverGameState.movePlayer((MovementData) actionData);
            } else if (actionData instanceof ToggleFireData) {
                serverGameState.toggleFire((ToggleFireData) actionData);
            }

            syncPositionData(actionData);
            broadcast(actionData);
            return;
        }

        broadcast(data);
    }

    private void syncPositionData(PositionData positionData) {
        int userId = positionData.getUserId();
        Player serverPlayer = serverGameState.findPlayer(userId);

        int serverPlayerX = serverPlayer.getX();
        int serverPlayerY = serverPlayer.getY();
        positionData.setPlayerPosition(serverPlayerX, serverPlayerY);
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
