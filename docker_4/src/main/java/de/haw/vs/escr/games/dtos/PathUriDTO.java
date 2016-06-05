package de.haw.vs.escr.games.dtos;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 05.06.2016.
 */
public class PathUriDTO {
    @Expose
    private String id;

    public PathUriDTO() {
    }

    public PathUriDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
