package client.view;

import client.adapter.GamesAdapter;
import client.adapter.UserAdapter;
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
    private VstTableModel _gamesTableModel;
    private UserAdapter _userAdapter;
    private User _user;

    public NewGameWindow(User user){
        _newGameWindowUI = new NewGameWindowUI();
        _userAdapter = new UserAdapter();
        _newGameWindowUI.getLogInFrame().setVisible(true);
        _gamesAdapter = new GamesAdapter(null);
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
                        _gamesAdapter.postGames(_gameName);
                        System.out.println("Game geposted");
                        Game game = _gamesAdapter.getGame(_gameName);
                        game.setStatus(GameStatus.registration);
                        System.out.println("GAMES im newGames: " + _gamesAdapter.getGames());

                        Game[] gamesList = _gamesAdapter.getGames();
                        List<Game> games = Arrays.asList(gamesList);
                        List<Game> gamesListRegistrationStatus = new ArrayList<Game>();
                        System.out.println("GamesList im UserWindow: " + gamesList);

                        for (Game gameToCheckStatus : games) {
//                            game.hasStatus(GameStatus.registration)
                            if(_gamesAdapter.getGamesStatus(gameToCheckStatus).equals(registration)){
                                gamesListRegistrationStatus.add(gameToCheckStatus);
//                                games.remove(game);

                            }
                        }
                        System.out.println(gamesList);
                        _gamesTableModel =  new VstTableModel(gamesList);
//                        _user =  _userAdapter.getUser(_userName);
                        _gamesWindow = new GamesWindow(_gamesTableModel, _user);
                        for (int i = 0; i < gamesListRegistrationStatus.size(); i++ ) {
                            _gamesWindow.getGamesWindowUI().getTableModel() // ui defaulttablemoodel
                                    .addRow(new java.lang.Object[]{_gamesTableModel.getValueAt(i, 1)});
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    _gamesWindow.getGamesWindowUI().getMainFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No game name", "no game name",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
