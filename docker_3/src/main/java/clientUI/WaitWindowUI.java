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

    public WaitWindowUI(){
        _waitFrame = new JFrame("Waiting for your turn");
        _waitPanel = new JPanel();
        _waitTextArea = new JTextArea();

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

        _waitPanel.add(_waitTextArea, BorderLayout.CENTER);

        _waitFrame.setSize(450, 550);
        _waitFrame.add(_waitPanel, BorderLayout.CENTER);
        _waitFrame.setVisible(false);
        _waitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JTextArea getWaitText(){ return _waitTextArea;}

    public JFrame getWaitFrame(){ return _waitFrame;}

}
