package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import network.Client;
import network.datatypes.GameInputData;

public class GameKeyInputs implements KeyListener {
    private static enum KeyInputs {
        left, right, up, down, shoot
    }

    private static HashMap<Integer, KeyInputs> bindings;

    private Client client;
    private ArrayList<KeyInputs> inputs = new ArrayList<KeyInputs>();

    GameKeyInputs(GamePanel gamePanel, Client client) {
        bindings = new HashMap<Integer, KeyInputs>();
        bindings.put(KeyEvent.VK_A, KeyInputs.left);
        bindings.put(KeyEvent.VK_D, KeyInputs.right);
        bindings.put(KeyEvent.VK_W, KeyInputs.up);
        bindings.put(KeyEvent.VK_S, KeyInputs.down);
        bindings.put(KeyEvent.VK_J, KeyInputs.shoot);

        this.client = client;
        gamePanel.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        KeyInputs activeAction = bindings.get(keyCode);

        if (!inputs.contains(activeAction)) {
            inputs.add(activeAction);

            sendInputToClient();
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int keyCode = event.getKeyCode();
        KeyInputs activeAction = bindings.get(keyCode);
        inputs.remove(activeAction);

        sendInputToClient();
    }

    private void sendInputToClient() {
        // ! to fix: extra unnecessary input data are being sent.
        // e.g. player moves to the left but client also sends a signal for player to
        // stop firing even though the player is already not firing bullets

        GameInputData horizontalInputData = getHorizontalInputData();
        client.send(horizontalInputData);

        GameInputData verticalInputData = getVerticalInputData();
        client.send(verticalInputData);

        GameInputData shootInputData = getShootInputData();
        client.send(shootInputData);
    }

    private GameInputData getHorizontalInputData() {
        Boolean left = inputs.contains(KeyInputs.left);
        Boolean right = inputs.contains(KeyInputs.right);

        if (left && !right) {
            return new GameInputData(GameInputData.Input.moveLeft);
        } else if (right && !left) {
            return new GameInputData(GameInputData.Input.moveRight);
        } else {
            return new GameInputData(GameInputData.Input.stopHorizontal);
        }
    }

    private GameInputData getVerticalInputData() {
        Boolean up = inputs.contains(KeyInputs.up);
        Boolean down = inputs.contains(KeyInputs.down);

        if (up && !down) {
            return new GameInputData(GameInputData.Input.jump);
        } else if (down && !up) {
            return new GameInputData(GameInputData.Input.drop);
        } else {
            return new GameInputData(GameInputData.Input.stopVertical);
        }
    }

    private GameInputData getShootInputData() {
        Boolean shoot = inputs.contains(KeyInputs.shoot);

        if (shoot) {
            return new GameInputData(GameInputData.Input.startShoot);
        } else {
            return new GameInputData(GameInputData.Input.stopShoot);
        }
    }
}
