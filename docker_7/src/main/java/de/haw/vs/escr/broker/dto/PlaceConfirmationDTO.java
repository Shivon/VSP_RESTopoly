package de.haw.vs.escr.broker.dto;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 20.06.2016.
 */
public class PlaceConfirmationDTO {
    @Expose
    private String type;

    @Expose
    private String description;

    public PlaceConfirmationDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
