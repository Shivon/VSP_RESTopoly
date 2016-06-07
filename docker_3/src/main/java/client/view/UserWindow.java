package client.view;

import client.adapter.GamesAdapter;
import client.adapter.IPAdresses;
import client.adapter.PlayerAdapter;
import client.adapter.UserAdapter;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.GameStatus;
import clientUI.UserWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static client.model.gameModels.GameStatus.*;
/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class UserWindow {

    private UserWindowUI _userWindowUI;
    private GamesWindow _gamesWindow;
    private VstTableModel _gamesTableModel;
    private String _userName;
    private UserAdapter _userAdapter;
    private PlayerAdapter _playerAdapter;
    private GamesAdapter _gamesAdapter;
//    private IPAdresses _ipAdresses;
    private User _user;
//    private GameStatus _gameStatus;
//    private Gson gson = new Gson();

    public UserWindow() throws UnirestException{
        _userWindowUI = new UserWindowUI();
        _userAdapter = new UserAdapter();
        _playerAdapter = new PlayerAdapter();
        _gamesAdapter = new GamesAdapter(_playerAdapter);
//        _gameStatus = GameStatus.registration;
        registerSubmitUserName();
    }

    public void registerSubmitUserName(){
        System.out.println("hallo");
        _userWindowUI.getLogInSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hallo");
                if(! _userWindowUI.getLogInArea().getText().isEmpty()){
                    _userName = _userWindowUI.getLogInArea().getText();
                    try {
                        _user = _userAdapter.getUser(_userName.toLowerCase());
                        if(_user != null){
                            JOptionPane.showMessageDialog(null, "user name not available", "choose an other name!",
                                        JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        _userAdapter.postUser(_userName);
                        System.out.println("user after post " + _userAdapter.getUser(_userName.toLowerCase()));
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    _userWindowUI.getLogInFrame().setVisible(false);
                    //Semaphore, weil max Anzahl Spieler?
                    try {
                        Game[] gamesList = _gamesAdapter.getGames();
                        List<Game> games = Arrays.asList(gamesList);
                        List<Game> gamesListRegistrationStatus = new ArrayList<Game>();
                        System.out.println("GamesList im UserWindow: " + gamesList);

                        for (Game game : games) {
//                            game.hasStatus(GameStatus.registration)
                            if(_gamesAdapter.getGamesStatus(game).equals(registration)){
                                gamesListRegistrationStatus.add(game);
//                                games.remove(game);

                            }
                        }
                            System.out.println(gamesList);
                            _gamesTableModel =  new VstTableModel(gamesList);
                            _user =  _userAdapter.getUser(_userName);
                            _gamesWindow = new GamesWindow(_gamesTableModel, _user);
                            for (int i = 0; i < gamesListRegistrationStatus.size(); i++ ) {
                                _gamesWindow.getGamesWindowUI().getTableModel() // ui defaulttablemoodel
                                        .addRow(new java.lang.Object[]{_gamesTableModel.getValueAt(i, 1)});
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }

                    _gamesWindow.getGamesWindowUI().getMainFrame().setVisible(true);
                }else {
                    JOptionPane.showMessageDialog(null, "No user name", "no user name",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
