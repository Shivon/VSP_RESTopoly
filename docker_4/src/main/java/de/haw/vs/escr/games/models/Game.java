package de.haw.vs.escr.games.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import de.haw.vs.escr.games.dtos.GameDTO;
import de.haw.vs.escr.games.dtos.PlayerURI;
import de.haw.vs.escr.games.dtos.StatusDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Christian on 29.04.2016.
 */
@Entity
@Table(name = "Game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gameId")
    private int gameId;

    @Column(name = "uri", unique = true)
    @Expose
    @SerializedName("id")
    private String uri;

    @Column(name = "name", nullable = false)
    @Expose
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Expose
    private List<Player> players;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Expose
    private Paths services;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Expose
    private Paths components;

    @Enumerated(EnumType.STRING)
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
