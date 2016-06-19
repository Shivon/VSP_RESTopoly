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

    private Gson gson;

    public BoardsAdapter(){
        gson = new Gson();
    }

    public Place getCurrentPlace(Game game, User user) throws UnirestException {
        String pawnDTOString = Unirest.get(game.getUri() + game.getComponents().getBoard()
                + "/pawns/" + user.getName().toLowerCase())
                .asString().getBody();
        PawnDTO pawnDTO = gson.fromJson(pawnDTOString, PawnDTO.class);
        Pawn pawn = pawnDTO.toEntity();
        String placeString = Unirest.get(pawn.getPlaceURI()).asString().getBody();
        Place place = gson.fromJson(placeString, Place.class);
        return place;
    }

}
