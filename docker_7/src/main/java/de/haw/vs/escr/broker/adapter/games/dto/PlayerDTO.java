package de.haw.vs.escr.broker.adapter.games.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Christian on 21.06.2016.
 */
public class PlayerDTO {
    @Expose
    @SerializedName("id")
    private String uri;

    @Expose
    private String user;

    @Expose
    private String pawn;

    @Expose
    private String account;

    @Expose
    private String ready;

    public PlayerDTO() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public String getReady() {
        return ready;
    }

    public void setReady(String ready) {
        this.ready = ready;
    }
}
