package de.haw.vs.escr.broker.adapter.games.dto;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 21.06.2016.
 */
public class GameDTO {
    @Expose
    private String uri;

    @Expose
    private String name;

    @Expose
    private String players;

    @Expose
    private String services;

    @Expose
    private String components;

    public GameDTO() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayers() {
        return players;
    }

    public void setPlayers(String players) {
        this.players = players;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }
}
