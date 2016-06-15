package de.haw.vs.escr.games.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Christian on 15.06.2016.
 */
public class BoardDTO {
    @Expose
    private String id;

    @Expose(deserialize = false)
    private List<String> fields;

    @Expose(deserialize = false)
    private List<Integer> positions;

    public BoardDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }
}
