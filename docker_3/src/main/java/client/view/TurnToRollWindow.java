package client.view;

import client.adapter.*;
import client.logic.GamesLogic;
import client.logic.PlayerLogic;
import client.logic.TurnToRollLogic;
import client.logic.UserLogic;
import client.model.*;
import client.model.Event;
import client.model.boardModels.Place;
import client.model.boardModels.Roll;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.model.gameModels.Ready;
import clientUI.TurnToRollWindowUI;
//import com.mashape.unirest.http.Unirest;
import clientUI.WaitWindowUI;
import com.jayway.restassured.response.Response;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                        Response event = _turnToRollLogic.getEventAfterDiceRoll();
//                        _waitWindowUI.getWaitText().setText(_waitWindowUI.getWaitText().getText()
//                                + "\n" + event.getName() + ": " + event.getReason() );

//                        TODO muss ich mir selber das Feld holen und checken, ob es schon belegt ist
//                        oder macht das das Events? Bekomme ich vom Events einen Post, dass ich das
//                        Feld kaufen kann?
//                        Place place = _boardAdapter.getCurrentPlace(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser());
//                        if(_brokerAdapter.getOwner(place) == null){
//                             new BuyPlaceWindow(place, _player, _userLogic.getCurrentUser(), _gamesLogic.getCurrentGame());
//                        }

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
