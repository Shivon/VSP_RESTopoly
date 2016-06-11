package client.logic;

import client.adapter.PlayerAdapter;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.model.gameModels.Ready;
//import client.view.PlayerLogInWindow;
import clientUI.PlayerLoginWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.*;

/**
 * Created by Jana Mareike on 08.06.2016.
 */
public class PlayerLogic {

    private PlayerLoginWindowUI _playerWindowUI;
    private List<Player> _playerList;
    private Ready _ready;
    private Game _game;
    private List<String> _pawnList;
    private String _playerPawn;
    private PlayerAdapter _playerAdapter;
    private User _user ;
    private Player _player;

    public PlayerLogic(PlayerLoginWindowUI playerLoginWindowUI, Game game, User user ){
        _playerWindowUI = playerLoginWindowUI;
        _ready = new Ready(true);
        _game = game;
        _user = user;
        _pawnList = new ArrayList<>();
        _pawnList.add("Car");
        _pawnList.add("Shoe");
        _pawnList.add("Hat");
        _pawnList.add("Dog");
        _pawnList.add("Ship");
        _pawnList.add("Flatiron");
        _playerAdapter = new PlayerAdapter();
    }


    public void setAllPlayersOfGameReady(List<Player> playerList){
        _playerList = playerList;
        for (Player player : playerList) {
            player.setReady(_ready);
        }
    }

    public List<String> showAvailablePawns() throws UnirestException {
        System.out.println("hallo");
        System.out.println("game show "+_game);
        if(!_game.getPlayers().isEmpty()){
            List<Player> playerList = _game.getPlayers();
            Set<String> playerPawnSet = new HashSet<>();
            System.out.println(playerList);
            for (Player player : playerList) {
                System.out.println("PLAYERLIST PAWN: " + player.getPawn());
                System.out.println("PLAYERLIST : " + playerList);
                playerPawnSet.add(player.getPawn());
                System.out.println(playerPawnSet);
                System.out.println(_pawnList.size());
            }

            Iterator<String> iterator = _pawnList.iterator();
            while(iterator.hasNext()){
                String pawn = iterator.next();
                if(playerPawnSet.contains(pawn)){
                    iterator.remove();
                }
            }
        }

        System.out.println("PAWNLIST"+ _pawnList);
        return _pawnList;
    }

    public boolean checkPlayerPawnEntered(){
        if(!_playerWindowUI.getPlayerPawnArea().getText().isEmpty()) {
            return true;
        }
        return false;
    }

    public String getPlayerPawn(){
       return  _playerPawn = _playerWindowUI.getPlayerPawnArea().getText();
    }

    public boolean checkIfPlayerPawnIsNotAvailable() throws UnirestException {
        if(!showAvailablePawns().contains(getPlayerPawn())){
            return true;
        }
        return false;
    }

    public void postPlayer() throws UnirestException {
        System.out.println("before post");
        _playerAdapter.postPlayer( _playerPawn, _game, _user);
        System.out.println("after post im Submit");
        System.out.println("get Player: " +  _playerAdapter.getPlayer(_game, _user));
    }

    public void closePlayerWindow(){
        _playerWindowUI.getPlayerNameFrame().setVisible(false);
    }

    public Player getPlayer() throws UnirestException {
       return  _player = _playerAdapter.getPlayer(_game, _user);
    }
}
