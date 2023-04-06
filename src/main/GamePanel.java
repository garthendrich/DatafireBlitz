package main;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import components.Sprite;

class GamePanel extends JPanel {
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();

    void setSprites(ArrayList<Sprite> sprites) {
        this.sprites = sprites;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for (Sprite sprite : sprites) {
            sprite.draw(graphics);
        }
    }
}
