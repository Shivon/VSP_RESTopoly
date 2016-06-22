package client.logic;

import client.EventSubscriptions;
import client.adapter.PlayerAdapter;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.*;


/**
 * Created by Jana Mareike on 08.06.2016.
 */
public class PlayerLogic {

    private List<String> _pawnList;
    private PlayerAdapter _playerAdapter;
    private GamesLogic _gamesLogic;
    private UserLogic _userLogic;

    public PlayerLogic(PlayerAdapter playerAdapter, GamesLogic gamesLogic, UserLogic userLogic ){
        _pawnList = new ArrayList<>();
        _pawnList.add("Car");
        _pawnList.add("Shoe");
        _pawnList.add("Hat");
        _pawnList.add("Dog");
        _pawnList.add("Ship");
        _pawnList.add("Flatiron");
        _playerAdapter = playerAdapter;
        _gamesLogic = gamesLogic;
        _userLogic = userLogic;
    }

    public List<String> getAvailablePawns() throws UnirestException {
        Game game = _gamesLogic.getCurrentGame();
        List<Player> players = game.getPlayers();
        if(!players.isEmpty()){
            List<Player> playerList = game.getPlayers();
            Set<String> playerPawnSet = new HashSet<>();
            for (Player player : playerList) {
                playerPawnSet.add(player.getPawn());
            }

            Iterator<String> iterator = _pawnList.iterator();
            while(iterator.hasNext()){
                String pawn = iterator.next();
                if(playerPawnSet.contains(pawn)){
                    iterator.remove();
                }
            }
        }
        return _pawnList;
    }

//    public boolean checkPlayerPawnEntered(){
//        if(!_playerWindowUI.getPlayerPawnArea().getText().isEmpty()) {
//            return true;
//        }
//        return false;
//    }
//
//    public String getPlayerPawn(){
//       return  _playerPawn = _playerWindowUI.getPlayerPawnArea().getText();
//    }

//    public boolean checkIfPlayerPawnIsNotAvailable() throws UnirestException {
//        if(!getAvailablePawns().contains(getPlayerPawn())){
//            return true;
//        }
//        return false;
//    }

    public void registerPlayer(String playerPawn) throws UnirestException {
        System.out.println("PLAYERPAWN IM REGISTER: " + playerPawn + " "  + _gamesLogic.getCurrentGame().getName() + "  " + _userLogic.getCurrentUser().getName());
        _playerAdapter.postPlayer(playerPawn, _gamesLogic.getCurrentGame(), _userLogic.getCurrentUser());
//  TODO Events
      new EventSubscriptions(_playerAdapter.getIPAdresses(), _gamesLogic, _userLogic);
        System.out.println(_playerAdapter.getPlayer(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser()));
    }

    public void setPlayerToReady() throws UnirestException {
        _playerAdapter.putPlayerReady(_gamesLogic.getCurrentGame(),
                _playerAdapter.getPlayer(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser()));
    }
}
