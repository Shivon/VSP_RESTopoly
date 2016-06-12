package client.adapter;

import client.model.Bank;
import client.model.Board;
import client.model.Dice;
import client.model.dtos.GameDTO;
import client.model.dtos.PlayerDTO;
import client.model.dtos.PlayerURI;
import client.model.dtos.StatusDTO;
import client.model.gameModels.Game;
import client.model.gameModels.GameStatus;
import client.model.gameModels.Paths;
import client.model.gameModels.Player;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static client.model.gameModels.GameStatus.running;

/**
 * Created by Jana Mareike on 17.05.2016.
 */
public class GamesAdapter {

    Gson gson = new Gson();
    private IPAdresses _ipAdresses;
    private PlayerAdapter _playerAdapter;

    public GamesAdapter(PlayerAdapter playerAdapter){
        _ipAdresses = new IPAdresses();
        _playerAdapter = playerAdapter;
    }

    public Game getGame(String gameName) throws UnirestException {
//        Game game = new Game();
        for (Game game : getGames()) {
            if(game.getName().equals(gameName)){
                return game;
            }
        }
        return null;
    }

    public Game[] getGames() throws UnirestException {
        String games = Unirest.get(_ipAdresses.gamesIP() + "/games")
                .asString().getBody();
//String games = Unirest.get(_ipAdresses.gamesIP())
//        .asString().getBody();
        System.out.println(games);

        GameDTO[] gamesList = gson.fromJson(games, GameDTO[].class);
        return dtoToEntities(gamesList);
    }

    public Game[] dtoToEntities(GameDTO[] gameDTOs) throws UnirestException {
        Game[] accu = new Game[gameDTOs.length];
        for (int i = 0; i < gameDTOs.length; i++) {
            GameDTO gameDTO = gameDTOs[i];

            // get services from game service
            String servicesUriPostfix = gameDTO.getServices();
            String servicesAsString = makeGetOnGames(servicesUriPostfix);
            System.out.println(servicesAsString);
            Paths services = gson.fromJson(servicesAsString, Paths.class);

            // get components from game service
            String componentsUriPostfix = gameDTO.getServices();
            String componentsAsString = makeGetOnGames(componentsUriPostfix);
            System.out.println(componentsAsString);
            Paths components = gson.fromJson(componentsAsString, Paths.class);

            // get players from game service (and player service)
            List<Player> playersList = new ArrayList<>();
            String playerUriPostfix = gameDTO.getPlayers();
            String playersAsString = makeGetOnGames(playerUriPostfix);
            System.out.println(playersAsString);
            PlayerURI players = gson.fromJson(playersAsString, PlayerURI.class);
            for (String playerUri : players.getPlayers()) {
                // möglicher fehler: es muss eventuell der player adapter verwendet werden.
                String playerAsString = makeGetOnGames(playerUri);
                System.out.println(playerAsString);
                Player player = gson.fromJson(playerAsString, Player.class);
                playersList.add(player);


            }

            String uri = gameDTO.getUri();
            String name = gameDTO.getName();
            accu[i] = new Game(uri, name, playersList, services, components);
        }
        return accu;
    }

    public String makeGetOnGames(String postfix) throws UnirestException {
        return Unirest.get(_ipAdresses.gamesIP() + postfix).asString().getBody();
    }


    public void postGames(Game game ) throws UnirestException {
        Unirest.post(_ipAdresses.gamesIP() + "/games")
                .body(this.gson.toJson(game)).asJson();
//         Unirest.post(_ipAdresses.gamesIP())
//        .body(this.gson.toJson(game)).asJson();
    }

//    public void putGameStatusRegistration(Game game) throws UnirestException {
//        GameStatus gameStatus = GameStatus.registration;
//        game.setStatus(gameStatus);
//        Unirest.put(game.getUri() + "/status" ).body(this.gson.toJson(game)).asJson();
//    }

    public GameStatus getGamesStatus(Game game) throws UnirestException {
        String statusString = Unirest.get(_ipAdresses.gamesIP() + game.getUri()
                    + "/status").asString().getBody();
        System.out.println(statusString);

        StatusDTO status = gson.fromJson(statusString, StatusDTO.class);

        return status.getStatus();
    }

//    public void putNewGameComponents(Game game ) throws UnirestException {
////"description": "game components as full, absolute url",
//    }

//    public void putNewGameServices(Game game) throws UnirestException {
////        "description": "game components as full, absolute url",
//        Paths path = new Paths();
//        Bank bank = new Bank();
//        Board board = new Board();
//        Dice dice = new Dice();
//        path.setBank(_ipAdresses.banksIP());
//        path.setBoard(_ipAdresses.boardsIP());
////        path.setBroker(_ipAdresses._);
////        path.setDecks(_ipAdresses.);
//        path.setDice(_ipAdresses.diceIP());
//        path.setEvents(_ipAdresses.eventsIP());
//        path.setGame(_ipAdresses.gamesIP());
//        game.setServices(path);
//        Unirest.put(game.getUri() + "/services").body(this.gson.toJson(game)).asJson();
//
//    }

    public void putGameStatusRunning(Game game) throws UnirestException {
        GameStatus gameStatus = running;
        game.setStatus(gameStatus);
        System.out.println(game.getUri());
        Unirest.put(_ipAdresses.gamesIP() + game.getUri() + "/status" )
                .body(this.gson.toJson(game)).asJson();
    }

//    public void setCurrentGame(Game game){
//
//    }
//
//    public Game getCurrentGame(){
//        return
//    }

}
