package main;

import java.awt.Graphics;

import javax.swing.JPanel;

import components.Player;

class GamePanel extends JPanel {
    private Player player = new Player(100, 100);

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        player.draw(graphics);
    }
}
