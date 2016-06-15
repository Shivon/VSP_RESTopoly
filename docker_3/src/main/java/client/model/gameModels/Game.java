package client.model.gameModels;

import client.model.dtos.GameDTO;
import client.model.dtos.PlayerURI;
import client.model.dtos.StatusDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 29.04.2016.
 */
public class Game {
    private int gameId;

    @Expose(deserialize = false)
    @SerializedName("id")
    private String uri;

    @Expose
    private String name;

    @Expose
    private List<Player> players;

    @Expose
    private Paths services;

    @Expose
    private Paths components;

    private GameStatus status;

    public Game() {
        this.status = GameStatus.registration;
        this.players = new ArrayList<>();
    }

    public Game(String uri, String name, List<Player> players, Paths services, Paths components) {
        this.uri = uri;
        this.name = name;
        this.players = players;
        this.services = services;
        this.components = components;
        this.status = GameStatus.registration;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Paths getServices() {
        return services;
    }

    public void setServices(Paths services) {
        this.services = services;
    }

    public Paths getComponents() {
        return components;
    }

    public void setComponents(Paths components) {
        this.components = components;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public GameDTO toDTO() {
        String serviceURI = this.getUri() + "/services";
        String componentURI = this.getUri() + "/components";
        String playerURI = this.getUri() + "/players";
        GameDTO gDto = new GameDTO(this.getGameId(), this.getUri(), this.getName(), playerURI, serviceURI, componentURI);
        return gDto;
    }

    public StatusDTO toStatus() {
        return new StatusDTO(this.getStatus());
    }

    public PlayerURI getPlayerURIs() {
        PlayerURI pu = new PlayerURI();
        for (Player p : this.players) {
            pu.addURIToPlayers(p.getUri());
        }
        return pu;
    }
}
