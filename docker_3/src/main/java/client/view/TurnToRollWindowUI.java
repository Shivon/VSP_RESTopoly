package client.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class TurnToRollWindowUI {

    private JFrame _diceFrame;
    private JPanel _dicePanel;
    private JTextArea _diceNumber;
    private JPanel _diceButtonPanel;
    private JButton _diceButton;

    public TurnToRollWindowUI(){
        _diceFrame = new JFrame("Dice");
        _dicePanel = new JPanel();
        _diceNumber = new JTextArea();
        _diceButtonPanel = new JPanel();
        _diceButton = new JButton("roll");

        _dicePanel.setBorder(new EmptyBorder(2,3,2,3));
        _dicePanel.setPreferredSize(new Dimension(300,30));

        _diceNumber.setBackground(Color.DARK_GRAY);
        _diceNumber.setForeground(Color.ORANGE);
        _diceNumber.setWrapStyleWord(true);
        _diceNumber.setLineWrap(true);
        _diceNumber.setCaretPosition(0);
        _diceNumber.setPreferredSize(new Dimension(300, 30));
        _diceNumber.setEnabled(false);

        _dicePanel.add(_diceNumber, BorderLayout.CENTER);

        _diceButtonPanel.setBorder(new EmptyBorder(2,3,2,3));
        _diceButtonPanel.setPreferredSize(new Dimension(300, 30));

        _diceButtonPanel.add(_diceButton, BorderLayout.CENTER);

        _diceFrame.setSize(350, 130);
        _diceFrame.add(_diceButtonPanel, BorderLayout.CENTER);
        _diceFrame.add(_dicePanel, BorderLayout.SOUTH);
        _diceFrame.setVisible(false);
        _diceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JButton getDiceButton(){ return _diceButton;}

    public JTextArea getDiceNumber(){ return _diceNumber;}
}
