package main;

import java.awt.Graphics;

import javax.swing.JPanel;

class GamePanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(100, 100, 50, 50);
    }
}
