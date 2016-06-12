package client.view;

import client.adapter.GamesAdapter;
import client.adapter.UserAdapter;
import client.logic.GamesLogic;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.GameStatus;
import clientUI.NewGameWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static client.model.gameModels.GameStatus.registration;

/**
 * Created by Jana Mareike on 02.06.2016.
 */
public class NewGameWindow {

    private NewGameWindowUI _newGameWindowUI;
    private GamesAdapter _gamesAdapter;
    private String _gameName;
    private UserAdapter _userAdapter;
    private User _user;
    private GamesLogic _gamesLogic;

    public NewGameWindow(User user, GamesLogic gamesLogic){
        _newGameWindowUI = new NewGameWindowUI();
        _userAdapter = new UserAdapter();
        _newGameWindowUI.getLogInFrame().setVisible(true);
        _gamesAdapter = new GamesAdapter(null);
        _gamesLogic = gamesLogic;
        _user = user;
        registerSubmitGameName();
    }

    private void registerSubmitGameName() {
        _newGameWindowUI.getLogInSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hallo");
                if (!_newGameWindowUI.getLogInArea().getText().isEmpty()) {
                    _gameName = _newGameWindowUI.getLogInArea().getText();
                    try {
                        System.out.println("HALLO VOR DER SCHLEIFE");
                        System.out.println("hallo hallo in der Schleife");
                        for (Game game : _gamesAdapter.getGames()) {
                            if (game.getName().equals(_gameName)) {
                                JOptionPane.showMessageDialog(null, "game name not available", "choose an other name!",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        try {
                            Game newGame = new Game();
                            newGame.setName(_gameName);
                            newGame.setStatus(GameStatus.registration);
                            _gamesAdapter.postGames(newGame);
                            System.out.println(newGame);
                            newGame = _gamesAdapter.getGame(_gameName);
                            _newGameWindowUI.getLogInFrame().setVisible(false);
                            new PlayerLogInWindow(newGame, _user, _gamesLogic);
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
}
