package de.haw.vs.escr.games.restmodel;

import de.haw.vs.escr.games.util.URLBuilder.URLBuilder;

/**
 * Created by Christian on 07.06.2016.
 */
public class BoardRESTModel {
    private final String pawns = "pawns";
    private final String componentPath;
    private final String servicePath;
    private final String place;

    public BoardRESTModel(String componentPath, String servicePath) {
        this.componentPath = componentPath;
        this.servicePath = servicePath;
        this.place = String.format("%s/places", componentPath);
    }

    public String getPawnsRoute() {
        return this.componentPath + "/" + this.pawns;
    }

    public String getPawnsServiceRoute() {
        return String.format("%s/%s", this.servicePath, this.pawns);
    }

    public String getInitialPlace() {
        URLBuilder ub = new URLBuilder(this.place);
        return ub.getPath() + "/0";
    }

    public String getInitialPosition() {
        return "0";
    }
}
