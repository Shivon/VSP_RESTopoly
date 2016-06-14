package client.view;

import client.adapter.PlayerAdapter;
import client.logic.GamesLogic;
import client.model.Client;
import client.model.User;
import client.model.gameModels.Game;
import client.service.ClientService;
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
    private ClientService _clientService;

    public StartGameWindow(User user, Game game, PlayerAdapter playerAdapter,
                           GamesLogic gamesLogic, ClientService clientService) throws UnirestException {
        _user = user;
        _game = game;
        _gamesLogic = gamesLogic;
        _playerAdapter = playerAdapter;
        _clientService = clientService;

        _startGameWindowUI = new StartGameWindowUI();
        _startGameWindowUI.getStartGameFrame().setVisible(true);
        registerStartGame(_user, _game);

    }

    public void handleGameStartPost() throws UnirestException {
        _gamesLogic.startWaitWindow(_user, _game, _playerAdapter, _clientService);
        _startGameWindowUI.getStartGameFrame().setVisible(false);
    }

    public void registerStartGame(User user, Game game){
        _startGameWindowUI.getStartGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println(game);
                    _gamesLogic.startGame(game);
                    _startGameWindowUI.getStartGameFrame().setVisible(false);
                    _gamesLogic.startWaitWindow(user, game, _playerAdapter, _clientService);

                } catch (UnirestException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }
}
