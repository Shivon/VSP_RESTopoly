package de.haw.vs.escr.broker.adapter.games.routes;

/**
 * Created by Christian on 21.06.2016.
 */
public class GameRestModel {
    private final String AUTHORITY;
    private final String PROTOCOL = "http://";
    private final String GAMES = "/games";

    public GameRestModel(String authority) {
        this.AUTHORITY = authority;
    }

    public String getGameAddress() {
        return this.PROTOCOL + this.AUTHORITY;
    }

    public String getGameAddress(String query) {
        return this.getGameAddress() + query;
    }

    public String getGamePath() {
        return this.getGameAddress() + this.GAMES;
    }

    public String getGamePath(int gameId) {
        return this.getGameAddress() + this.GAMES + "/" + gameId;
    }
}
