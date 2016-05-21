package client.adapter;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 18.05.2016.
 */
public class DiceAdapter {

    public DiceAdapter(){}

    public static HttpResponse<Integer> getDiceRollNumber() throws UnirestException {
       return  Unirest.get("http://172.18.0.83:4567/dice").asObject(Integer.class);

    }
}
