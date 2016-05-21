package client.adapter;

import client.model.gameModels.Game;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jana Mareike on 17.05.2016.
 */
public class GamesAdapter {

    Gson gson = new Gson();

    public GamesAdapter(){

    }

    public Game[] getGames() throws UnirestException {
        String games = Unirest.get("http://172.18.0.86:4567/games")
                .asString().getBody();
        Game[] gamesList = gson.fromJson(games, Game[].class);

        return gamesList;
    }

    //
//    public void postGames() {
//        Unirest.post("")
//    }
}
