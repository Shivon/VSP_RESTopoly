package client.view;

import client.adapter.PlayerAdapter;
import client.model.Players;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class PlayerLogInWindow {

    private String _playerPawn;
    private PlayerLoginWindowUI _playerWindowUI;
//    private GamesWindowUI _gamesWindowUI;
//    private VstTableModel _gamesTableModel;
    private PlayerAdapter _playerAdapter;
    private List<String> _pawnList;
    private Game _game;
    private User _user;

    public PlayerLogInWindow(Game selectedGame, User user) throws UnirestException {
//        this._gamesWindowUI = gamesWindow;
        this._user = user;
        _pawnList = Arrays.asList("Car", "Shoe", "Hat", "Dog", "Ship");
        System.out.println(_pawnList);
        _playerWindowUI = new PlayerLoginWindowUI();
//        _playerPawn = new String();
        this._game = selectedGame;
//        this._gamesTableModel = tableModel;
        _playerAdapter = new PlayerAdapter();
        System.out.println("constructor playerloginwindow - before showavailablepawns");
        _playerWindowUI.getAvailablePawnsArea().setText(showAvailablePawns().toString());
        System.out.println("constructor playerloginwindow - after showavailablepawns");
        _playerWindowUI.getPlayerNameFrame().setVisible(true);
        registerSubmitPlayerPawn();
    }

    private List<String> showAvailablePawns() throws UnirestException {
        System.out.println("hallo");
        System.out.println("game show "+_game);
        List<Player> playerList  = _playerAdapter.getPlayers(_game);
        System.out.println(playerList);
        if(!playerList.isEmpty()) {
            for (int i = 0; i <= playerList.size(); i++) {
                for (int j = 0; j < _pawnList.size(); j++) {
                    if (playerList.get(i).getPawn().equals(_pawnList.get(j))) {
                        _pawnList.remove(j);
                    }
                }
            }
        }
        System.out.println(""+ _pawnList);
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
                    //Semaphore
                    try {
                        System.out.println("before post");
                      _playerAdapter.postPlayer( _playerPawn, _game, _user);
                        System.out.println("after post");
                        System.out.println( _playerAdapter.getPlayers(_game));
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    //Semaphore Ende
                }else{
                    JOptionPane.showMessageDialog(null, "No pawn chosen", "enter pawn",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
