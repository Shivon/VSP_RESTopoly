package client.model;

import client.model.gameModels.Game;
import com.google.gson.annotations.Expose;

/**
 * Created by Jana Mareike on 21.06.2016.
 */
public class Subscription {

    @Expose
    private String uri;
    private Game game;
    private Event event;



    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
