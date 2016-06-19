package client.view;

import client.logic.GamesLogic;
import client.model.gameModels.Game;
import clientUI.NewGameWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jana Mareike on 02.06.2016.
 */
public class NewGameWindow {

    private NewGameWindowUI _newGameWindowUI;
    private String _gameName;
    private PlayerLogInWindow _playerLoginWindow;
    private GamesLogic _gameLogic;

    public NewGameWindow(NewGameWindowUI newGameWindowUI, PlayerLogInWindow playerLoginWindow,
                         GamesLogic gameLogic){

        _gameLogic = gameLogic;
        _newGameWindowUI = newGameWindowUI;
        _playerLoginWindow = playerLoginWindow;

        registerSubmitGameName();
    }

    private void registerSubmitGameName() {
        _newGameWindowUI.getLogInSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!_newGameWindowUI.getLogInArea().getText().isEmpty()) {
                    _gameName = _newGameWindowUI.getLogInArea().getText();
                    try {
                        for (Game game : _gameLogic.getGames()) {
                            if (game.getName().equals(_gameName)) {
                                JOptionPane.showMessageDialog(null, "game name not available", "choose an other name!",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        try {
                            _gameLogic.createNewGame(_gameName);
                            _newGameWindowUI.getLogInFrame().setVisible(false);
                            _playerLoginWindow.getPlayerLoginWindowUI().getPlayerNameFrame().setVisible(true);
                        } catch (UnirestException e1) {
                            e1.printStackTrace();
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No game name", "no game name",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public NewGameWindowUI getNewGamesWindowUI(){ return _newGameWindowUI;}
}
