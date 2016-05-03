package de.haw.vs.escr.games.dtos;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 02.05.2016.
 */
public class GameDTO {
    private int gameId;

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

    public GameDTO(int gameId, String uri, String name, String players, String services, String components) {
        this.gameId = gameId;
        this.uri = uri;
        this.name = name;
        this.players = players;
        this.services = services;
        this.components = components;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
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
