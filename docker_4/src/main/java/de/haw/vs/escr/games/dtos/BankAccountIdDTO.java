package de.haw.vs.escr.games.dtos;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 23.06.2016.
 */
public class BankAccountIdDTO {
    @Expose
    private String id;

    public BankAccountIdDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
