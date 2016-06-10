package client.logic;

import clientUI.BuyPlaceWindowUI;

/**
 * Created by Jana Mareike on 09.06.2016.
 */
public class BuyPlaceLogic {

    private BuyPlaceWindowUI _buyPlaceWindowUI;

    public BuyPlaceLogic(BuyPlaceWindowUI buyPlaceWindowUI){
        _buyPlaceWindowUI = buyPlaceWindowUI;
    }

    public void closeBuyPlaceWindow(){
        _buyPlaceWindowUI.getMainFrame().setVisible(false);
    }
}
