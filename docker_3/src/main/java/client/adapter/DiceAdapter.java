package client.adapter;

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

    public DiceAdapter(){
        _ipAdresses = new IPAdresses();
    }

    public int getDiceRollNumber() throws UnirestException {
        String diceNumber = Unirest.get(_ipAdresses.diceIP()).asString().getBody();
        int number = gson.fromJson(diceNumber, Integer.class);
        System.out.println("" + number);
        return  number;
    }
}
