package client.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jana Mareike on 02.05.2016.
 */
public class ClientView {

    private JFrame _logInFrame;
    private JPanel _logInPanel;
    private JTextArea _logInArea;
    private JPanel _logInButtonPanel;
    private JButton _logInButton;
    private JLabel _logInLabel;

    private JFrame _mainFrame;
    private JPanel _allGamesPanel;
    private JTextArea _allGamesArea;
    private JPanel _takePartButtonPanel;
    private JButton _takePartButton;


    private JFrame _playerNameFrame;
    private JPanel _playerNamePanel;
    private JPanel _submitButtonPanel;
    private JTextArea _playerNameArea;
    private JButton _submitButton;



    public ClientView(){
//Log In
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

//        Games
        _mainFrame = new JFrame("Available Games");
        _allGamesPanel = new JPanel();
        _allGamesArea = new JTextArea();
        _takePartButtonPanel = new JPanel();
        _takePartButton = new JButton("Join Game");

        _takePartButtonPanel.setBorder(new EmptyBorder(2,3,2,3));
        _takePartButtonPanel.setPreferredSize(new Dimension(300,50));
        _takePartButtonPanel.add(_takePartButton);

        _allGamesPanel.setBorder(new EmptyBorder(2,3,2,3));
        _allGamesPanel.setPreferredSize(new Dimension(300,450));

        _allGamesArea.setBackground(Color.DARK_GRAY);
        _allGamesArea.setForeground(Color.ORANGE);
        _allGamesArea.setWrapStyleWord(true);
        _allGamesArea.setLineWrap(true);
        _allGamesArea.setPreferredSize(new Dimension(400,10000));
        _allGamesArea.setEnabled(false);

        _allGamesPanel.add(_allGamesArea, BorderLayout.CENTER );

        _mainFrame.setSize(450, 550);
        _mainFrame.add(_allGamesPanel, BorderLayout.NORTH);
        _mainFrame.add(_takePartButtonPanel, BorderLayout.PAGE_END);

        _mainFrame.setVisible(false);
        _mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Player
        _playerNameFrame = new JFrame();
        _playerNamePanel = new JPanel();
        _playerNameArea = new JTextArea();
        _submitButtonPanel = new JPanel();
        _submitButton = new JButton("Submit");
    }

    public JButton getLogInSubmit(){
        return _logInButton;
    }

    public JFrame getLogInFrame(){
        return _logInFrame;
    }

    public JFrame getMainFrame(){
        return _mainFrame;
    }

    public JTextArea getLogInArea(){
        return _logInArea;
    }

    public JTextArea getAllGamesArea(){
        return _allGamesArea;
    }
}
