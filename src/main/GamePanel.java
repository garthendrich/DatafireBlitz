package main;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import components.Entity;

class GamePanel extends JPanel {
    private ArrayList<Entity> entities = new ArrayList<Entity>();

    void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for (Entity entity : entities) {
            entity.draw(graphics);
        }
    }
}
