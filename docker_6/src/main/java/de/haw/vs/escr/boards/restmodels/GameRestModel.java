package de.haw.vs.escr.boards.restmodels;

/**
 * Created by Eric on 15.06.2016.
 */
public class GameRestModel {
    private final String path;
    private final String SERVICES ="services";
    private final String TURN = "players/turn";

    public GameRestModel(String path) {
        this.path = path;
    }

    public String getServicesRoute(){
        return String.format("%s/%s", this.path, this.SERVICES);
    }

    public String getTurnRoute(){
        return String.format("%s/s", this.path, this.TURN);
    }
}
