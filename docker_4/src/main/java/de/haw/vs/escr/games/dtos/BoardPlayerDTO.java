package de.haw.vs.escr.games.dtos;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 12.06.2016.
 */
public class BoardPlayerDTO {
    @Expose
    private String player;

    public BoardPlayerDTO(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
