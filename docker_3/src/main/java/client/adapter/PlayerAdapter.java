package client.adapter;

import client.model.Accounts;
import client.model.Players;
import client.model.User;
import client.model.dtos.PlayerDTO;
import client.model.dtos.PlayerURI;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.model.gameModels.Ready;
import clientUI.GamesWindowUI;
import client.view.VstTableModel;
import com.google.gson.Gson;
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

        System.out.println("Player im Post: " + _player + "  " +
                Unirest.post(_ipAdresses.gamesIP()+ _game.getUri() + "/players")
                .body(this.gson.toJson(_player)).getBody());
        System.out.println(_ipAdresses.gamesIP()+ _game.getUri() + "/players");

        Unirest.post(_ipAdresses.gamesIP()+ _game.getUri() + "/players")
                .body(this.gson.toJson( _player)).asJson();
    }

    public void putPlayer(User user, Game game, Accounts account, boolean ready) throws UnirestException {
        this._game = game;
        this._account = account;
        this._user = user ;
        this._ready = new Ready(ready);

        Unirest.put( _ipAdresses.gamesIP() + "/games/" + _game.getGameId()
                + "/players/" + user.getName().toLowerCase()
                + "?account=" + _account
                + "&ready=" + _ready);
    }

    public Player getPlayer(Game game, User user) throws UnirestException {
        this._game = game;
        this._user = user;
        String userName = _user.getName().toLowerCase();

        Player[] playerList = getPlayers(_game);
        for(Player player : playerList){
            if(player.getUser().equals(userName)){
                return player;
            }
        }
        return null;
    }

    public Player[] getPlayers(Game game) throws UnirestException {
        System.out.println("getplayers");
        this._game = game;
        System.out.println("game " + _game + ", uri: " + _game.getUri());
        String players = Unirest.get(_ipAdresses.gamesIP()
                    + _game.getUri() + "/players")
                    .asString().getBody();
        System.out.println("playerstring: " +players);
        PlayerURI playerUriList = gson.fromJson(players, PlayerURI.class);

        List<String> playerFromUriList =  playerUriList.getPlayers();
        Player[] playerList = new Player[playerFromUriList.size()+1];
        for(int i = 0; i < playerFromUriList.size(); i++) {
            String playerDTOString = Unirest.get(_ipAdresses.gamesIP()
                    + playerFromUriList.get(i)).asString().getBody();
              System.out.println("PLAYER: " + playerDTOString);

            Player playerDTO = gson.fromJson(playerDTOString, Player.class);

            playerList[i] = playerDTO;
        }
        return playerList;
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

        Ready playerIsReady = gson.fromJson(isReady, Ready.class);
        return playerIsReady.isReady();
    }

    public void putPlayerTurn(Game game, Player player) throws UnirestException {
        this._game = game;
        this._player = player;

        Unirest.put(_ipAdresses.gamesIP()
                + _game.getUri() + "/players/turn"
                + "?player=" + _player)
                .asString().getBody();
    }

    public Player[] dtoToEntities(PlayerDTO[] playerDTOs) throws UnirestException {
    Player[] accu = new Player[playerDTOs.length];
        for (int i = 0; i < playerDTOs.length; i++){
            PlayerDTO playerDTO = playerDTOs[i];

//            get Ready from player service
           String readyUriPostfix = playerDTO.getReady();
            String readyAsString = makeGetOnPlayers(readyUriPostfix);
            System.out.println(readyAsString);
            Ready ready = gson.fromJson(readyAsString, Ready.class);

            String uri = playerDTO.getUri();
            String user = playerDTO.getUser();
            String pawn = playerDTO.getPawn();
            String account = playerDTO.getAccount();

            accu[i] = new Player(uri, user, pawn, account, ready);

            System.out.println("ACCU "  + i);
        }
        return accu;
    }


//    URI, User, Pawn, account, ready

    public String makeGetOnPlayers(String postfix) throws UnirestException {
        return Unirest.get(_ipAdresses.gamesIP() + "/players" + postfix).asString().getBody();
    }
}

//    public Game[] dtoToEntities(GameDTO[] gameDTOs) throws UnirestException {
//        Game[] accu = new Game[gameDTOs.length];
//        for (int i = 0; i < gameDTOs.length; i++) {
//            GameDTO gameDTO = gameDTOs[i];
//
//            // get services from game service
//            String servicesUriPostfix = gameDTO.getServices();
//            String servicesAsString = makeGetOnGames(servicesUriPostfix);
//            System.out.println(servicesAsString);
//            Paths services = gson.fromJson(servicesAsString, Paths.class);
//
//
//        }
