package client.adapter;

import client.EventSubscriptions;
import client.logic.GamesLogic;
import client.logic.UserLogic;
import client.model.Accounts;
import client.model.Players;
import client.model.User;
import client.model.dtos.PlayerDTO;
import client.model.dtos.PlayerURI;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.model.gameModels.Ready;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.List;

/**
 * Created by Jana Mareike on 18.05.2016.
 */
public class PlayerAdapter {

    Gson gson = new Gson();
    private String _playerPawn;
    private Game _game;
    private Player _player;
    private User _user;
    private IPAdresses _ipAdresses;
    private Accounts _account;
    private Ready _ready;
//    private UserLogic _userLogic;
//    private GamesLogic _gamesLogic;

    public PlayerAdapter(IPAdresses ipAdresses){
        _ipAdresses = ipAdresses;
    }

    public void postPlayer( String playerPawn, Game game, User user) throws UnirestException {
        System.out.println(playerPawn + " " + game.getName() + " " + user.getName());
        this._player = new Player();
        this._user = user;
        this._game = game;
        this._playerPawn = playerPawn;
        this._ready = new Ready();
        _ready.setReady(false);

//        TODO Accout für player erstellen
//        _player.setAccount("uri to the account in the bank belonging to the player");
//        _player.setPlayerId("uri the resource");
        _player.setPawn(playerPawn);
        _player.setReady(_ready);
        _player.setUri(_game.getUri() + "/players/" + _user.getName().toLowerCase());
        _player.setUser(_user.getName().toLowerCase());

        System.out.println("PLAYER Im POST : " + _player.getPawn());

            Unirest.post(_ipAdresses.gamesIP()+ _game.getUri().replaceFirst("/games" , "") + "/players")
                    .body(this.gson.toJson( _player)).asString();
    }


    public void putPlayer(User user, Game game, Accounts account, boolean ready) throws UnirestException {
        this._game = game;
        this._account = account;
        this._user = user ;
        this._ready = new Ready(ready);

        Unirest.put( _ipAdresses.gamesIP() + "/" + _game.getGameId()
                + "/players/" + user.getName().toLowerCase()
                + "?account=" + _account
                + "&ready=" + _ready);
    }

    public Player getPlayer(Game game, User user) throws UnirestException {
        this._game = game;
        this._user = user;

        String userName = _user.getName().toLowerCase();

        Player[] playerList = getPlayers(_game);

            for (Player player : playerList) {
                if (player.getUser().equals(userName)) {
                    return player;
                }
            }

        return null;
    }

    public Player[] getPlayers(Game game) throws UnirestException {
        this._game = game;
        System.out.println("GAME im get players: " + _game);
        System.out.println(_game.getUri() + "/players");
        String players = Unirest.get(_ipAdresses.gamesIP()  + _game.getUri().replaceFirst("/games", "") + "/players")
                    .asString().getBody();

        System.out.println("IM GET PLAYERS: " + players);

        PlayerURI playerUriList = gson.fromJson(players, PlayerURI.class);

        System.out.println("PLAYER URI LIST : "  + playerUriList);

        List<String> playerFromUriList =  playerUriList.getPlayers();

        Player[] playerList = new Player[playerFromUriList.size()+1];
        for(int i = 0; i < playerFromUriList.size(); i++) {
            String playerDTOString = Unirest.get(_ipAdresses.gamesIP()
                    + playerFromUriList.get(i).replaceFirst("/games", "")).asString().getBody();
            Player playerDTO = gson.fromJson(playerDTOString, Player.class);

            playerList[i] = playerDTO;
        }
        return playerList;
    }

    public void putPlayerReady(Game game, Player player) throws UnirestException {
        this._game = game;
        this._player = player;
        _player.setReady(new Ready(true));
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
        }
        return accu;
    }


//    URI, User, Pawn, account, ready

    public String makeGetOnPlayers(String postfix) throws UnirestException {
        return Unirest.get(_ipAdresses.gamesIP() + "/players" + postfix).asString().getBody();
    }

    public IPAdresses getIPAdresses(){return _ipAdresses;}
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
