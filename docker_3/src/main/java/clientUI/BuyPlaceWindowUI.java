package clientUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jana Mareike on 09.06.2016.
 */
public class BuyPlaceWindowUI {

    private JFrame _mainFrame;
    private JPanel _placeToBuyPanel;
    private JTextArea _placeToBuyArea;
    private JPanel _placeToBuyButtonPanel;
    private JButton _buyPlaceButton;
    private  JButton _dontBuyButton;



    public BuyPlaceWindowUI(){
        _mainFrame = new JFrame("You can buy a new place");

//        _placeToBuyPanel = new JPanel();
//        _placeToBuyArea = new JTextArea("You are on Field: ");

        _placeToBuyButtonPanel = new JPanel();
        _buyPlaceButton = new JButton("Buy Place");
        _dontBuyButton = new JButton("Don´t buy and continue");

        _placeToBuyButtonPanel.setBorder(new EmptyBorder(2,3,2,3));
        _placeToBuyButtonPanel.setPreferredSize(new Dimension(100,50));
        _placeToBuyButtonPanel.add(_buyPlaceButton);
        _placeToBuyButtonPanel.add(_dontBuyButton);

//        _placeToBuyPanel.setBorder(new EmptyBorder(2,3,2,3));
//        _placeToBuyPanel.setPreferredSize(new Dimension(100,50));
//        _placeToBuyPanel.add(_placeToBuyArea, BorderLayout.CENTER );

        _mainFrame.setSize(350, 100);
//        _mainFrame.add(_placeToBuyPanel, BorderLayout.NORTH);
        _mainFrame.add(_placeToBuyButtonPanel, BorderLayout.CENTER);

        _mainFrame.setVisible(false);
        _mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JFrame getMainFrame(){
        return _mainFrame;
    }

    public JButton getPlaceToBuyButton(){
        return _buyPlaceButton;
    }

//    public JTextArea getPlaceToBuyArea(){ return _placeToBuyArea; }

    public JButton getDontBuyPlaceButton() { return _dontBuyButton; }
}
