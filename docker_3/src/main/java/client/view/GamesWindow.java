package client.view;

import client.adapter.GamesAdapter;
import client.adapter.PlayerAdapter;
import client.logic.GamesLogic;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Ready;
import clientUI.GamesWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class GamesWindow {

    private GamesWindowUI _gamesWindowUI;
    private PlayerAdapter _playerAdapter;
    private GamesAdapter _gamesAdapter;
    private VstTableModel _gamesTableModel;
    private Game _selectedGame;
    private User _user;
    private Ready _ready;
    private GamesLogic _gamesLogic;

    public GamesWindow(User user) throws UnirestException {

        _gamesWindowUI = new GamesWindowUI();
        this._user = user;
        _gamesLogic = new GamesLogic(_gamesWindowUI, _user);
        _playerAdapter = new PlayerAdapter();
        _gamesAdapter = new GamesAdapter(_playerAdapter);
        _ready = new Ready(true);

        buildGamesWindowUI();
        registerSubmitJoinTheGame();
        registerNewGame();
        selectRow();
    }

    private void buildGamesWindowUI(){
        try {
            _gamesLogic.gamesWithRegistrationStatusTable();
        } catch (UnirestException e1) {
            e1.printStackTrace();
        }
        _gamesLogic.setGamesUIVisible();
    }

    private Game selectRow(){
        _gamesWindowUI.getAllGameTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = _gamesWindowUI.getAllGameTable().rowAtPoint(evt.getPoint());
                if (_gamesLogic.checkIfRowIsChosen(row)) {
                   _selectedGame=  _gamesLogic.getSelectedGameFromTable(row);
                }
            }
        });
        return _selectedGame;
    }

    private void registerSubmitJoinTheGame() {
        _gamesWindowUI.getTakePartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_gamesLogic.checkIfThereAreNoGames()){
                    new NewGameWindow(_user, _gamesLogic);
                    _gamesLogic.closeGamesWindowUI();
                } else if (isRowSelected()) {
                    try {
                        _gamesLogic.closeGamesWindowUI();
                        new PlayerLogInWindow(_selectedGame, _user, _gamesLogic);
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No game selected", "Select Game!",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void registerNewGame(){
        _gamesWindowUI.getNewGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewGameWindow(_user, _gamesLogic);
                _gamesLogic.closeGamesWindowUI();
            }
        });
    }

    private boolean isRowSelected(){
        if (selectRow() != null){
            return true;
        }
        return false;
    }

}
