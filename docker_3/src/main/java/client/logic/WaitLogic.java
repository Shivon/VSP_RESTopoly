package client.logic;

import client.adapter.BanksAdapter;
import client.adapter.PlayerAdapter;
import client.model.Accounts;
import client.model.Client;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.model.gameModels.Ready;
import client.view.TurnToRollWindow;
import clientUI.StartGameWindowUI;
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
    private BanksAdapter _banksAdapter;
    private StartGameWindowUI _startGameWindowUI;

    public WaitLogic(User user , Game game, PlayerAdapter playerAdapter) throws UnirestException {
        _user = user;
        _game = game;
        _playerAdapter = playerAdapter;


        _waitWindowUI = new WaitWindowUI();
        showGameHasStarted();
        _player = _playerAdapter.getPlayer(_game, _user);
        _banksAdapter = new BanksAdapter();
        setSaldo();
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

    }

    public void showGameHasStarted(){
        _waitWindowUI.getWaitText().setText("Game has started");
        _waitWindowUI.getWaitFrame().setVisible(true);
    }

    public String getWindowText(){
        return  _waitWindowUI.getWaitText().getText() + "\n";
    }

    public void setSaldo() throws UnirestException {
        System.out.println("PLAYER IM SALDO: " + _player);
        System.out.println("User IM SALDO: " + _user);
        System.out.println("GAME IM SALDO: " + _game);

        Accounts account = _banksAdapter.getAccount(_user, _game);
        int saldo = account.getSaldo();
          _waitWindowUI.getSaldoTextArea().setText("" + saldo);
    }
}
