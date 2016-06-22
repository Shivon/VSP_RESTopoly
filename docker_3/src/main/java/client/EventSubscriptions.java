package client;


import client.adapter.IPAdresses;
import client.logic.GamesLogic;
import client.logic.UserLogic;
import client.logic.WaitLogic;
import client.model.Event;
import client.model.Subscription;
import client.model.User;
import client.view.WaitWindow;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 21.06.2016.
 */
public class EventSubscriptions {

    Gson gson = new Gson();
    private WaitLogic _waitLogic;
    private GamesLogic _gamesLogic;
    private UserLogic _userLogic;
    private IPAdresses _ipAdresses;

    public EventSubscriptions(IPAdresses ipAdresses, GamesLogic gamesLogic, UserLogic userLogic) throws UnirestException {
        _gamesLogic = gamesLogic;
        _userLogic = userLogic;
        _ipAdresses = ipAdresses;

        startGameEvent();
        moveEvent();
        rentEvent();
        buyEvent();
    }

    private void startGameEvent() throws UnirestException {
        Subscription startGame = new Subscription();
        startGame.setGame(_gamesLogic.getCurrentGame());
        startGame.setUri(_userLogic.getCurrentUser().getUri());

        Event event = new Event();
        event.setType("start");

        startGame.setEvent(event);
        System.out.println(_ipAdresses.eventsIP());
        Unirest.post(_ipAdresses.eventsIP() + "/subscriptions ").body(gson.toJson(startGame)).asString().getBody();
    }

    private void moveEvent() throws UnirestException {
        Subscription startGame = new Subscription();
        startGame.setGame(_gamesLogic.getCurrentGame());
        startGame.setUri(_userLogic.getCurrentUser().getUri());

        Event event = new Event();
        event.setType("move");

        startGame.setEvent(event);

        Unirest.post(_ipAdresses.eventsIP() + "/subscriptions ").body(gson.toJson(startGame)).asString().getBody();
    }


    private void rentEvent() throws UnirestException {
        Subscription startGame = new Subscription();
        startGame.setGame(_gamesLogic.getCurrentGame());
        startGame.setUri(_userLogic.getCurrentUser().getUri());

        Event event = new Event();
        event.setType("rent");

        startGame.setEvent(event);

        Unirest.post(_ipAdresses.eventsIP() + "/subscriptions ").body(gson.toJson(startGame)).asString().getBody();
    }

    private void buyEvent() throws UnirestException {
        Subscription startGame = new Subscription();
        startGame.setGame(_gamesLogic.getCurrentGame());
        startGame.setUri(_userLogic.getCurrentUser().getUri());

        Event event = new Event();
        event.setType("buy");

        startGame.setEvent(event);

        Unirest.post(_ipAdresses.eventsIP() + "/subscriptions ").body(gson.toJson(startGame)).asString().getBody();
    }
}
