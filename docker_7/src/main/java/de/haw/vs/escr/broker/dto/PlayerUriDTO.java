package de.haw.vs.escr.broker.dto;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 21.06.2016.
 */
public class PlayerUriDTO {
    @Expose
    private String player;

    public PlayerUriDTO() {
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
