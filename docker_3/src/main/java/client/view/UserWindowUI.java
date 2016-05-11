package client.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class UserWindowUI {

    private JFrame _logInFrame;
    private JPanel _logInPanel;
    private JTextArea _logInArea;
    private JPanel _logInButtonPanel;
    private JButton _logInButton;
    private JLabel _logInLabel;

    public UserWindowUI(){
        _logInFrame = new JFrame("logIn");
        _logInPanel = new JPanel();
        _logInLabel = new JLabel("Enter your name: ");
        _logInArea = new JTextArea();
        _logInButtonPanel = new JPanel();
        _logInButton = new JButton("Submit");

        _logInPanel.setBorder(new EmptyBorder(2,3,2,3));
        _logInPanel.setPreferredSize(new Dimension(300,30));

        _logInArea.setBackground(Color.DARK_GRAY);
        _logInArea.setForeground(Color.ORANGE);
        _logInArea.setWrapStyleWord(true);
        _logInArea.setLineWrap(true);
        _logInArea.setCaretPosition(0);
        _logInArea.setPreferredSize(new Dimension(300, 30));
        _logInArea.setEnabled(true);

        _logInPanel.add(_logInArea, BorderLayout.CENTER);

        _logInButtonPanel.setBorder(new EmptyBorder(2,3,2,3));
        _logInButtonPanel.setPreferredSize(new Dimension(300, 30));

        _logInButtonPanel.add(_logInButton, BorderLayout.CENTER);

        _logInFrame.setSize(350, 130);
        _logInFrame.add(_logInLabel, BorderLayout.NORTH);
        _logInFrame.add(_logInPanel, BorderLayout.CENTER);
        _logInFrame.add(_logInButtonPanel, BorderLayout.SOUTH);
        _logInFrame.setVisible(true);
        _logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JButton getLogInSubmit(){
        return _logInButton;
    }

    public JFrame getLogInFrame(){
        return _logInFrame;
    }

    public JTextArea getLogInArea(){
        return _logInArea;
    }
}
