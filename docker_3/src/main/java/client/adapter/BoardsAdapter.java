package client.adapter;

import client.model.User;
import client.model.boardModels.Pawn;
import client.model.boardModels.Place;
import client.model.dtos.PawnDTO;
import client.model.dtos.PlacesDTO;
import client.model.gameModels.Game;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 13.06.2016.
 */
public class BoardsAdapter {

    private Game _game;
    private User _user;
    private Gson gson;

    public BoardsAdapter(Game game, User user){
        _game = game;
        _user = user;
    }

    public Place getCurrentPlace() throws UnirestException {
        String pawnDTOString = Unirest.get(_game.getUri() + _game.getComponents().getBoard()
                + "/pawns/" + _user.getName().toLowerCase())
                .asString().getBody();

        PawnDTO pawnDTO = gson.fromJson(pawnDTOString, PawnDTO.class);

        Pawn pawn = pawnDTO.toEntity();

        String placeString = Unirest.get(pawn.getPlaceURI()).asString().getBody();

        Place place = gson.fromJson(placeString, Place.class);

        return place;

    }

}
