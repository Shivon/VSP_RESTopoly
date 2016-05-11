package client.view;

import javax.persistence.Column;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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
    private JTable _allGamesTable;
    private DefaultTableModel _tableModel;
    private TableColumn _gamesColumn;
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
// TODO change _allGamesTable to JTextArea and add a Textarea to enter the GAmename to make it easier
//        Games
        _mainFrame = new JFrame("Available Games");
        _allGamesPanel = new JPanel();
        _allGamesTable =  new JTable(_tableModel);
        _takePartButtonPanel = new JPanel();
        _takePartButton = new JButton("Join Game");

        _takePartButtonPanel.setBorder(new EmptyBorder(2,3,2,3));
        _takePartButtonPanel.setPreferredSize(new Dimension(300,50));
        _takePartButtonPanel.add(_takePartButton);

        _allGamesPanel.setBorder(new EmptyBorder(2,3,2,3));
        _allGamesPanel.setPreferredSize(new Dimension(300,450));

        _allGamesTable.setBackground(Color.DARK_GRAY);
        _allGamesTable.setForeground(Color.ORANGE);
        _tableModel.addColumn(_gamesColumn);
        _allGamesTable.setRowSelectionAllowed(true);
//        _allGamesTable.getSelectedRow();
        _allGamesTable.setPreferredSize(new Dimension(400,10000));
        _allGamesTable.setEnabled(false);

        _allGamesPanel.add(_allGamesTable, BorderLayout.CENTER );

        _mainFrame.setSize(450, 550);
        _mainFrame.add(_allGamesPanel, BorderLayout.NORTH);
        _mainFrame.add(_takePartButtonPanel, BorderLayout.PAGE_END);

        _mainFrame.setVisible(false);
        _mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// Player
        _playerNameFrame = new JFrame("Enter your Player Name: ");
        _playerNamePanel = new JPanel();
        _playerNameArea = new JTextArea();
        _submitButtonPanel = new JPanel();
        _submitButton = new JButton("Submit");

        _playerNamePanel.setBorder(new EmptyBorder(2,3,2,3));
        _playerNamePanel.setPreferredSize(new Dimension(300,30));

        _playerNameArea.setBackground(Color.DARK_GRAY);
        _playerNameArea.setForeground(Color.ORANGE);
        _playerNameArea.setWrapStyleWord(true);
        _playerNameArea.setLineWrap(true);
        _playerNameArea.setCaretPosition(0);
        _playerNameArea.setPreferredSize(new Dimension(300, 30));
        _playerNameArea.setEnabled(true);

        _playerNamePanel.add(_playerNameArea, BorderLayout.CENTER);

        _submitButtonPanel.setBorder(new EmptyBorder(2,3,2,3));
        _submitButtonPanel.setPreferredSize(new Dimension(300, 30));

        _submitButtonPanel.add(_submitButton, BorderLayout.CENTER);

        _playerNameFrame.setSize(350, 130);
        _playerNameFrame.add(_playerNamePanel, BorderLayout.CENTER);
        _playerNameFrame.add(_submitButtonPanel, BorderLayout.SOUTH);
        _playerNameFrame.setVisible(false);
        _playerNameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JButton getLogInSubmit(){
        return _logInButton;
    }

    public DefaultTableModel getTableModel(){ return _tableModel;}

    public JFrame getLogInFrame(){return _logInFrame;}

    public JFrame getMainFrame(){
        return _mainFrame;
    }

    public JTextArea getLogInArea(){
        return _logInArea;
    }

    public JTable getAllGameTable(){
        return _allGamesTable;
    }

    public JButton getTakePartButton(){
        return _takePartButton;
    }

    public JFrame getPlayerNameFrame(){ return _playerNameFrame;}

    public JTextArea getPlayerNameArea(){ return _playerNameArea;}
}
