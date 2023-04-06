package main;

import java.util.ArrayList;

import components.Player;
import components.Sprite;

public class GameLoop implements Runnable {
    private int FPS = 120;
    private double NANO_SECONDS_PER_FRAME = 1_000_000_000.0 / FPS;

    private GamePanel gamePanel;
    private Thread gameThread;

    private Player player = new Player(100, 100);

    GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        gameThread = new Thread(this);
    }

    void start() {
        gameThread.start();
    }

    @Override
    public void run() {
        double nextFrameNanoSeconds = System.nanoTime() + NANO_SECONDS_PER_FRAME;

        while (true) {
            if (System.nanoTime() >= nextFrameNanoSeconds) {
                player.setMovementToRight(1);
                player.updatePosition();

                ArrayList<Sprite> sprites = new ArrayList<Sprite>();
                sprites.add(player);

                gamePanel.setSprites(sprites);
                gamePanel.repaint();

                nextFrameNanoSeconds += NANO_SECONDS_PER_FRAME;
            }
        }
    }
}
