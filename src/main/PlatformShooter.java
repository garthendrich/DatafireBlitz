package main;

import javax.swing.JFrame;

public class PlatformShooter {
    public static void main(String[] args) {
        JFrame window = createWindow();
        window.setVisible(true);
    }

    private static JFrame createWindow() {
        JFrame window = new JFrame();
        window.setSize(960, 540);
        window.setTitle("");
        window.setResizable(false);
        window.setLocationRelativeTo(null); // center window on screen
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return window;
    }
}