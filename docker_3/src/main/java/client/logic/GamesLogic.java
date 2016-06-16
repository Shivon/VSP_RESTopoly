package client.logic;

import client.adapter.GamesAdapter;
import client.adapter.PlayerAdapter;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Ready;
import client.service.ClientService;
import client.view.VstTableModel;
import clientUI.GamesWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static client.model.gameModels.GameStatus.registration;

/**
 * Created by Jana Mareike on 08.06.2016.
 */
public class GamesLogic {

    private PlayerAdapter _playerAdapter;
    private GamesAdapter _gamesAdapter;
    private VstTableModel _gamesTableModel;
    private GamesWindowUI _gamesWindowUI;
    private Game _selectedGame;
    private User _user;
    private Ready _ready;

    public GamesLogic(GamesWindowUI gamesWindowUI, User user) throws UnirestException {
        _gamesWindowUI = gamesWindowUI;
        _user = user;
        _playerAdapter = new PlayerAdapter();
        _gamesAdapter = new GamesAdapter(_playerAdapter);
        _ready = new Ready(true);
    }

    public List<Game> getGamesWithStatusRegistration() throws UnirestException {
        Game[] gamesList = _gamesAdapter.getGames();
        List<Game> games = Arrays.asList(gamesList);
        List<Game> gamesListRegistrationStatus = new ArrayList<Game>();

        for (Game game : games) {
            if (_gamesAdapter.getGamesStatus(game).equals(registration)) {
                gamesListRegistrationStatus.add(game);
            }
        }
        return gamesListRegistrationStatus;
    }

    public void gamesWithRegistrationStatusTable() throws UnirestException {
       _gamesTableModel =  new VstTableModel(getGamesWithStatusRegistration());
        for (int i = 0; i < getGamesWithStatusRegistration().size(); i++ ) {
            _gamesWindowUI.getTableModel()
                    .addRow(new java.lang.Object[]{_gamesTableModel.getValueAt(i, 1)});
        }
    }

    public void setGamesUIVisible(){
        _gamesWindowUI.getMainFrame().setVisible(true);
    }

    public boolean checkIfRowIsChosen(int row){
        if (row >= 0){
            return true;
        }
        return false;
    }

    public Game getSelectedGameFromTable(int row){
        return _selectedGame = _gamesTableModel.getGameAt(row);
    }

    public boolean checkIfThereAreNoGames(){
        Game[] gamesList = new Game[0];
        try {
            gamesList = _gamesAdapter.getGames();
        } catch (UnirestException e1) {
            e1.printStackTrace();
        }
        if (gamesList.length == 0) {
            return true;
        }
        return false;
    }

    public void closeGamesWindowUI(){
        _gamesWindowUI.getMainFrame().setVisible(false);
    }

    public void startGame(Game game) throws UnirestException {
        _gamesAdapter.putGameStatusRunning(game);
    }

    public void startWaitWindow(User user, Game game, PlayerAdapter playerAdapter, ClientService clientService) throws UnirestException {
        _playerAdapter = playerAdapter;
        _user = user;
        _selectedGame = game;
        WaitLogic waitLogic = new WaitLogic(_user, _selectedGame, _playerAdapter);
        clientService.setWaitLogic(waitLogic);
    }
}
