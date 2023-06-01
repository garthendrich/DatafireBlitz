package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import network.Client;

public class GameChat extends JPanel{
    // Elements declaration
    JTextArea chatHistory;
    JTextField inputField;
    JScrollPane scroller;

    String playerName;
    Client client;

    GameChat(String playerName) {
        this.playerName = playerName;

        // set 
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(225, 540));
        this.setBackground(new Color(0,0,0,64));
        this.setVisible(true);
        
        chatHistory = createChatHistory();          // chat text field
        scroller = createScroller(chatHistory);     // scroller
        inputField = createInputField();            // input text field
        
        // add inputField to the layout
        this.add(scroller);
        this.add(inputField);        
    }

    public void updateChat(String message){
        if(!message.isBlank()){
            chatHistory.append("\n" + message);
            inputField.setText("");
        }
    }   

    private static JTextArea createChatHistory(){
        JTextArea newChat = new JTextArea();
        newChat.setEditable(false);
        newChat.setLineWrap(true);
        newChat.setWrapStyleWord(true);
        return newChat;
    }

    private static JScrollPane createScroller(JTextArea window){
        JScrollPane newScroller = new JScrollPane(window);
        newScroller.setPreferredSize(new Dimension(200, 450));
        newScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return newScroller;
    }

    private JTextField createInputField(){
        JTextField newField = new JTextField();
        newField.setPreferredSize(new Dimension(200, 30));
        newField.setText("Message...");
        newField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){newField.setText("");}
        });
        newField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.send(playerName + ": " + inputField.getText());
            }
        });
        return newField;
    }

    void attachClient(Client client) {
        this.client = client;
    }
}
