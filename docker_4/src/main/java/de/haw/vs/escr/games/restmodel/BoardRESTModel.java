package de.haw.vs.escr.games.restmodel;

/**
 * Created by Christian on 07.06.2016.
 */
public class BoardRESTModel {
    private final String pawns = "pawns";
    private final String componentPath;
    private final String servicePath;

    public BoardRESTModel(String componentPath, String servicePath) {
        this.componentPath = componentPath;
        this.servicePath = servicePath;
    }

    public String getPawnsRoute() {
        return String.format("%s/$s", this.componentPath, this.pawns);
    }

    public String getPawnsServiceRoute() {
        return String.format("%s/%s", this.servicePath, this.pawns);
    }
}
