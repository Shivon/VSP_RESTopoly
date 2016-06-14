package de.haw.vs.escr.boards.models.dtos;

import de.haw.vs.escr.boards.models.entities.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 12.06.2016.
 */
public class FieldDTO {
    private String place;
    private List<String> pawns;

    public FieldDTO(){
        this.pawns = new ArrayList<>();
    }

    public List<String> getPawns() {
        return pawns;
    }

    public void setPawns(List<String> pawns) {
        this.pawns = pawns;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Field toEntity(){
        Field f = new Field();
        f.setPawns(this.pawns);
        return f;
    }
}
