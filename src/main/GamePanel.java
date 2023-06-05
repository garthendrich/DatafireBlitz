package main;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import components.Entity;

class GamePanel extends JPanel {
    private ArrayList<Entity> entities = new ArrayList<Entity>();

    GamePanel() {
        setFocusable(true);
    }

    void render(GameState gameState) {
        this.entities = gameState.getEntities();

        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for (Entity entity : entities) {
            entity.draw(graphics);
        }
    }
}
