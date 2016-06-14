package de.haw.vs.escr.boards.models.dtos;

import com.google.gson.annotations.Expose;

/**
 * Created by Eric on 14.06.2016.
 */
public class PawnPostDTO {
    @Expose
    private String name;
    @Expose
    private String place;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
