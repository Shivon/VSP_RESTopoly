package client.view;

import client.adapter.*;
import client.model.*;
import client.model.boardModels.Place;
import client.model.boardModels.Roll;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.model.gameModels.Ready;
import clientUI.TurnToRollWindowUI;
//import com.mashape.unirest.http.Unirest;
import clientUI.WaitWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class TurnToRollWindow {

//    soll aufgehen, wenn man an der Reihe ist
    private TurnToRollWindowUI _turnToRollWindowUI;
    private IPAdresses _ipAdresses;
    private Game _game;
    private User _user;
    private DiceAdapter _diceAdapter;
    private Player _player;
    private PlayerAdapter _playerAdapter;
    private Ready _ready;
    private WaitWindowUI _waitWindowUI;
    private Roll _diceRoll1;
    private Roll _diceRoll2;
    private int _diceRoll1Number;
    private int _diceRoll2Number;
    private boolean _rolled = false;
    private BoardsAdapter _boardAdapter;
    private BrokerAdapter _brokerAdapter;

    public TurnToRollWindow(Game game, User user, WaitWindowUI waitWindowUI) throws UnirestException {
        _turnToRollWindowUI = new TurnToRollWindowUI();
        _ipAdresses = new IPAdresses();
        _game = game;
        _user = user;
        _diceAdapter = new DiceAdapter();
        _playerAdapter = new PlayerAdapter();
        _player = _playerAdapter.getPlayer(_game, _user);
        _ready = new Ready(true);
        _turnToRollWindowUI.getDiceFrame().setVisible(true);
        _waitWindowUI = waitWindowUI;
        _boardAdapter = new BoardsAdapter(_game, _user);
        _brokerAdapter = new BrokerAdapter();
        registerRoll();
    }

    private void registerRoll(){
        _turnToRollWindowUI.getDiceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!_rolled) {
                    try {
// würfeln und Betrag anzeigen
//                    2 mal get Dice roll, 1 post board roll mit number?
                        _diceRoll1 = new Roll();
                        _diceRoll2 = new Roll();
                        _diceRoll1Number =  _diceAdapter.getDiceRollNumber();
                        _diceRoll1.setNumber(_diceRoll1Number);
                        _diceRoll2Number = _diceAdapter.getDiceRollNumber();
                        _diceRoll2.setNumber(_diceRoll2Number);
                        int number = _diceRoll1Number + _diceRoll2Number;
                        _turnToRollWindowUI.getDiceNumber().setText("" + number);
                        client.model.Event event = _diceAdapter.postDiceRollOnBoard(_game, _user, _diceRoll1, _diceRoll2);
                        _waitWindowUI.getWaitText().setText(_waitWindowUI.getWaitText().getText()
                                + "\n" + event.getName() + ": " + event.getReason() );

//                        TODO muss ich mir selber das Feld holen und checken, ob es schon belegt ist
//                        oder macht das das Events? Bekomme ich vom Events einen Post, dass ich das
//                        Feld kaufen kann?
                        Place place = _boardAdapter.getCurrentPlace();
                        if(_brokerAdapter.getOwner(place) == null){
                             new BuyPlaceWindow(place, _player, _user, _game);
                        }

                        _rolled = true;
                        _turnToRollWindowUI.getDiceButton().setText("Close");
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        _playerAdapter.putPlayerReady(_game, _player);
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

}
