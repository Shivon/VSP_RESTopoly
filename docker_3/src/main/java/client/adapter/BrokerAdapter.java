package client.adapter;

import client.model.boardModels.Place;
import client.model.gameModels.Player;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 13.06.2016.
 */
public class BrokerAdapter {

    private Gson gson;

    public BrokerAdapter(){
        gson = new Gson();

    }

    public Player getOwner(Place place ) throws UnirestException {
        String ownerString = Unirest.get(place.getBrokerURI() + place.getUri() + "/owner").asString().getBody();

        Player owner = gson.fromJson(ownerString, Player.class);

        return owner;
    }

    public String getPriceOfPlace(Place place) throws UnirestException {
        String placeString = Unirest.get(place.getBrokerURI() + place.getUri()).asString().getBody();

        Place placeObj = gson.fromJson(placeString, Place.class);
// TODO getCost
//        return placeObj.getCost.toString;
        return "";
    }

    public String getRent(Place place) throws UnirestException {
        String placeString = Unirest.get(place.getBrokerURI() + place.getUri()).asString().getBody();

        Place placeObj = gson.fromJson(placeString, Place.class);
// TODO getCost
//        return placeObj.getRent.toString;
        return "";
    }

    public void buyPlace(Place place, Player player) throws UnirestException {

        Unirest.post(place.getBrokerURI() + "/places/" + place.getPlaceId() + "/owner")
                .body(gson.toJson(player)).asJson().getBody();
    }
}
