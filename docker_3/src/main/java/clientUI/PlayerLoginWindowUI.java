package clientUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class PlayerLoginWindowUI {
    private JFrame _playerFrame;
    private JPanel _playerPawnPanel;
    private JPanel _availablePawnsPanel;
    private JTextArea _availablePawns;
    private JPanel _submitButtonPanel;
    private JTextArea _playerPawnArea;

    private JButton _submitButton;

    public PlayerLoginWindowUI(){

        _playerFrame = new JFrame("Choose your pawn: ");
        _availablePawns = new JTextArea();
        _availablePawnsPanel = new JPanel();
        _playerPawnPanel = new JPanel();
        _playerPawnArea = new JTextArea();
        _submitButtonPanel = new JPanel();
        _submitButton = new JButton("Submit");

        _playerPawnPanel.setBorder(new EmptyBorder(2,3,2,3));
        _playerPawnPanel.setPreferredSize(new Dimension(300,30));

        _playerPawnArea.setBackground(Color.DARK_GRAY);
        _playerPawnArea.setForeground(Color.ORANGE);
        _playerPawnArea.setWrapStyleWord(true);
        _playerPawnArea.setLineWrap(true);
        _playerPawnArea.setCaretPosition(0);
        _playerPawnArea.setPreferredSize(new Dimension(300, 30));

        _availablePawnsPanel.setBorder(new EmptyBorder(2,3,2,3));
        _availablePawnsPanel.setPreferredSize(new Dimension(300,300));

        _availablePawns.setBackground(Color.DARK_GRAY);
        _availablePawns.setForeground(Color.ORANGE);
        _availablePawns.setWrapStyleWord(true);
        _availablePawns.setLineWrap(true);
        _availablePawns.setPreferredSize(new Dimension(300,300));
        _availablePawns.setEnabled(false);

        _playerPawnArea.setEnabled(true);

        _playerPawnPanel.add(_playerPawnArea, BorderLayout.CENTER);

        _availablePawnsPanel.add(_availablePawns, BorderLayout.CENTER);

        _submitButtonPanel.setBorder(new EmptyBorder(2,3,2,3));
        _submitButtonPanel.setPreferredSize(new Dimension(300, 30));

        _submitButtonPanel.add(_submitButton, BorderLayout.CENTER);

        _playerFrame.setSize(500, 400);
        _playerFrame.add(_availablePawnsPanel, BorderLayout.NORTH);
        _playerFrame.add(_playerPawnPanel, BorderLayout.CENTER);
        _playerFrame.add(_submitButtonPanel, BorderLayout.SOUTH);
        _playerFrame.setVisible(false);
        _playerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JFrame getPlayerNameFrame(){ return _playerFrame;}

    public JTextArea getPlayerPawnArea(){ return _playerPawnArea;}

    public JTextArea getAvailablePawnsArea(){ return _availablePawns;}

    public JButton getSubmitButton(){ return _submitButton;}
}
