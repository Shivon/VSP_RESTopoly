package client.view;

import client.adapter.GamesAdapter;
import client.adapter.PlayerAdapter;
import client.adapter.UserAdapter;
import client.logic.GamesLogic;
import client.logic.WaitLogic;
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
    private PlayerLogInWindow _playerWindow;
    private PlayerAdapter _playerAdapter;
    private GamesAdapter _gamesAdapter;
    private VstTableModel _gamesTableModel;
    private Game _selectedGame;
    private User _user;
    private Ready _ready;
    private WaitLogic _waitLogic;
    private UserAdapter _userAdapter;
    private String _userName;
    private GamesLogic _gamesLogic;

    public GamesWindow(User user) throws UnirestException {

        _gamesWindowUI = new GamesWindowUI();
        this._user = user;
        _gamesLogic = new GamesLogic(_gamesWindowUI, _user);
        _userAdapter = new UserAdapter();
        _playerAdapter = new PlayerAdapter();
        _gamesAdapter = new GamesAdapter(_playerAdapter);
        _ready = new Ready(true);


        buildGamesWindowUI();
        registerSubmitJoinTheGame();
        registerNewGame();
        registerStartGame(_selectedGame, _user);
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
        System.out.println("select row");
        _gamesWindowUI.getAllGameTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("maus click");
                int row = _gamesWindowUI.getAllGameTable().rowAtPoint(evt.getPoint());
                System.out.println("Zeile: " +row);
                if (_gamesLogic.checkIfRowIsChosen(row)) {
                   _selectedGame=  _gamesLogic.getSelectedGameFromTable(row);
                }
            }
        });
        System.out.println("selected Game"+ _selectedGame);
        return _selectedGame;
    }

    private void registerSubmitJoinTheGame() {
        _gamesWindowUI.getTakePartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_gamesLogic.checkIfThereAreNoGames()){
                    _gamesLogic.openNewGameWindow();
                    _gamesLogic.closeGamesWindowUI();
                } else if (isRowSelected()) {
                    try {
                        _gamesLogic.closeGamesWindowUI();
                        _gamesLogic.openPlayerLoginWindow();
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
                _gamesLogic.openNewGameWindow();
                _gamesLogic.closeGamesWindowUI();
            }
        });
    }

    private void registerStartGame(Game game, User user){
        _user = user;
        _selectedGame = game;
        _gamesWindowUI.getStartGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_gamesLogic.checkIfThereAreNoGames()){
                    System.out.println("Es gibt noch keine Games");
                    _gamesLogic.openNewGameWindow();
                    _gamesLogic.closeGamesWindowUI();
                } else if (isRowSelected()) {
                    try {
                        if(_gamesLogic.checkIfUserHasAPlayerInGame(_selectedGame)){
                        _gamesLogic.startGame(_selectedGame);
                        _gamesLogic.closeGamesWindowUI();
                        _gamesLogic.startWaitWindow(_user, _selectedGame, _playerAdapter);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "No pawn choosen", "You have to join the game to start it!",
                                    JOptionPane.ERROR_MESSAGE);
                        }
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

    private boolean isRowSelected(){
        if (selectRow() != null){
            return true;
        }
        return false;
    }

}
