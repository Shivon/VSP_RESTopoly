package client.adapter;

import client.model.Board;
import client.model.User;
import client.model.gameModels.Game;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
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
        String rollString = Unirest.get(_ipAdresses.boardsIP() + "/boards/" + _game.getGameId()
                + "/pawns/" + _user.getName() + "/roll").asString().getBody();
//        TODO was gibt das get zurück????
        int number = gson.fromJson(rollString, Integer.class);
        System.out.println("" + number);
        return  number;
    }

    public void postDiceRollOnBoard(Game game, User user) throws UnirestException {
        _game = game;
        _user = user;
//        TODO was wird geposted?
        _board = _game.getComponents().getBoard();
        Unirest.post(_ipAdresses.boardsIP() + "/boards/" + _game.getGameId()
                + "/pawns/" + _user.getName() + "/roll").body(this.gson.toJson(_board)).asJson();
    }
}
