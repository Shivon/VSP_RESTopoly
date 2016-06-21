package de.haw.vs.escr.broker.adapter.games.routes;

/**
 * Created by Christian on 21.06.2016.
 */
public class GameRestModel {
    private final String AUTHORITY;
    private final String PROTOCOL = "http://";

    public GameRestModel(String authority) {
        this.AUTHORITY = authority;
    }

    public String getGamePath() {
        return this.PROTOCOL + this.AUTHORITY;
    }
}
