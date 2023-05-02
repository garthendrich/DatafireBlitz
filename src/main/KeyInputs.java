package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputs implements KeyListener {
    // for player movement
    boolean moveLeft = false;
    boolean moveRight = false;
    boolean moveUp = false;
    boolean moveDown = false;

    // for bullet direction
    boolean bulletLeft = false;
    boolean bulletRight = false;

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        setKeyValue(event.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        setKeyValue(event.getKeyCode(), false);
    }

    private void setKeyValue(int keyCode, boolean value) {
        switch (keyCode) {
            case KeyEvent.VK_A:
                moveLeft = value;
                break;
            case KeyEvent.VK_S:
                moveDown = value;
                break;
            case KeyEvent.VK_W:
                moveUp = value;
                break;
            case KeyEvent.VK_D:
                moveRight = value;
                break;
            case KeyEvent.VK_LEFT:
                bulletLeft = value;
                break;
            case KeyEvent.VK_RIGHT:
                bulletRight = value;
        }
    }
}
