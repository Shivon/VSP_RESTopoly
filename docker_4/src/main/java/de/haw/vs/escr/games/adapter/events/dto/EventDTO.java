package de.haw.vs.escr.games.adapter.events.dto;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 22.06.2016.
 */
public class EventDTO {
    @Expose(serialize = false)
    private String id;

    @Expose
    private String game;

    @Expose
    private String type;

    @Expose
    private String name;

    @Expose
    private String reason;

    @Expose
    private String resource;

    @Expose
    private String player;

    public EventDTO() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
