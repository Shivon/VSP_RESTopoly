package de.haw.vs.escr.games.restmodel;

/**
 * Created by Christian on 07.06.2016.
 */
public class BoardRESTModel {
    private final String pawns = "pawns";
    private final String componentPath;
    private final String servicePath;
    private final String place;

    public BoardRESTModel(String componentPath, String servicePath, String boardComponentPath) {
        this.componentPath = componentPath;
        this.servicePath = servicePath;
        this.place = String.format("%s/places", boardComponentPath);
    }

    public String getPawnsRoute() {
        return String.format("%s/$s", this.componentPath, this.pawns);
    }

    public String getPawnsServiceRoute() {
        return String.format("%s/%s", this.servicePath, this.pawns);
    }

    public String getInitialPlace() {
        return String.format("%s/0", this.place);
    }

    public String getInitialPosition() {
        return "0";
    }
}
