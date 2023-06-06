package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameHud extends JPanel {
    int PLAYER_HUD_HEIGHT = 60;
    int PLAYER_HUD_WIDTH = 130;

    ImageIcon heart0 = new ImageIcon("src/assets/hearts0.png");
    ImageIcon heart1 = new ImageIcon("src/assets/hearts1.png");
    ImageIcon heart2 = new ImageIcon("src/assets/hearts2.png");
    ImageIcon heart3 = new ImageIcon("src/assets/hearts3.png");

    private ArrayList<Integer> userIds = new ArrayList<Integer>();
    private ArrayList<JPanel> playerDisplays = new ArrayList<JPanel>();
    private ArrayList<JLabel> playerHeartDisplays = new ArrayList<JLabel>();
    private ImageIcon[] heartImages = { heart0, heart1, heart2, heart3 };

    GameHud() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
        this.setPreferredSize(new Dimension(500, 70));
        this.setVisible(true);
    }

    public void createPlayer(int userId, String userName) {
        userIds.add(userId);

        JLabel playerHeartDisplay = newHearts();
        playerHeartDisplays.add(playerHeartDisplay);

        JPanel playerDisplay = createPlayerHUD(playerDisplays.size(), userName, playerHeartDisplay);
        playerDisplays.add(playerDisplay);
    }

    public void setHearts(int userId, int lives) {
        int playerHeartDisplayIndex = userIds.indexOf(userId);
        JLabel playerHeartDisplay = playerHeartDisplays.get(playerHeartDisplayIndex);
        playerHeartDisplay.setIcon(heartImages[lives]);
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
        playerHUD.add(newPlayerImage(playerNumber + 1), BorderLayout.WEST);
        playerHUD.add(newComponent(hearts, playerName));
        return playerHUD;
    }

    public void displayHUD() {
        for (JPanel playerDisplay : playerDisplays) {
            this.add(playerDisplay);
        }
    }
}
