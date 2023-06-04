package network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import main.GameChat;
import main.GameState;
import main.Lobby;
import network.datatypes.Data;
import network.datatypes.MessageData;
import network.datatypes.PlayerCreationData;
import network.datatypes.PositionData;
import network.datatypes.StartGameData;
import network.datatypes.ToggleFireData;
import network.datatypes.MovementData;
import network.datatypes.ClientCreationData;

public class Client extends NetworkNode {
    private Lobby lobbyPage;
    private GameState gameState;
    private GameChat chat;

    public Client(String userName, String ipAddress, int portNumber) {
        connect(ipAddress, portNumber);

        ClientCreationData userCreationData = new ClientCreationData(userName);
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
        } else if (data instanceof StartGameData) {
            lobbyPage.startGame();
        } else if (data instanceof PlayerCreationData) {
            PlayerCreationData playerCreationData = (PlayerCreationData) data;
            gameState.createPlayer(playerCreationData.getUserId(), playerCreationData.getUserName(),
                    playerCreationData.getUserTeam());
        } else if (data instanceof MovementData) {
            gameState.updatePlayerPosition((PositionData) data);
            gameState.movePlayer((MovementData) data);
        } else if (data instanceof ToggleFireData) {
            gameState.updatePlayerPosition((PositionData) data);
            gameState.toggleFire((ToggleFireData) data);
        }
    }

    public void attachLobbyPage(Lobby lobbyPage) {
        this.lobbyPage = lobbyPage;
    }

    public void attachChat(GameChat chat) {
        this.chat = chat;
    }

    public void attachGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
