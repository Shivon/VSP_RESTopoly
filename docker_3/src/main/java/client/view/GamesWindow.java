package client.view;

import client.adapter.GamesAdapter;
import client.adapter.PlayerAdapter;
import client.adapter.UserAdapter;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.model.gameModels.Ready;
import clientUI.GamesWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


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
    private  WaitWindow _waitWindow;

    public GamesWindow(VstTableModel gamesTableModel, User user) throws UnirestException {
        this._gamesTableModel = gamesTableModel;
        _gamesWindowUI = new GamesWindowUI();
        _playerAdapter = new PlayerAdapter();
        _gamesAdapter = new GamesAdapter(_playerAdapter);
        _ready = new Ready(true);
        this._user = user;
        registerSubmitJoinTheGame();
        registerNewGame();
        registerStartGame(_selectedGame);
        selectRow();
    }

    private Game selectRow(){
        System.out.println("select row");
        _gamesWindowUI.getAllGameTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("maus click");
                int row = _gamesWindowUI.getAllGameTable().rowAtPoint(evt.getPoint());
                System.out.println("Zeile: " +row);

                if (row >= 0){
                    System.out.println("Eintrag in Zeile: " +_gamesWindowUI.getTableModel().getValueAt(row,0));
                   _selectedGame = _gamesTableModel.getGameAt(row);
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
                Game[] gamesList = new Game[0];
                try {
                    gamesList = _gamesAdapter.getGames();
                } catch (UnirestException e1) {
                    e1.printStackTrace();
                }
                System.out.println("GamesList im UserWindow: " + gamesList);
                if (gamesList.length == 0) {
                    System.out.println("Es gibt noch keine Games");
                    NewGameWindow newGameWindow = new NewGameWindow(_user);
                    _gamesWindowUI.getMainFrame().setVisible(false);

                    System.out.println(gamesList);
//                wenn game markiert, dann
                } else if (selectRow() != null) {
                        try {
                            System.out.println("selected Game submit " + _selectedGame.getName());
                            System.out.println("SELECTED GAME PLAYERS " + _selectedGame.getPlayers());
                            _playerWindow = new PlayerLogInWindow(_selectedGame, _user);
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

    public void registerNewGame(){
        _gamesWindowUI.getNewGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewGameWindow newGameWindow = new NewGameWindow(_user);
                _gamesWindowUI.getMainFrame().setVisible(false);
            }
        });
    }

    public void registerStartGame(Game game){
        _selectedGame = game;
        _gamesWindowUI.getStartGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game[] gamesList = new Game[0];
                try {
                    gamesList = _gamesAdapter.getGames();
                } catch (UnirestException e1) {
                    e1.printStackTrace();
                }
                System.out.println("GamesList im UserWindow: " + gamesList);
                if (gamesList.length == 0) {
                    System.out.println("Es gibt noch keine Games");
                    NewGameWindow newGameWindow = new NewGameWindow(_user);
                    _gamesWindowUI.getMainFrame().setVisible(false);

                    System.out.println(gamesList);
//                wenn game markiert, dann
                } else if (selectRow() != null) {
                    try {
                        System.out.println("selected Game submit " + _selectedGame.getName());
                        System.out.println("SELECTED GAME PLAYERS " + _selectedGame.getPlayers());
                        _gamesAdapter.putGameStatusRunning(_selectedGame);
                        System.out.println(_gamesAdapter.getGamesStatus(_selectedGame));
                        List<Player> playerList = _selectedGame.getPlayers();
                        System.out.println(playerList);
                        for (Player player : playerList) {
                            player.setReady(_ready);
                        }
                        _gamesWindowUI.getMainFrame().setVisible(false);
                        _waitWindow = new WaitWindow(_user, _selectedGame);
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

    public GamesWindowUI getGamesWindowUI(){ return _gamesWindowUI;}

}
