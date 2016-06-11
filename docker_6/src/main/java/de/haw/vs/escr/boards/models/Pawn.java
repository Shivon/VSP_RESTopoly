package de.haw.vs.escr.boards.models;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Eric on 25.05.2016.
 */
//@Entity
//@Table(name = "Pawn")
public class Pawn {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    int pawnId;

    //@Column(name = "ID")
    @Expose
    private String id;

    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Throw> throwList;

    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Integer> moveList;

    //@Column(name = "PlayerURI")
    private String playerURI;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPawnId() {
        return pawnId;
    }

    public void setPawnId(int pawnId) {
        this.pawnId = pawnId;
    }

    public String getPlayerURI() {
        return playerURI;
    }

    public void setPlayerURI(String playerURI) {
        this.playerURI = playerURI;
    }

    public List<Throw> getThrowList() {
        return throwList;
    }

    public void setThrowList(List<Throw> throwList) {
        this.throwList = throwList;
    }

    public List<Integer> getMoveList() {
        return moveList;
    }

    public void setMoveList(List<Integer> moveList) {
        this.moveList = moveList;
    }

}
