package client.view;

import client.adapter.BanksAdapter;
import client.adapter.PlayerAdapter;
import client.model.Accounts;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import clientUI.PlayerLoginWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class PlayerLogInWindow {

    private String _playerPawn;
    private PlayerLoginWindowUI _playerWindowUI;
    private PlayerAdapter _playerAdapter;
    private BanksAdapter _banksAdapter;
    private Accounts _account;
    private List<String> _pawnList;
    private Game _game;
    private User _user;
    private TurnToRollWindow _turnToRollWindow;

    public PlayerLogInWindow(Game selectedGame, User user) throws UnirestException {
        this._user = user;
        _pawnList = new ArrayList<>();
        _pawnList.add("Car");
        _pawnList.add("Shoe");
        _pawnList.add("Hat");
        _pawnList.add("Dog");
        _pawnList.add("Ship");
        _pawnList.add("Flatiron");

        System.out.println(_pawnList);
        _playerWindowUI = new PlayerLoginWindowUI();
        this._game = selectedGame;
        _playerAdapter = new PlayerAdapter();
        _banksAdapter = new BanksAdapter();
        System.out.println("constructor playerloginwindow - before showavailablepawns");
        _playerWindowUI.getAvailablePawnsArea().setText(showAvailablePawns().toString());
        System.out.println("constructor playerloginwindow - after showavailablepawns");
        _playerWindowUI.getPlayerNameFrame().setVisible(true);
        registerSubmitPlayerPawn();
    }

    private List<String> showAvailablePawns() throws UnirestException {
        System.out.println("hallo");
        System.out.println("game show "+_game);
        if(!_game.getPlayers().isEmpty()){
            List<Player> playerList = _game.getPlayers();
            Set<String> playerPawnSet = new HashSet<>();
            System.out.println(playerList);
            for (Player player : playerList) {
                System.out.println("PLAYERLIST PAWN: " + player.getPawn());
                System.out.println("PLAYERLIST : " + playerList);
                playerPawnSet.add(player.getPawn());
                System.out.println(playerPawnSet);
                System.out.println(_pawnList.size());
            }

            Iterator<String> iterator = _pawnList.iterator();
            while(iterator.hasNext()){
                String pawn = iterator.next();
                if(playerPawnSet.contains(pawn)){
                    iterator.remove();
                }
            }
        }

        System.out.println("PAWNLIST"+ _pawnList);
        return _pawnList;
    }

    private void registerSubmitPlayerPawn() {
        _playerWindowUI.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!_playerWindowUI.getPlayerPawnArea().getText().isEmpty()) {
                    _playerPawn = _playerWindowUI.getPlayerPawnArea().getText();
                    System.out.println("playerpawn " + _playerPawn);
                    try {
                        if(!showAvailablePawns().contains(_playerPawn)){
                            JOptionPane.showMessageDialog(null, "pawn not available", "choose an other pawn!",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println(_playerPawn);
                    try {
                        System.out.println("before post");
                      _playerAdapter.postPlayer( _playerPawn, _game, _user);
                        System.out.println("after post im Submit");
                        System.out.println("get Player: " +  _playerAdapter.getPlayer(_game, _user));
                        _playerWindowUI.getPlayerNameFrame().setVisible(false);
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No pawn chosen", "enter pawn",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void playersTurnToRoll(User user, Game game) throws UnirestException {
        this._user = user;
        this._game = game;
        Player player = _playerAdapter.getPlayer(_game, _user);
//        if(Eingang Post von Boards){
//        isReady false
//        _turnToRollWindow = new TurnToRollWindow();


        while(! _playerAdapter.getPlayerIsReady(_game, player)){
            _playerAdapter.putPlayerTurn(_game, player);
        }
        _playerAdapter.putPlayer(_user, _game, _account, false);
        _turnToRollWindow = new TurnToRollWindow(_game, _user);
    }

}
