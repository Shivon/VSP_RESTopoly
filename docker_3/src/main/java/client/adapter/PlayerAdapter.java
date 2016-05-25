package client.adapter;

import client.model.Players;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.view.GamesWindowUI;
import client.view.VstTableModel;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jana Mareike on 18.05.2016.
 */
public class PlayerAdapter {

    Gson gson = new Gson();
    private VstTableModel _gamesTableModel;
    private GamesWindowUI _gamesWindowUI;
    private String _playerPawn;
    private Game _game;
    private Player _player;
    private User _user;
    private IPAdresses _ipAdresses;

    public PlayerAdapter(){
        _ipAdresses = new IPAdresses();
    }

    public void postPlayer( String playerPawn, Game game, User user) throws UnirestException {
//        this._gamesTableModel = gamesTableModel;
//        this._gamesWindowUI = gamesWindowUI;
        this._user = user;
        this._game = game;
        this._playerPawn = playerPawn;
        System.out.println("playerPawn in postplayer" + playerPawn);
        System.out.println("User: " +_user);

        Unirest.post(_ipAdresses.gamesIP()
                + this._game.getUri() + "/players"
                + "?user=" + "/user/" + user.getName().toLowerCase()
                + "&ready=" + true
                + "&pawn=" + playerPawn);
    }

//    public void putPlayer(VstTableModel gamesTableModel, GamesWindowUI gamesWindowUI, String playerPawn, Game game) throws UnirestException {
//        this._gamesTableModel = gamesTableModel;
//        this._gamesWindowUI = gamesWindowUI;
//        this._playerPawn = playerPawn;
//        this._game = game;
//
//        Unirest.put(_ipAdresses.gamesIP()
//                + _game.getUri() + "/players")
//    }

    public List<Player> getPlayers(Game game) throws UnirestException {
//        this._gamesWindowUI = _gamesWindowUI;
//        this._gamesTableModel = gamesTableModel;
        System.out.println("getplayer");
        this._game = game;
        System.out.println("game " + _game + ", uri: " + _game.getUri());
        String players = Unirest.get(_ipAdresses.gamesIP()
                + _game.getUri() + "/players")
                .asString().getBody();
        System.out.println("playerstring" +players);
        Players playerList = gson.fromJson(players, Players.class);
        System.out.println("playerlist" + playerList);
       return playerList.getPlayers();
    }

    public void putPlayerReady(Game game, Player player) throws UnirestException {
        this._game = game;
        this._player = player;
// /games/{gameid}/players/{playerid}/ready
        Unirest.put(_ipAdresses.gamesIP()
                + _game.getUri() + "/players/" + _player.getPlayerId() + "/ready")
                .asString().getBody();
    }

}
