package client.view;

import client.adapter.BanksAdapter;
import client.adapter.PlayerAdapter;
import client.logic.GamesLogic;
import client.logic.PlayerLogic;
import client.logic.WaitLogic;
import client.model.Accounts;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.service.ClientService;
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

    private PlayerLoginWindowUI _playerWindowUI;
    private Game _game;
    private User _user;
    private PlayerLogic _playerLogic;
    private GamesLogic _gamesLogic;
    private PlayerAdapter _playerAdapter;

    public PlayerLogInWindow(Game selectedGame, User user, GamesLogic gamesLogic) throws UnirestException {

        this._user = user;
        _playerWindowUI = new PlayerLoginWindowUI();
        this._game = selectedGame;
        _playerAdapter = new PlayerAdapter();
        _playerLogic = new PlayerLogic(_playerWindowUI, _game, _user);
        _gamesLogic = gamesLogic;
        System.out.println("constructor playerloginwindow - before showavailablepawns");
        _playerWindowUI.getAvailablePawnsArea().setText(_playerLogic.showAvailablePawns().toString());
        System.out.println("constructor playerloginwindow - after showavailablepawns");
        _playerWindowUI.getPlayerNameFrame().setVisible(true);
        registerSubmitPlayerPawn();
    }

    private void registerSubmitPlayerPawn() {
        _playerWindowUI.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_playerLogic.checkPlayerPawnEntered()){
                    try {
                        if(_playerLogic.checkIfPlayerPawnIsNotAvailable()){
                            JOptionPane.showMessageDialog(null, "pawn not available", "choose an other pawn!",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        _playerLogic.postPlayer();
                        _playerLogic.closePlayerWindow();
                        new StartGameWindow(_user, _game,_playerAdapter,_gamesLogic);
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
}
