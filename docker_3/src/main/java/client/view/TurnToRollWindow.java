package client.view;

import client.adapter.BanksAdapter;
import client.adapter.BoardsAdapter;
import client.adapter.BrokerAdapter;
import client.adapter.PlayerAdapter;
import client.logic.*;
import client.model.boardModels.Place;
import client.model.gameModels.Player;
import clientUI.BuyPlaceWindowUI;
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
    private TurnToRollLogic _turnToRollLogic;
    private boolean _rolled = false;
    private BoardsAdapter _boardsAdapter;
    private BuyLogic _buyLogic;
    private WaitWindow _waitWindow;
    private BuyPlaceWindowUI _buyPlaceWindowUI;
    private WaitLogic _waitLogic;
    private BrokerAdapter _brokerAdapter;
    private PlayerAdapter _playerAdapter;
    private BanksAdapter _banksAdapter;

    public TurnToRollWindow(TurnToRollWindowUI turnToRollWindowUI, PlayerLogic playerLogic, GamesLogic gamesLogic,
                            UserLogic userLogic, TurnToRollLogic turnToRollLogic, BoardsAdapter boardsAdapter,
                            BuyLogic buyLogic, WaitWindow waitWindow, BuyPlaceWindowUI buyPlaceWindowUI,
                            WaitLogic waitLogic, BrokerAdapter brokerAdapter, PlayerAdapter playerAdapter,
                            BanksAdapter banksAdapter) throws UnirestException {
        _turnToRollWindowUI = turnToRollWindowUI;
        _playerLogic = playerLogic;
        _gamesLogic = gamesLogic;
        _userLogic = userLogic;
        _turnToRollLogic = turnToRollLogic;
        _boardsAdapter = boardsAdapter;
        _buyLogic = buyLogic;
        _waitWindow = waitWindow;
        _buyPlaceWindowUI = buyPlaceWindowUI;
        _waitLogic = waitLogic;
        _brokerAdapter = brokerAdapter;
        _playerAdapter = playerAdapter;
        _banksAdapter = banksAdapter;

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

                        _waitWindow.getWaitWindowUI().getWaitText().setText(_waitWindow.getWaitWindowUI().getWaitText().getText()
                                + "\n" + "Now you are on Field: " + fieldName);

                        Place place = _boardsAdapter.getCurrentPlace(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser());
                        if(_buyLogic.checkPlaceIsBuyable()){
                            _waitWindow.getWaitWindowUI().getWaitText().setText(_waitWindow.getWaitWindowUI().getWaitText().getText()
                        + "\n" + "You can buy "  + fieldName + " for " + _buyLogic.getPrice());
                             new BuyPlaceWindow(_buyPlaceWindowUI, _waitWindow, _waitLogic, _gamesLogic, _userLogic
                        , _brokerAdapter, _buyLogic, _playerAdapter, _banksAdapter, _boardsAdapter);
                        }else {
                            _waitWindow.getWaitWindowUI().getWaitText().setText(_waitWindow.getWaitWindowUI().getWaitText().getText()
                                    + "\n" + "You have to pay " + _buyLogic.getRent() + " rent for" + fieldName);

                            _rolled = true;
                            _turnToRollWindowUI.getDiceButton().setText("Close");
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        _playerLogic.setPlayerToReady();
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    _waitWindow.getWaitWindowUI().getWaitText().setText(_waitWindow.getWaitWindowUI().getWaitText().
                            getText() + "\n Wait until your turn to roll");
                    _waitWindow.getWaitWindowUI().getWaitText().setForeground(Color.ORANGE);
                } else {
                    _turnToRollWindowUI.getDiceFrame().setVisible(false);
                }
            }
        });
    }

    public TurnToRollWindowUI getTurnToRollWindowUI(){ return _turnToRollWindowUI; }
}
