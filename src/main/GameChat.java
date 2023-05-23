package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameChat extends JFrame implements ActionListener{
    // Elements declaration
    JTextArea chatHistory;
    JTextField inputField;
    JButton btn;

    GameChat(){
        // set 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Chat");
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(330, 600));

        // chat text field
        chatHistory = new JTextArea();
        chatHistory.setPreferredSize(new Dimension(280, 500));
        chatHistory.setEditable(false);
        chatHistory.setLineWrap(true);
        chatHistory.setWrapStyleWord(true);

        // input text field
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(200, 30));
        inputField.setText("Message...");
        inputField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){inputField.setText("");}
        });
        inputField.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){updateChat();}
        });

        // button for chat
        btn = new JButton("Enter");
        btn.setPreferredSize(new Dimension(75, 30));
        btn.addActionListener(this);

        // add inputField to the layout
        this.add(chatHistory);
        this.add(inputField);
        this.add(btn);

        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt){
        if(evt.getSource() == btn){updateChat();}
    }

    public void updateChat(){
        String tempStr = inputField.getText();

        if(!tempStr.isBlank()){
            chatHistory.append("\n" + tempStr);
            inputField.setText("");
        }
    }

    // public static void main(String[] args){
    //     GameChat gameChat = new GameChat();
    // }    
}


/*
    OTHER USEFUL METHODS:
    // set string of text field -> input: String
    textfield.setText("Text field");

    // captures the text in the field
    textfield.getText()

    // determines if a textfield is editable we can use this to create two text fields
    // the first being the text field that will show the history of the chat, then we set editable to false 
    textfield.setEditable(<bool>); 

    // for styling
    textfield.setFont();
    textfield.setForeground();
    textfield.setCaretColor();
 */