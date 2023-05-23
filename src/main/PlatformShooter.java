package main;

import javax.swing.JFrame;

// ! temporary class name
public class PlatformShooter {
    static int WINDOW_WIDTH = 960;
    static int WINDOW_HEIGHT = 540;

    public static void main(String[] args) {
        JFrame window = createWindow();

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.setVisible(true);

        GameLoop gameLoop = new GameLoop(gamePanel);
        gameLoop.start();

        GameChat gameChat = new GameChat();
        
    }

    private static JFrame createWindow() {
        JFrame window = new JFrame();
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setTitle("");
        window.setResizable(false);
        window.setLocationRelativeTo(null); // center window on screen
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return window;
    }
}