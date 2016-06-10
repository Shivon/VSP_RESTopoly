package client.logic;

import client.adapter.GamesAdapter;
import client.adapter.PlayerAdapter;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.model.gameModels.Ready;
import client.view.NewGameWindow;
import client.view.PlayerLogInWindow;
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
    private PlayerLogInWindow _playerWindow;
    private PlayerLogic _playerLogic;
    private Ready _ready;
    private WaitLogic _waitLogic;

    public GamesLogic(GamesWindowUI gamesWindowUI, User user){
        _playerAdapter = new PlayerAdapter();
        _gamesAdapter = new GamesAdapter(_playerAdapter);
        _gamesWindowUI = gamesWindowUI;
//        _playerLogic = new PlayerLogic(_);
        _user = user;
        _ready = new Ready(true);
    }

    public List<Game> getGamesWithStatusRegistration() throws UnirestException {
        Game[] gamesList = _gamesAdapter.getGames();
        List<Game> games = Arrays.asList(gamesList);
        List<Game> gamesListRegistrationStatus = new ArrayList<Game>();
        System.out.println("GamesList im UserWindow: " + gamesList);

        for (Game game : games) {
//                            game.hasStatus(GameStatus.registration)
            if (_gamesAdapter.getGamesStatus(game).equals(registration)) {
                gamesListRegistrationStatus.add(game);
//                                games.remove(game);
            }
        }
        return gamesListRegistrationStatus;
    }

    public void gamesWithRegistrationStatusTable() throws UnirestException {
       _gamesTableModel =  new VstTableModel(getGamesWithStatusRegistration());
        for (int i = 0; i < getGamesWithStatusRegistration().size(); i++ ) {
//                _gamesWindow.getGamesWindowUI().getTableModel() // ui defaulttablemoodel
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
        System.out.println("GamesList im UserWindow: " + gamesList);
        if (gamesList.length == 0) {
            return true;
        }
        return false;
    }

    public void openNewGameWindow(){
        NewGameWindow newGameWindow = new NewGameWindow(_user);
    }

    public void closeGamesWindowUI(){
        _gamesWindowUI.getMainFrame().setVisible(false);
    }

    public void openPlayerLoginWindow() throws UnirestException {
        _playerWindow = new PlayerLogInWindow(_selectedGame, _user);
    }

    public void openRegisterNewGameWindow(){
        NewGameWindow newGameWindow = new NewGameWindow(_user);
        _gamesWindowUI.getMainFrame().setVisible(false);
    }

    public void startGame(Game game) throws UnirestException {
        _selectedGame = game;
        _gamesAdapter.putGameStatusRunning(_selectedGame);
        System.out.println(_gamesAdapter.getGamesStatus(_selectedGame));
        List<Player> playerList = _selectedGame.getPlayers();
        System.out.println(playerList);
        for (Player player : playerList) {
            player.setReady(_ready);
        }

//        TODO put player ready
    }

    public boolean checkIfUserHasAPlayerInGame(Game game){
        _selectedGame = game;
        List<Player> playerList = _selectedGame.getPlayers();
        System.out.println("USERNAMELIST: " + playerList);
        for (Player player : playerList) {
            System.out.println("PLAYER.GETUSER:  " + player.getUser());
            System.out.println("USERNAME: " + _user.getName().toLowerCase());
            if(player.getUser().equals(_user.getName().toLowerCase())){
                return true;
            }
        }

        return false;
    }

    public void startWaitWindow(User user, Game game, PlayerAdapter playerAdapter) throws UnirestException {
        _playerAdapter = playerAdapter;
        _user = user;
        _selectedGame = game;
        _waitLogic = new WaitLogic(_user, _selectedGame, _playerAdapter);
    }
}
