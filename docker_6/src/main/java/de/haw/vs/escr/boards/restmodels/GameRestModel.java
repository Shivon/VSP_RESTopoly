package de.haw.vs.escr.boards.restmodels;

/**
 * Created by Eric on 15.06.2016.
 */
public class GameRestModel {
    private final String path;
    private final String COMPONENTS ="components";
    private final String TURN = "players/turn";

    public GameRestModel(String path) {
        this.path = path;
    }

    public String getComponentsRoute(){
        return String.format("%s/%s", this.path, this.COMPONENTS);
    }

    public String getTurnRoute(){
        return String.format("%s/s", this.path, this.TURN);
    }
}
