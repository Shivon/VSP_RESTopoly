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
    private GamesWindow _gamesWindow;
    private String _gameName;
    private UserAdapter _userAdapter;
    private User _user;
    private GamesLogic _gamesLogic;
//    private Game _game;
//    private PlayerLogInWindow _playerLoginWindow;

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
                        for (Game game : _gamesAdapter.getGames()) {
                            if (game.getName().equals(_gameName)) {
                                JOptionPane.showMessageDialog(null, "game name not available", "choose an other name!",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        Game game = new Game();
                        game.setName(_gameName);
                        game.setStatus(GameStatus.registration);
                        _gamesAdapter.postGames(game);
                        System.out.println(game);
                        game = _gamesAdapter.getGame(_gameName);
                        _newGameWindowUI.getLogInFrame().setVisible(false);
                        new PlayerLogInWindow(game, _user, _gamesLogic);
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
