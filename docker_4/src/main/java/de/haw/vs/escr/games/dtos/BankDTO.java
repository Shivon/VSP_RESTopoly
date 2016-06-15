package de.haw.vs.escr.games.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Christian on 15.06.2016.
 */
public class BankDTO {
    @Expose
    private String id;

    @Expose(deserialize = false)
    private String accounts;

    @Expose(deserialize = false)
    private String transfers;

    public BankDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccounts() {
        return accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public String getTransfers() {
        return transfers;
    }

    public void setTransfers(String transfers) {
        this.transfers = transfers;
    }
}
