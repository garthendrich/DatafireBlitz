package main;

import java.awt.*;
import javax.swing.*;

import network.Client;
import network.Server;

import java.awt.event.*;

public class Lobby extends JFrame{
    public static int WINDOW_WIDTH = 960;
    public static int WINDOW_HEIGHT = 540;

    Server server;
    Client client;
    GameChat gameChat;

    public Lobby(){
        this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        this.setTitle("Datafire Blitz");
        this.setResizable(false);
        this.setLocationRelativeTo(null); // center window on screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
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

                server = new Server();
                String ipAddress = server.getIpAddress();
                int portNumber = server.getPortNumber();

                if (name.length() > 0)
                    lobbyPage(name, ipAddress, portNumber);
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

    private void lobbyPage(String name, String ipAddress, int portNumber) {
        client = new Client(name, ipAddress, portNumber);
        client.attachLobbyPage(this);

        gameChat = new GameChat();
        gameChat.setFocusable(true);

        client.attachChat(gameChat);
        gameChat.attachClient(client);

        this.getContentPane().removeAll();

        // ! add chat UI to lobby page:
        // this.add(gameChat, BorderLayout.EAST);

        String[] players = {name};
        String label = "";
        for (int i = 0; i < players.length; i++) {
            label += players[i] + " ";
        }
        JLabel ipLabel = new JLabel("IP Address: " + ipAddress, SwingConstants.CENTER);
        JLabel portLabel = new JLabel("Port Number: " + Integer.toString(portNumber), SwingConstants.CENTER);
        JLabel playersLabel = new JLabel("Players: ".concat(label), SwingConstants.CENTER);
        JLabel confirmation = new JLabel("Start the game?", SwingConstants.CENTER);
        JButton b1 = new JButton("Start");
        JButton b2 = new JButton("Main menu");
        confirmation.setLabelFor(b1);
        b1.setBackground(Color.green);
        b2.setBackground(Color.cyan);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stopConnectionSearch();
                server.setVaryingTeams();
                server.startGame();
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

        if (server != null) {
        gbc.gridy = 3;
        this.add(confirmation,gbc);
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        this.add(b1,gbc);
        }

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
            public void actionPerformed(ActionEvent e) {
                String ipAddress = ipInput.getText();
                String portNumber = portInput.getText();

                // ! verify if input for port number is a number

                if (ipAddress.length() > 0 && portNumber.length() > 0) {
                    lobbyPage(name, ipAddress, Integer.parseInt(portNumber));
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

    public void startGame() {
        this.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel();
        new GameKeyInputs(gamePanel, client);
        addListeners(gameChat, gamePanel);

        GameState gameState = new GameState();
        client.attachGameState(gameState);

        new GameLoop(gameState, gamePanel);

        this.setLayout(new BorderLayout());

        this.add(gamePanel);
        this.add(gameChat, BorderLayout.EAST);

        gamePanel.requestFocusInWindow();

        refreshFrame();
    }

    private void addListeners(GameChat gameChat, GamePanel gamePanel){
        gamePanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            
            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    gameChat.inputField.setText("");
                    gameChat.inputField.requestFocusInWindow();
                }
            }
        });

        gameChat.inputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            
            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    gamePanel.requestFocusInWindow();
                }
            }
        });
    }
}
