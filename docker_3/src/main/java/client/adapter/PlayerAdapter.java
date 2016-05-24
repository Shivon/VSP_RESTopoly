package client.adapter;

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
    private IPAdresses _ipAdresses;

    public PlayerAdapter(){
        _ipAdresses = new IPAdresses();
    }

    public void postPlayer(VstTableModel gamesTableModel, GamesWindowUI gamesWindowUI, String playerPawn, Game game) throws UnirestException {
        this._gamesTableModel = gamesTableModel;
        this._gamesWindowUI = gamesWindowUI;
//        this._player = playerPawn;

        Unirest.post(_ipAdresses.gamesIP()
                + this._game.getGameId() + "/players")
                .field("pawn", playerPawn)
                .asJson();
    }

    public void putPlayer(VstTableModel gamesTableModel, GamesWindowUI gamesWindowUI, String playerPawn, Game game) throws UnirestException {
        this._gamesTableModel = gamesTableModel;
        this._gamesWindowUI = gamesWindowUI;
        this._playerPawn = playerPawn;
        this._game = game;

        Unirest.post(_ipAdresses.gamesIP()
                + _game.getGameId() + "/players")
                .field("pawn", playerPawn)
                .asJson();
    }

    public Player[] getPlayers(Game game) throws UnirestException {
//        this._gamesWindowUI = _gamesWindowUI;
//        this._gamesTableModel = gamesTableModel;
        System.out.println("getplayer");
        this._game = game;
        System.out.println("game " + _game + ", id: " + _game.getGameId());
        String players = Unirest.get(_ipAdresses.gamesIP()
                + _game.getGameId() + "/players")
                .asString().getBody();
        System.out.println(players);
        Player[] playerList = gson.fromJson(players, Player[].class);
        System.out.println(playerList);
       return  playerList;
    }

    public void putPlayerReady(Game game, Player player) throws UnirestException {
        this._game = game;
        this._player = player;
// /games/{gameid}/players/{playerid}/ready
        Unirest.put(_ipAdresses.gamesIP()
                + _game.getGameId() + "/players/" + _player.getPlayerId() + "/ready")
                .asString().getBody();
    }

}
