package de.haw.vs.escr.games.dtos;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 15.06.2016.
 */
public class BrokerDTO {
    @Expose
    private String id;

    @Expose(deserialize = false)
    private String game;

    @Expose(deserialize = false)
    private String estates;

    public BrokerDTO() {
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
