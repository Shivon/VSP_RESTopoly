package clientUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jana Mareike on 11.06.2016.
 */
public class StartGameWindowUI {

    private JFrame _StartGameFrame;
    private JPanel _startGamePanel;
    private JButton _startGameButton;

    public StartGameWindowUI(){
        _StartGameFrame = new JFrame("Start your Game");
        _startGamePanel = new JPanel();
        _startGameButton = new JButton("Start Game");

        _startGamePanel.setBorder(new EmptyBorder(0,3,5,3));
        _startGamePanel.setPreferredSize(new Dimension(250, 75));
        _startGamePanel.add(_startGameButton, BorderLayout.CENTER);

        _StartGameFrame.setSize(250, 75);
        _StartGameFrame.add(_startGamePanel, BorderLayout.CENTER);
        _StartGameFrame.setVisible(false);
        _StartGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JButton getStartGameButton(){ return _startGameButton; }

    public JFrame getStartGameFrame(){ return _StartGameFrame;}
}
