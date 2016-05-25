package de.haw.vs.escr.games.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import de.haw.vs.escr.games.dtos.PlayerDTO;

import javax.persistence.*;

/**
 * Created by Christian on 03.05.2016.
 */
@Entity
@Table(name = "Player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerId")
    @Expose(deserialize = false)
    private int playerId;

    @Column(name = "uri", unique = true)
    @Expose(deserialize = false)
    @SerializedName("id")
    private String uri;

    @Column(name = "user", unique = true, nullable = false)
    @Expose
    private String user;

    @Column(name = "pawn")
    @Expose
    private String pawn;

    @Column(name = "account")
    @Expose
    private String account;

    @Column(name = "turn")
    private boolean turn;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Expose
    private Ready ready;

    public Player() {
        this.turn = false;
    }

    public Player(String uri, String user, String pawn, String account, Ready ready) {
        this.uri = uri;
        this.user = user;
        this.pawn = pawn;
        this.account = account;
        this.ready = ready;
        this.turn = false;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String id) {
        this.uri = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPawn() {
        return pawn;
    }

    public void setPawn(String pawn) {
        this.pawn = pawn;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Ready getReady() {
        return ready;
    }

    public void setReady(Ready ready) {
        this.ready = ready;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public PlayerDTO toDTO() {
        String readyUri = this.getUri() + "/ready";
        return new PlayerDTO(this.getPlayerId(), this.getUri(), this.getUser(), this.getPawn(), this.getAccount(), readyUri);
    }
}
