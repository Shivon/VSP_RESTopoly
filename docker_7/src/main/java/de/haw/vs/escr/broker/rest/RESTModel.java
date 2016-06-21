package de.haw.vs.escr.broker.rest;

/**
 * Created by Christian on 20.06.2016.
 */
public class RESTModel {
    private final String BROKER = "/broker";
    private final String OWNER = "/owner";
    private final String VISIT = "/visit";
    private final String PLACES = "/places";
    private final String HYPO = "/hypothecarycredit";

    public String getOwnerRoute(int gameId, int placeId) {
        return this.BROKER + "/" + gameId + this.PLACES + "/" + placeId + this.OWNER;
    }

    public String getVisitRoute(int gameId, int placeId) {
        return this.BROKER + "/" + gameId + this.PLACES + "/" + placeId + this.VISIT;
    }

    public String getHypoRoute(int gameId, int placeId) {
        return this.BROKER + "/" + gameId + this.PLACES + "/" + placeId + this.HYPO;
    }

    public String getPlaceRoute(int gameId, int placeId) {
        return this.BROKER + "/" + gameId + this.PLACES + "/" + placeId;
    }

    public String getEstatesRoute(int gameId) {
        return this.BROKER + "/" + gameId + this.PLACES;
    }

    public String getBrokerRoute(int gameId) {
        return this.BROKER + "/" + gameId;
    }
}
