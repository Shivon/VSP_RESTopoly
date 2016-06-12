package de.haw.vs.escr.boards.models.entities;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 04.05.2016.
 */
public class Field {
    private int fieldId;
    private int boardId;
    @Expose
    private String placeURI;

    private Place place;

    @Expose
    private List<String> pawns;

    public Field(){
        this.pawns = new ArrayList<>();
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
        this.placeURI = place.getUri();
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public List<String> getPawns() {
        return pawns;
    }

    public void setPawns(List<String> pawns) {
        this.pawns = pawns;
    }

    public Field addPawn(Pawn pawn) {
        this.pawns.add(pawn.getPawnURI());
        return this;
    }

    public void removePawn(Pawn pawn) {
        pawns.removeIf(p -> p.equals(pawn.getPawnURI()));
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }
}
