package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameHud extends JPanel {
    int PLAYER_HUD_HEIGHT = 60;
    int PLAYER_HUD_WIDTH = 130;
    int numPlayer;

    JPanel player1, player2, player3, player4;
    JLabel hearts1, hearts2, hearts3, hearts4;

    ImageIcon heart0 = new ImageIcon("src/assets/hearts0.png");
    ImageIcon heart1 = new ImageIcon("src/assets/hearts1.png");
    ImageIcon heart2 = new ImageIcon("src/assets/hearts2.png");
    ImageIcon heart3 = new ImageIcon("src/assets/hearts3.png");

    GameHud() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        this.setPreferredSize(new Dimension(500, 70));
        this.setVisible(true);

        // TO-DO: retrieve actual player count
        numPlayer = 4;

        displayHUD(numPlayer);

        // sample
        updateHearts(hearts2, 2);
        updateHearts(hearts3, 1);
        updateHearts(hearts4, 0);
    }

    private void updateHearts(JLabel hearts, int health) {
        switch (health) {
            case 3:
                hearts.setIcon(heart3);
                break;
            case 2:
                hearts.setIcon(heart2);
                break;
            case 1:
                hearts.setIcon(heart1);
                break;
            case 0:
                hearts.setIcon(heart0);
                break;
        }
    }

    private JLabel newHearts() {
        JLabel hearts = new JLabel();
        hearts.setIcon(heart3);
        return hearts;
    }

    private JPanel newComponent(JLabel hearts, String playerName) {
        JPanel component = new JPanel();
        component.setLayout(new FlowLayout());

        JLabel name = new JLabel(playerName);
        component.add(name);
        component.add(hearts);

        return component;
    }

    private JLabel newPlayerImage(int playerNumber) {
        ImageIcon playerImage = null;

        switch (playerNumber) {
            case 1:
                playerImage = new ImageIcon("src/assets/player1.gif");
                break;
            case 2:
                playerImage = new ImageIcon("src/assets/player2.gif");
                break;
            case 3:
                playerImage = new ImageIcon("src/assets/player3.gif");
                break;
            case 4:
                playerImage = new ImageIcon("src/assets/player4.gif");
                break;
            default:
                playerImage = new ImageIcon("src/assets/square_red.png");
                break;
        }

        return new JLabel(playerImage);
    }

    private JPanel createPlayerHUD(int playerNumber, String playerName, JLabel hearts) {
        JPanel playerHUD = new JPanel();
        playerHUD.setLayout(new BorderLayout());
        playerHUD.setPreferredSize(new Dimension(PLAYER_HUD_WIDTH, PLAYER_HUD_HEIGHT));
        playerHUD.add(newPlayerImage(playerNumber), BorderLayout.WEST);
        playerHUD.add(newComponent(hearts, playerName));
        return playerHUD;
    }

    public void displayHUD(int numPlayer) {
        hearts1 = newHearts();
        player1 = createPlayerHUD(1, "Player 1", hearts1);
        this.add(player1);

        if (numPlayer > 1) {
            hearts2 = newHearts();
            player2 = createPlayerHUD(2, "Player 2", hearts2);
            this.add(player2);
        }

        if (numPlayer > 2) {
            hearts3 = newHearts();
            player3 = createPlayerHUD(3, "Player 3", hearts3);
            this.add(player3);
        }

        if (numPlayer > 3) {
            hearts4 = newHearts();
            player4 = createPlayerHUD(4, "Player 4", hearts4);
            this.add(player4);
        }
    }
}
