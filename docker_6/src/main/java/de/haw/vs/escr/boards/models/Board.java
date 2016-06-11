package de.haw.vs.escr.boards.models;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Eric on 03.05.2016.
 */
//@Entity
//@Table(name = "Board")
public class Board {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardId;

    //@Column(name = "uri")
    @Expose
    private String uri;

    //@Column(name = "gameURI")
    private String gameURI;

    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Expose
    private Field[] fields;

    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //@Column(name = "position")
    private int[] positions;

    //@Column(name = "playerListURI")
    @Expose
    private String playerListURI;

    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    //private List<Pawn> pawns;

    public Board() {

    }

    public Board(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getGameURI() {
        return gameURI;
    }

    public void setGameURI(String gameURI) {
        this.gameURI = gameURI;
    }

    public List<Pawn> getAllPawns() {
        //return this.pawns;
        return null;
    }

    public void addPawn(Pawn pawn) {
        //this.pawns.add(pawn);
    }
}
