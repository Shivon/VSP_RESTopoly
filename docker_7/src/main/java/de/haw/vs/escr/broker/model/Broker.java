package de.haw.vs.escr.broker.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 13.06.2016.
 */
public class Broker {
    private int gameId;

    @Expose
    private String id;

    @Expose
    private String game;

    @Expose
    private String estates;

    private List<Place> places;

    public Broker() {
        this.places = new ArrayList<>();
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getEstates() {
        return estates;
    }

    public void setEstates(String estates) {
        this.estates = estates;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public void addPlace(Place place) {
        this.places.add(place);
    }
}
