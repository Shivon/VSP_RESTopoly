package client.logic;

import client.adapter.PlayerAdapter;
import client.model.Client;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.model.gameModels.Ready;
import client.view.TurnToRollWindow;
import clientUI.WaitWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.awt.*;

/**
 * Created by Jana Mareike on 07.06.2016.
 */
public class WaitLogic {

    private WaitWindowUI _waitWindowUI;
    private TurnToRollWindow _turnToRollWindow;
    private User _user;
    private Game _game;
    private Player _player;
    private PlayerAdapter _playerAdapter;
    private Event _event;

    public WaitLogic(User user , Game game, PlayerAdapter playerAdapter) throws UnirestException {
        _waitWindowUI = new WaitWindowUI();
        _waitWindowUI.getWaitFrame().setVisible(true);
        _user = user;
        _game = game;
        _playerAdapter = playerAdapter;
        _player = _playerAdapter.getPlayer(_game, _user);
    }

    public void playerTurn(Player player) throws UnirestException {
        player.setReady(new Ready(false));
        _waitWindowUI.getWaitText().setText(getWindowText() + "Now it´s your turn to roll!");
        _waitWindowUI.getWaitText().setForeground(Color.RED);
        _turnToRollWindow = new TurnToRollWindow(_game, _user, _waitWindowUI);
    }

    public void registerAndShowEvent(Event event){
        _event = event;
        _waitWindowUI.getWaitText().setText(getWindowText() + "New Event!\n" + _event);


//        TODO
//        if(event = you are on a place which you can buy){
//        new BuyPlaceWindow
//        }
    }

    public String getWindowText(){
        return  _waitWindowUI.getWaitText().getText() + "\n";
    }
}
