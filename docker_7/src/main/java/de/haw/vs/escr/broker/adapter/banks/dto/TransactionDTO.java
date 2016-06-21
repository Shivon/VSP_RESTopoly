package de.haw.vs.escr.broker.adapter.banks.dto;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 21.06.2016.
 */
public class TransactionDTO {
    @Expose
    private String id;

    public TransactionDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
