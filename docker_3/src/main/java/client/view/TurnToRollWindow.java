package client.view;

import client.logic.GamesLogic;
import client.logic.PlayerLogic;
import client.logic.TurnToRollLogic;
import client.logic.UserLogic;
import client.model.gameModels.Player;
import clientUI.TurnToRollWindowUI;
import clientUI.WaitWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import com.mashape.unirest.http.Unirest;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class TurnToRollWindow {

    private TurnToRollWindowUI _turnToRollWindowUI;
    private GamesLogic _gamesLogic;
    private UserLogic _userLogic;
    private Player _player;
    private PlayerLogic _playerLogic;
    private WaitWindowUI _waitWindowUI;
    private TurnToRollLogic _turnToRollLogic;
    private boolean _rolled = false;

    public TurnToRollWindow(WaitWindowUI waitWindowUI,
                            TurnToRollWindowUI turnToRollWindowUI, PlayerLogic playerLogic, GamesLogic gamesLogic,
                            UserLogic userLogic, TurnToRollLogic turnToRollLogic) throws UnirestException {
        _turnToRollWindowUI = turnToRollWindowUI;
        _playerLogic = playerLogic;
        _waitWindowUI = waitWindowUI;
        _gamesLogic = gamesLogic;
        _userLogic = userLogic;
        _turnToRollLogic = turnToRollLogic;
//        _player = _playerAdapter.getPlayer(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser());

        registerRoll();
    }

    private void registerRoll(){
        _turnToRollWindowUI.getDiceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!_rolled) {
                    try {
                        int number = _turnToRollLogic.getNumberOfRolls();
                        _turnToRollWindowUI.getDiceNumber().setText("" + number);
                        String fieldName = _turnToRollLogic.getFieldAfterDiceRoll().getName();

                        _waitWindowUI.getWaitText().setText(_waitWindowUI.getWaitText().getText()
                                + "\n" + "Now you are on Field: " + fieldName);

//                        Place place = _boardAdapter.getCurrentPlace(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser());
//                        if(_buyLogic.checkPlaceIsBuyable()){
//                        _waitWindowUI.getWaitText().setText(_waitWindowUI.getWaitText().getText()
//                        + "\n" + "You can buy "  + fieldName + " for "_buyLogic.getCost() );
//                             new BuyPlaceWindow(buyPlaceWindowUI, waitWindow, waitLogic, gamesLogic,userLogic
//                        , brokerAdapter, buyLogic, playerAdapter);
//                        }else{
//                        _waitWindowUI.getWaitText().setText(_waitWindowUI.getWaitText().getText()
//                        + "\n" + "You have to pay "  + _buyLogic.getRent() +" rent for" + fieldName);

                        _rolled = true;
                        _turnToRollWindowUI.getDiceButton().setText("Close");
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        _playerLogic.setPlayerToReady();
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    _waitWindowUI.getWaitText().setText(_waitWindowUI.getWaitText().getText() + "\n Wait until your turn to roll");
                    _waitWindowUI.getWaitText().setForeground(Color.ORANGE);
                } else {
                    _turnToRollWindowUI.getDiceFrame().setVisible(false);
                }
            }
        });
    }

    public TurnToRollWindowUI getTurnToRollWindowUI(){ return _turnToRollWindowUI; }
}
