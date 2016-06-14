package clientUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jana Mareike on 07.06.2016.
 */
public class WaitWindowUI {
    private JFrame _waitFrame;
    private JPanel _waitPanel;
    private JTextArea _waitTextArea;
    private JTextArea _saldoTextArea;
    private JPanel _saldoPanel;
    private JLabel _saldoLabel;

    public WaitWindowUI(){
        _waitFrame = new JFrame("Waiting for your turn");
        _waitPanel = new JPanel();
        _waitTextArea = new JTextArea();
        _saldoTextArea = new JTextArea();
        _saldoPanel = new JPanel();
        _saldoLabel = new JLabel("Your Saldo: ");

        _waitPanel.setBorder(new EmptyBorder(0,3,5,3));
        _waitPanel.setPreferredSize(new Dimension(400,500));

        _waitTextArea.setBackground(Color.DARK_GRAY);
        _waitTextArea.setForeground(Color.ORANGE);
        _waitTextArea.setWrapStyleWord(true);
        _waitTextArea.setLineWrap(true);
        _waitTextArea.setCaretPosition(0);
        _waitTextArea.setPreferredSize(new Dimension(400, 500));
        _waitTextArea.setEnabled(false);
        _waitTextArea.setText("Wait until your turn to roll");

        _saldoPanel.setBorder( new EmptyBorder(2,3,2,3));
        _saldoPanel.setPreferredSize(new Dimension(200, 75));

        _saldoTextArea.setBackground(Color.DARK_GRAY);
        _saldoTextArea.setForeground(Color.ORANGE);
        _saldoTextArea.setLineWrap(true);
        _saldoTextArea.setWrapStyleWord(true);
        _saldoTextArea.setPreferredSize(new Dimension(100, 50));

        _saldoPanel.add(_saldoLabel, BorderLayout.WEST);
        _saldoPanel.add(_saldoTextArea, BorderLayout.EAST);

        _waitPanel.add(_waitTextArea, BorderLayout.CENTER);

        _waitFrame.setSize(450, 550);
        _waitFrame.add(_saldoPanel, BorderLayout.NORTH);
        _waitFrame.add(_waitPanel, BorderLayout.CENTER);
        _waitFrame.setVisible(false);
        _waitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JTextArea getWaitText(){ return _waitTextArea;}

    public JTextArea getSaldoTextArea(){return _saldoTextArea;}

    public JFrame getWaitFrame(){ return _waitFrame;}

}
