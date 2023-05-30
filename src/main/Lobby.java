package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Lobby extends JFrame{
    static int WINDOW_WIDTH = 960;
    static int WINDOW_HEIGHT = 540;

    public Lobby(){
        this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        this.setTitle("Data Fire Blitz");
        this.setResizable(false);
        this.setLocationRelativeTo(null); // center window on screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
    }

    public void homepage(){
        this.getContentPane().removeAll();
        TextField usernameInput = new TextField(20);
        JButton b1 = new JButton("Create Lobby");
        b1.setBackground(Color.green);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = usernameInput.getText();
                if (name.length() > 0) lobbyPage(name);
            }
        });
        JButton b2 = new JButton("Join Lobby");
        b2.setBackground(Color.cyan);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String name = usernameInput.getText();
                if (name.length() > 0) joinLobbyPage(name);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel user = new JLabel("Enter player name: ", SwingConstants.CENTER);
        user.setLabelFor((usernameInput));
        this.add(user, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(usernameInput, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        this.add(b1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(b2,gbc);
        this.setVisible(true);

        refreshFrame();
        return;
    }

    private void lobbyPage(String name){
        this.getContentPane().removeAll();
        String port = "3000";
        String ip = "10.2.10.3";
        String[] players = {name};
        String label = "";
        for(int i=0; i<players.length; i++) label.concat(players[i]).concat(" ");
        JLabel ipLabel = new JLabel("IP Address: ".concat(ip), SwingConstants.CENTER);
        JLabel portLabel = new JLabel("Port Number: ".concat(port), SwingConstants.CENTER);
        JLabel playersLabel = new JLabel("Players: ".concat(label), SwingConstants.CENTER);
        JLabel confirmation = new JLabel("Start the game?", SwingConstants.CENTER);
        JButton b1 = new JButton("Start");
        JButton b2 = new JButton("Main menu");
        confirmation.setLabelFor(b1);
        b1.setBackground(Color.green);
        b2.setBackground(Color.cyan);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                startGame(name);
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                homepage();
            }
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(ipLabel,gbc);
        gbc.gridy = 1;
        this.add(portLabel,gbc);
        gbc.gridy = 2;
        this.add(playersLabel,gbc);
        gbc.gridy = 3;
        this.add(confirmation,gbc);
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        this.add(b1,gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        this.add(b2,gbc);
        refreshFrame();
    }

    private void joinLobbyPage(String name) {
        this.getContentPane().removeAll();
        JLabel ip, port;
        TextField ipInput, portInput;
        ip = new JLabel("IP Address:", SwingConstants.CENTER);
        port = new JLabel("Port Number:", SwingConstants.CENTER);
        ipInput = new TextField(20);
        portInput = new TextField(20);
        ip.setLabelFor(ipInput);
        port.setLabelFor(portInput);
        JButton b1 = new JButton("Join Lobby");
        b1.setBackground(Color.green);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String ipAddress = ipInput.getText();
                String portNumber = portInput.getText();
                if(ipAddress.length() > 0 && portNumber.length() > 0){
                    lobbyPage(name);
                }
            }
        });

        JButton b2 = new JButton("Main Menu");
        b2.setBackground(Color.cyan);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                homepage();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(ip,gbc);
        gbc.gridy = 1;
        this.add(ipInput,gbc);
        gbc.gridy = 2;
        this.add(port,gbc);
        gbc.gridy = 3;
        this.add(portInput,gbc);
        gbc.gridy = 4;
        this.add(b1,gbc);
        gbc.gridy = 5;
        this.add(b2,gbc);

        refreshFrame();
    }

    private void refreshFrame(){
        this.revalidate();
        this.repaint();
    }

    private void startGame(String name){
        this.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel();
        this.setLayout(new BorderLayout());
        this.add(gamePanel);
        refreshFrame();
        GameLoop gameLoop = new GameLoop(gamePanel);
        gameLoop.start();
    }
}
