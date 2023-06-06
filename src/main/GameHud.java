package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
    private ArrayList<JLabel> playerDamageDisplays = new ArrayList<JLabel>();
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

        JLabel playerDamageDisplay = newDamages();
        playerDamageDisplays.add(playerDamageDisplay);

        JPanel playerDisplay = createPlayerHUD(playerDisplays.size(), userName, playerHeartDisplay,
                playerDamageDisplay);
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

    private JLabel newDamages() {
        JLabel damages = new JLabel();
        damages.setText("0%");
        return damages;
    }

    void setDamage(int userId, int damage) {
        int playerDamageDisplayIndex = userIds.indexOf(userId);
        JLabel playerDamageDisplay = playerDamageDisplays.get(playerDamageDisplayIndex);
        playerDamageDisplay.setText(damage + "%");
    }

    private JPanel newComponent(JLabel hearts, JLabel damages, String playerName) {
        JPanel component = new JPanel();
        component.setLayout(new GridLayout(3, 0));

        JLabel name = new JLabel(playerName);

        name.setHorizontalAlignment(JLabel.CENTER);
        damages.setHorizontalAlignment(JLabel.CENTER);
        hearts.setHorizontalAlignment(JLabel.CENTER);

        component.add(name);
        component.add(damages);
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

    private JPanel createPlayerHUD(int playerNumber, String playerName, JLabel hearts, JLabel damages) {
        JPanel playerHUD = new JPanel();
        playerHUD.setLayout(new BorderLayout());
        playerHUD.setPreferredSize(new Dimension(PLAYER_HUD_WIDTH, PLAYER_HUD_HEIGHT));
        playerHUD.add(newPlayerImage(playerNumber + 1), BorderLayout.WEST);
        playerHUD.add(newComponent(hearts, damages, playerName));
        return playerHUD;
    }

    public void displayHUD() {
        for (JPanel playerDisplay : playerDisplays) {
            this.add(playerDisplay);
        }
    }
}
