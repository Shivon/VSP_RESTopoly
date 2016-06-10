package client.view;

import client.logic.BuyPlaceLogic;
import clientUI.BuyPlaceWindowUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jana Mareike on 09.06.2016.
 */
public class BuyPlaceWindow {

    private BuyPlaceWindowUI _buyPlaceWindowUI;
    private BuyPlaceLogic _buyPlaceLogic;

    public BuyPlaceWindow(){
        _buyPlaceWindowUI = new BuyPlaceWindowUI();
        _buyPlaceLogic = new BuyPlaceLogic(_buyPlaceWindowUI);
    }

    public void registerBuyPlaceButton(){
        _buyPlaceWindowUI.getPlaceToBuyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//      TODO
                _buyPlaceLogic.closeBuyPlaceWindow();
            }
        });
    }

    public void registerDontBuyPlaceButton(){
        _buyPlaceWindowUI.getDontBuyPlaceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//      TODO
                _buyPlaceLogic.closeBuyPlaceWindow();
            }
        });
    }

    public void showPlaceToBuy(){
//        TODO
        _buyPlaceWindowUI.getPlaceToBuyArea().setText("You can buy: ");
    }
}
