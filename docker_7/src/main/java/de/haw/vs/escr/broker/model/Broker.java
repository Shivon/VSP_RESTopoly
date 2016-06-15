package de.haw.vs.escr.broker.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 13.06.2016.
 */
public class Broker {
    private int brokerId;

    @Expose
    private String id;

    @Expose
    private String game;

    @Expose
    private String estates;

    public Broker() {
    }

    public int getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(int brokerId) {
        this.brokerId = brokerId;
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
}
