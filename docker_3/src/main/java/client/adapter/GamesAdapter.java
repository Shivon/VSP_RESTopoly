package client.adapter;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;

/**
 * Created by Jana Mareike on 17.05.2016.
 */
public class GamesAdapter {


    public GamesAdapter(){

    }

    public static HttpResponse<ArrayList> getGames() throws UnirestException {
       return   Unirest.get("http://localhost:4567/games")
               .asObject(ArrayList.class);
    }

    //
//    public void postGames() {
//        Unirest.post("")
//    }
}
