package client.logic;

import client.adapter.DiceAdapter;
import client.adapter.PlayerAdapter;
import client.model.Dice;
import client.model.Event;
import client.model.boardModels.Roll;
import com.jayway.restassured.response.Response;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 17.06.2016.
 */
public class TurnToRollLogic {

    private DiceAdapter _diceAdapter;
    private GamesLogic _gamesLogic;
    private UserLogic _userLogic;
    private Roll _diceRoll1;
    private Roll _diceRoll2;
    private PlayerAdapter _playerAdapter;

    public TurnToRollLogic(DiceAdapter diceAdapter, PlayerAdapter playerAdapter, GamesLogic gamesLogic, UserLogic userLogic){
        _diceAdapter = diceAdapter;
        _playerAdapter = playerAdapter;
        _gamesLogic = gamesLogic;
        _userLogic = userLogic;
    }

    public int getNumberOfRolls() throws UnirestException {
        _diceRoll1 = new Roll();
        _diceRoll2 = new Roll();
        int diceRoll1Number =  _diceAdapter.getDiceRollNumber();
        _diceRoll1.setNumber(diceRoll1Number);
        int diceRoll2Number = _diceAdapter.getDiceRollNumber();
        _diceRoll2.setNumber(diceRoll2Number);
        return diceRoll1Number + diceRoll2Number;
    }

    public Response getEventAfterDiceRoll() throws UnirestException {
       return  _diceAdapter.postDiceRollOnBoard(_gamesLogic.getCurrentGame(),
                _userLogic.getCurrentUser(), _diceRoll1, _diceRoll2);
    }

//    public void setPlayerToReady() throws UnirestException {
//        _playerAdapter.putPlayerReady(_gamesLogic.getCurrentGame(), _playerAdapter.
//                getPlayer(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser()));
//    }
}
