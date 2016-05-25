package client.adapter;

import client.model.gameModels.Game;
import client.adapter.IPAdresses;
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
    private IPAdresses _ipAdresses;

    public GamesAdapter(){
        _ipAdresses = new IPAdresses();
    }

    public Game[] getGames() throws UnirestException {
        String games = Unirest.get(_ipAdresses.gamesIP() + "/games")
                .asString().getBody();
        Game[] gamesList = gson.fromJson(games, Game[].class);
        System.out.println("games list " +gamesList);
        System.out.println(gamesList[0].getName());
        System.out.println(gamesList[0].getUri());
        System.out.println("id first game: " +
                gamesList[0].getGameId() + ", id second game: " + gamesList[1].getGameId()+ ", Länge GamesList: " + gamesList.length);
        return gamesList;
    }

    //
//    public void postGames() {
//        Unirest.post("")
//    }
}
