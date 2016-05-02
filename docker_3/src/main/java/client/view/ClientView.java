package client.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jana Mareike on 02.05.2016.
 */
public class ClientView {

    private JFrame _mainFrame;
    private JPanel _allGamesPanel;
    private JTextArea _allGamesArea;


    private JFrame _playerNameFrame;
    private JTextArea _playerNameArea;
    private JButton _submitButton;



    public ClientView(){
        _mainFrame = new JFrame();
        _allGamesPanel = new JPanel();
        _allGamesArea = new JTextArea();


        _playerNameFrame = new JFrame();
        _playerNameArea = new JTextArea();
        _submitButton = new JButton();

        _allGamesPanel.setBorder(new EmptyBorder(2,3,2,3));
        _allGamesPanel.setPreferredSize(new Dimension(300,400));


        _allGamesArea.setBackground(Color.DARK_GRAY);
        _allGamesArea.setForeground(Color.ORANGE);
        _allGamesArea.setWrapStyleWord(true);
        _allGamesArea.setLineWrap(true);
        _allGamesArea.setPreferredSize(new Dimension(400,10000));
        _allGamesArea.setEnabled(false);

        _allGamesPanel.add(_allGamesArea, BorderLayout.CENTER );

        _mainFrame.setSize(450, 600);
        _mainFrame.add(_allGamesPanel, BorderLayout.NORTH);

        _mainFrame.setVisible(true);
        _mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
