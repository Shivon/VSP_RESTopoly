package de.haw.vs.escr.dice.dto;

import java.net.URI;

/**
 * Created by Christian on 13.04.2016.
 */
public class Event {
    private String id;
    private URI game;
    private String type;
    private String name;
    private String reason;
    private URI resource;
    private URI player;

    public Event() {
    }

    public Event(String id, URI game, String type, String name, String reason, URI resource, URI player) {
        this.id = id;
        this.game = game;
        this.type = type;
        this.name = name;
        this.reason = reason;
        this.resource = resource;
        this.player = player;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URI getGame() {
        return game;
    }

    public void setGame(URI game) {
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

    public URI getResource() {
        return resource;
    }

    public void setResource(URI resource) {
        this.resource = resource;
    }

    public URI getPlayer() {
        return player;
    }

    public void setPlayer(URI player) {
        this.player = player;
    }
}
