package client.adapter;

import client.model.Accounts;
import client.model.Players;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.model.gameModels.Ready;
import clientUI.GamesWindowUI;
import client.view.VstTableModel;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

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
    private Accounts _account;
    private Ready _ready;
    private boolean _isReady;

    public PlayerAdapter(){
        _ipAdresses = new IPAdresses();
    }

    public void postPlayer( String playerPawn, Game game, User user) throws UnirestException {
        this._player = new Player();
        this._user = user;
        this._game = game;
        this._playerPawn = playerPawn;
        this._ready = new Ready();
        _ready.setReady(false);
        System.out.println("playerPawn in postplayer" + playerPawn);
        System.out.println("User im Post: " +_user);

//        TODO Accout für player erstellen
//        _player.setAccount("uri to the account in the bank belonging to the player");
//        _player.setPlayerId("uri the resource");
        _player.setPawn(playerPawn);
        _player.setReady(_ready);
       _player.setUri(_game.getUri() + "/players/" + _user.getName().toLowerCase());
        _player.setUser(_user.getName().toLowerCase());

        System.out.println("GAme im postPlayer: " + _game.getUri());
        System.out.println("Gamename: " + _game.getName());

        System.out.println("PLaYer im POst: " + _player + "  " + Unirest.post(_ipAdresses.gamesIP()+ _game.getUri() + "/players")
                .body(this.gson.toJson(_player)).getBody());
        System.out.println(_ipAdresses.gamesIP()+ _game.getUri() + "/players");

        Unirest.post(_ipAdresses.gamesIP()+ _game.getUri() + "/players")
                .body(this.gson.toJson( _player)).getBody();
    }

    public void putPlayer(User user, Game game, Accounts account, boolean ready) throws UnirestException {
        this._game = game;
        this._account = account;
        this._user = user ;
//        this._ready = ready;

        Unirest.put( _ipAdresses.gamesIP() + "/games/" + _game.getGameId()
                + "/players/" + user.getName().toLowerCase()
                + "?account=" + _account
                + "&ready=" + _ready);
    }

//    TODO playerID ???
    public Player getPlayer(Game game, User user) throws UnirestException {
        this._game = game;
        this._user = user;
        String userName = _user.getName().toLowerCase();

        String playerString = Unirest.get(_ipAdresses.gamesIP() + _game.getUri()
                + "/players/" + userName)
                .asString().getBody();
        System.out.println("PlayerString: " +playerString);

        Player player = gson.fromJson(playerString, Player.class);
        System.out.println("PlayerObject: " + player);
        return player;

//        System.out.println("Username im getUser: " +userName);
//        String user = Unirest.get("http://"+_ipAdresses.usersIP()+"/users/"
//                + userName.toLowerCase()).asString().getBody();
//
//        System.out.println("String...............\n" + user);
//
//        User userObj = gson.fromJson(user, User.class);
//
//        System.out.println("UserObject: " + userObj);
//        return userObj;
    }

    public List<Player> getPlayers(Game game) throws UnirestException {
//        this._gamesWindowUI = _gamesWindowUI;
//        this._gamesTableModel = gamesTableModel;
        System.out.println("getplayers");
        this._game = game;
        System.out.println("game " + _game + ", uri: " + _game.getUri());
        String players = Unirest.get(_ipAdresses.gamesIP()
                + _game.getUri() + "/players")
                .asString().getBody();
        System.out.println("playerstring: " +players);
        Players playerList = gson.fromJson(players, Players.class);
        System.out.println("playerlist: " + playerList);
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

    public boolean getPlayerIsReady(Game game, Player player) throws UnirestException {
        this._game = game;
        this._player = player;

        String isReady = Unirest.get(_ipAdresses.gamesIP()
        + _game.getUri() + "/players/" + _player.getPlayerId() + "/ready")
                .asString().getBody();

        boolean playerIsReady = gson.fromJson(isReady, boolean.class);
        return playerIsReady;
    }

    public void putPlayerTurn(Game game, Player player) throws UnirestException {
        this._game = game;
        this._player = player;

        Unirest.put(_ipAdresses.gamesIP()
                + _game.getUri() + "/players/turn"
                + "?player=" + _player)
                .asString().getBody();
    }

}
