package de.haw.vs.escr.games.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.persistence.*;
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

    @Column(name = "players")
    @Expose
    private String players;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Expose
    private Paths services;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Expose
    private Paths components;

    public Game() {

    }

    public Game(String uri, String name, String players, Paths services, Paths components) {
        this.uri = uri;
        this.name = name;
        this.players = players;
        this.services = services;
        this.components = components;
    }

    public int getGameId() {
        return gameId;
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
}
