package client.view;

import client.adapter.PlayerAdapter;
import client.logic.GamesLogic;
import client.logic.PlayerLogic;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import clientUI.StartGameWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jana Mareike on 11.06.2016.
 */
public class StartGameWindow {

    private StartGameWindowUI _startGameWindowUI;
    private User _user;
    private Game _game;
    private GamesLogic _gamesLogic;
    private PlayerAdapter _playerAdapter;

    public StartGameWindow(User user, Game game, PlayerAdapter playerAdapter, GamesLogic gamesLogic){
        _startGameWindowUI = new StartGameWindowUI();
        _startGameWindowUI.getStartGameFrame().setVisible(true);
        _user = user;
        _game = game;
        _gamesLogic = gamesLogic;
        _playerAdapter = playerAdapter;
        registerStartGame(_user, _game);
    }

    public void registerStartGame(User user, Game game){
        _startGameWindowUI.getStartGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _user = user;
                _game = game;
                try {
                    System.out.println(game);
                    _gamesLogic.startGame(_game);
                    _gamesLogic.startWaitWindow(_user, _game, _playerAdapter);
                    _startGameWindowUI.getStartGameFrame().setVisible(false);
                } catch (UnirestException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }
}
