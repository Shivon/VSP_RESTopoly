package de.haw.vs.escr.games.dtos;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 12.06.2016.
 */
public class BoardPlayerDTO {
    @Expose
    private String player;

    @Expose
    private String place;

    @Expose
    private String position;

    public BoardPlayerDTO(String player, String place, String position) {
        this.player = player;
        this.place = place;
        this.position = position;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
