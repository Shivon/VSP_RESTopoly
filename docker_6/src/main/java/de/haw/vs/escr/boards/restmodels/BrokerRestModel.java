package de.haw.vs.escr.boards.restmodels;

/**
 * Created by Eric on 20.06.2016.
 */
public class BrokerRestModel {
    private final String path;
    private final String VISIT = "/visit";

    public BrokerRestModel(String path) {
        this.path = path;
    }

    public String getVisitRoute(int placeId) {
        return path + "/places/" + placeId + VISIT;
    }

    public String getPlaceRoute(int gameId) {
        return path + "/" + gameId + "/places";
    }

}
