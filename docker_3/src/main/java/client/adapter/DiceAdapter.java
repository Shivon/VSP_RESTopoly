package client.adapter;

import client.model.Dice;
import client.model.User;
import client.model.boardModels.Board;
import client.model.boardModels.Roll;
import client.model.boardModels.Throw;
import client.model.dtos.BoardDTO;
import client.model.gameModels.Game;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 18.05.2016.
 */
public class DiceAdapter {

    Gson gson = new Gson();
    private IPAdresses _ipAdresses;
    private User _user;
    private Game _game;
    private String _board;

    public DiceAdapter(){

        _ipAdresses = new IPAdresses();
    }

    public int getDiceRollNumber() throws UnirestException {
        System.out.println("Dice roll");
        String rollString = Unirest.get(_ipAdresses.diceIP()).asString().getBody();
        System.out.println(rollString);
        Dice dice = gson.fromJson(rollString, Dice.class);
        System.out.println("" + dice.getNumber());
        return  dice.getNumber();
    }

    public BoardDTO postDiceRollOnBoard(Game game, User user, Roll diceRoll1, Roll diceRoll2) throws UnirestException {
        _game = game;
        _user = user;
        Throw throwOnBoard = new Throw();
        throwOnBoard.setRoll1(diceRoll1);
        throwOnBoard.setRoll2(diceRoll2);
        System.out.println("Throw: " + throwOnBoard);
        System.out.println(Unirest.post(_ipAdresses.boardsIP() + "/" + _game.getGameId()
                + "/pawns/" + _user.getName().toLowerCase() + "/roll").body(this.gson.toJson(throwOnBoard)).getBody());

        String boardString = Unirest.post(_game.getComponents().getBoard()
                             + "/pawns/" + _user.getName().toLowerCase() + "/roll")
                             .body(this.gson.toJson(throwOnBoard)).asString().getBody();

        System.out.println("BOARDSTRING : " + boardString + "   URI: " + _game.getComponents().getBoard()
                + "/pawns/" + _user.getName().toLowerCase() + "/roll");

        BoardDTO board = gson.fromJson(boardString, BoardDTO.class);

        return board;
    }
}
