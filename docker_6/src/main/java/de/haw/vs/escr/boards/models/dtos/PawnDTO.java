package de.haw.vs.escr.boards.models.dtos;

import com.google.gson.annotations.Expose;
import de.haw.vs.escr.boards.models.entities.Pawn;

/**
 * Created by Eric on 07.06.2016.
 */
public class PawnDTO {

    @Expose
    private String id;
    @Expose(serialize = false)
    private String pawnURI;
    @Expose
    private String place;
    @Expose
    private int position;
    @Expose(serialize = false)
    private String player;

    public String getPawnURI() {
        return pawnURI;
    }

    public void setPawnURI(String pawnURI) {
        this.pawnURI = pawnURI;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Pawn toEntity(){
        Pawn p = new Pawn();
        p.setPlayerURI(this.getPlayer());
        p.setPosition(this.getPosition());
        p.setPlaceURI(this.getPlace());
        return p;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
