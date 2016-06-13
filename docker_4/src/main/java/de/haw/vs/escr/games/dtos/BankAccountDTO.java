package de.haw.vs.escr.games.dtos;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 12.06.2016.
 */
public class BankAccountDTO {
    @Expose(serialize = false)
    private String id;

    @Expose
    private String player;

    @Expose
    private int saldo;

    public BankAccountDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
