package main;

import java.util.ArrayList;

import components.Entity;
import components.Player;

public class GameLoop implements Runnable {
    private int FPS = 120;
    private double NANO_SECONDS_PER_FRAME = 1_000_000_000.0 / FPS;

    private GamePanel gamePanel;
    private Thread gameThread;
    private KeyInputs keyInputs = new KeyInputs();

    private Player player = new Player(400, 0);
    private ArrayList<Entity> platforms = new ArrayList<Entity>();

    GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        gameThread = new Thread(this);

        createPlatforms();
    }

    private void createPlatforms() {
        platforms.add(new Entity(256, 128, 256, 128));
        platforms.add(new Entity(128, gamePanel.getHeight() - 128, gamePanel.getWidth() - 256, 64));
    }

    void start() {
        gameThread.start();
        gamePanel.addKeyListener(keyInputs);
        gamePanel.requestFocusInWindow();
    }

    @Override
    public void run() {
        double currentTime = System.nanoTime();
        double nextFrameNanoSeconds = currentTime + NANO_SECONDS_PER_FRAME;

        while (true) {
            if (currentTime >= nextFrameNanoSeconds) {
                updatePlayerMovement();
                updateCanvas();

                nextFrameNanoSeconds += NANO_SECONDS_PER_FRAME;
            }

            currentTime = System.nanoTime();
        }
    }

    private void updatePlayerMovement() {
        player.stopHorizontalMovement();
        if (keyInputs.moveLeft && !keyInputs.moveRight) {
            player.moveLeft();
        } else if (keyInputs.moveRight && !keyInputs.moveLeft) {
            player.moveRight();
        }

        player.updatePosition(platforms);
    }

    private void updateCanvas() {
        ArrayList<Entity> entities = new ArrayList<Entity>();
        entities.add(player);
        entities.addAll(platforms);

        gamePanel.setEntities(entities);
        gamePanel.repaint();
    }
}
