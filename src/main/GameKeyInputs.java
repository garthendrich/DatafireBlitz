package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import network.Client;
import network.datatypes.PlayerMovementData;

public class GameKeyInputs implements KeyListener {
    private static enum Actions {
        left, right, up, down
    }

    private static HashMap<Integer, Actions> bindings = new HashMap<Integer, Actions>();

    private Client client;
    private ArrayList<Actions> actions = new ArrayList<Actions>();

    GameKeyInputs(GamePanel gamePanel, Client client) {
        bindings.put(KeyEvent.VK_A, Actions.left);
        bindings.put(KeyEvent.VK_D, Actions.right);
        bindings.put(KeyEvent.VK_W, Actions.up);
        bindings.put(KeyEvent.VK_S, Actions.down);

        this.client = client;
        gamePanel.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        Actions activeAction = bindings.get(keyCode);

        if (!actions.contains(activeAction)) {
            actions.add(activeAction);
            sendUserMovementDataToClient();
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        Actions activeAction = bindings.get(keyCode);
        actions.remove(activeAction);
        sendUserMovementDataToClient();
    }

    private void sendUserMovementDataToClient() {
        Boolean left = actions.contains(Actions.left);
        Boolean right = actions.contains(Actions.right);
        Boolean up = actions.contains(Actions.up);
        Boolean down = actions.contains(Actions.down);

        PlayerMovementData userMovementData;
        if (left && !right) {
            userMovementData = new PlayerMovementData(PlayerMovementData.Direction.left);
        } else if (right && !left) {
            userMovementData = new PlayerMovementData(PlayerMovementData.Direction.right);
        } else {
            userMovementData = new PlayerMovementData(PlayerMovementData.Direction.stopHorizontal);
        }

        if (userMovementData != null) {
            client.send(userMovementData);
        }

        userMovementData = null;
        if (up && !down) {
            userMovementData = new PlayerMovementData(PlayerMovementData.Direction.up);
        } else if (down && !up) {
            userMovementData = new PlayerMovementData(PlayerMovementData.Direction.down);
        } else {
            userMovementData = new PlayerMovementData(PlayerMovementData.Direction.stopVertical);
        }

        if (userMovementData != null) {
            client.send(userMovementData);
        }
    }
}
