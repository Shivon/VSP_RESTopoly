package client.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class PlayerLoginWindowUI {
    private JFrame _playerNameFrame;
    private JPanel _playerNamePanel;
    private JPanel _submitButtonPanel;
    private JTextArea _playerNameArea;
    private JButton _submitButton;

    public PlayerLoginWindowUI(){

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

    public JFrame getPlayerNameFrame(){ return _playerNameFrame;}

    public JTextArea getPlayerNameArea(){ return _playerNameArea;}

    public JButton getSubmitButton(){ return _submitButton;}
}
