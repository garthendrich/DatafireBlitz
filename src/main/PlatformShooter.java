package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// ! temporary class name
public class PlatformShooter {
    static int WINDOW_WIDTH = 960;
    static int WINDOW_HEIGHT = 540;

    public static void main(String[] args) {
        JFrame lobby = homeLobby(); 
        lobby.setVisible(true);

        // JFrame window = createWindow();
        // GamePanel gamePanel = new GamePanel();
        // window.add(gamePanel);

        // window.setVisible(true);

        // GameLoop gameLoop = new GameLoop(gamePanel);
        // gameLoop.start();
    }

    private static JFrame createWindow() {
        JFrame window = new JFrame();
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setTitle("");
        window.setResizable(false);
        window.setLocationRelativeTo(null); // center window on screen
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return window;
    }

    private static JFrame homeLobby(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setLayout(null);
        frame.setBackground(Color.gray);
        frame.setLocationRelativeTo(null);
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.setBounds(100,0,200,200);
        panel2.setBounds(100,200,200,100);
        TextField username, address, portNumber;
        username = new TextField(20);
        address = new TextField(20);
        portNumber = new TextField(20);
        JButton b1=new JButton("Create Lobby");
        b1.setBounds(50,100,80,30);
        b1.setBackground(Color.yellow);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String name = username.getText();
                String ip = address.getText();
                String port = address.getText();
                if(name.length() != 0 && ip.length() != 0 && port.length() != 0){
                    // frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    frame.setVisible(false);
                    waitingLobby(name, ip, port, "host");
                }
            }
        });
        JButton b2=new JButton("Join Lobby");
        b2.setBounds(50,150,80,30);
        b2.setBackground(Color.green);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String name = username.getText();
                String ip = address.getText();
                String port = address.getText();
                if(name.length() != 0 && ip.length() != 0 && port.length() != 0){
                    // frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    frame.setVisible(false);
                    waitingLobby(name, ip, port, "client");
                }
            }
        });
        
        JLabel user, addressLabel, number;
        user = new JLabel("Enter player name: ");
        addressLabel = new JLabel("Enter your IP Addres: ");
        number = new JLabel("Enter your port number: ");
        user.setLabelFor((username));
        addressLabel.setLabelFor(address);
        number.setLabelFor(portNumber);
        panel1.add(user);
        panel1.add(username);
        panel1.add(addressLabel);
        panel1.add(address);
        panel1.add(number);
        panel1.add(portNumber);
        panel2.add(b1);
        panel2.add(b2);
        frame.add(panel1);
        frame.add(panel2);
        return frame;
    }
    private static void waitingLobby(String name, String ip, String port, String status){
        JFrame frame = new JFrame();
        String[] players = {name, "Ji", "Andi", "Paw"};
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setLayout(null);
        JLabel address = new JLabel("IP Address: " +ip);
        address.setBounds(100,0,200,50);
        JLabel p = new JLabel("Port No.: " +port);
        p.setBounds(100,50,200,50);
        String label = "";
        for(int i=0; i<4; i++) label += players[i] + " ";
        JLabel names = new JLabel("Players: " + label);
        names.setBounds(100,100,200,50);
        JButton b1 = new JButton();
        b1.setBounds(100,150,200,50);
        if(status == "host") b1.setText("Start Lobby");
        else b1.setText("Ready to enter?");
        b1.setBackground(Color.CYAN);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                frame.setVisible(false);
                JFrame window = createWindow();
                GamePanel gamePanel = new GamePanel();
                window.add(gamePanel);
        
                window.setVisible(true);
        
                GameLoop gameLoop = new GameLoop(gamePanel);
                gameLoop.start();
            }
        });

        frame.add(address);
        frame.add(p);
        frame.add(names);
        frame.add(b1);
        frame.setVisible(true);
    }
}

