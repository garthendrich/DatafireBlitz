package main;

public class GameLoop implements Runnable {
    private int FPS = 120;
    private double NANO_SECONDS_PER_FRAME = 1_000_000_000.0 / FPS;

    private Thread gameThread;
    private GamePanel gamePanel;
    private GameState gameState;

    // to be used by client user
    public GameLoop(GameState gameState, GamePanel gamePanel) {
        this(gameState);
        this.gamePanel = gamePanel;
    }

    // to be used by host user
    public GameLoop(GameState gameState) {
        this.gameState = gameState;

        start();
    }

    private void start() {
        gameState.setVaryingTeams();

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double currentTime = System.nanoTime();
        double nextFrameNanoSeconds = currentTime + NANO_SECONDS_PER_FRAME;

        while (true) {
            if (currentTime >= nextFrameNanoSeconds) {
                gameState.updateEntityPositions();
                // gameState.spawnPlayerBullets();
                gameState.manageBulletCollision();
                gameState.respawnDeadPlayers();

                if (gamePanel != null) {
                    gamePanel.render(gameState);
                }

                nextFrameNanoSeconds += NANO_SECONDS_PER_FRAME;
            }

            currentTime = System.nanoTime();
        }
    }
}
