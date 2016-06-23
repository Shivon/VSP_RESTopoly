package de.haw.vs.escr.boards.models.dtos;

import com.google.gson.annotations.Expose;

/**
 * Created by Eric on 15.06.2016.
 */
public class PlayerDTO {
    @Expose
    private int playerId;
    @Expose
    private String id;
    @Expose
    private String user;
    @Expose
    private String pawn;
    @Expose
    private String account;
    @Expose
    private ReadyDTO ready;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ReadyDTO getReady() {
        return ready;
    }

    public void setReady(ReadyDTO ready) {
        this.ready = ready;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
