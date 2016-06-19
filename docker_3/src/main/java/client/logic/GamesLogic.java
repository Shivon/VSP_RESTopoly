package client.logic;

import client.adapter.GamesAdapter;
import client.model.gameModels.Game;
import client.model.gameModels.GameStatus;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static client.model.gameModels.GameStatus.registration;

/**
 * Created by Jana Mareike on 08.06.2016.
 */
public class GamesLogic {

    private GamesAdapter _gamesAdapter;
    private UserLogic _userLogic;
    private Game _game;

    public GamesLogic(GamesAdapter gamesAdapter, UserLogic userLogic) throws UnirestException {
        _gamesAdapter = gamesAdapter;
        _userLogic = userLogic;
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

    public boolean checkIfRowIsChosen(int row){
        if (row >= 0){
            return true;
        }
        return false;
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

//    Player of the game will be set to ready(true) by Game
    public void startGame(Game game) throws UnirestException {
        _gamesAdapter.putGameStatusRunning(game);
    }

    public Game getCurrentGame(){
        return _gamesAdapter.getGame(_game.getName());
    }

//    public Game joinGame(Game game) throws UnirestException {
//
//        setCurrentGame(game.getName());
//        _userLogic.getCurrentUser();
//    }

    public void setCurrentGame(Game game) {
        _game = game;
    }

    public Game[] getGames() throws UnirestException {
        return _gamesAdapter.getGames();
    }

    public Game createNewGame(String gameName) throws UnirestException {
        Game newGame = new Game();
        newGame.setName(gameName);
        newGame.setStatus(GameStatus.registration);
        setCurrentGame(newGame);
        _gamesAdapter.postGame(newGame);
        _game = newGame;
        System.out.println("asdfaslkfjaskfaslökfjasöldkf\n\n\n");
        System.out.println(_game.toString());
        System.out.println(getCurrentGame().toString());
        System.out.println("asdfaslkfjaskfaslökfjasöldkf");
        return newGame;
    }
}
