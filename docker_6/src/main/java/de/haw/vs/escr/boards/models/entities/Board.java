package de.haw.vs.escr.boards.models.entities;

import de.haw.vs.escr.boards.models.dtos.BoardDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 03.05.2016.
 */
//@Entity
//@Table(name = "Board")
public class Board {
    private int boardId;
    private String uri;

    private String gameURI;


    /*
     * spieler (playerList) an der stelle i soll an stelle i in positions stehen
     * players: {A, B, C}
     * positions: [3, 1, 2}
     */
    private int[] positions;

    private List<Field> fields;

    private String playerListURI;

    public Board() {
        this.fields = new ArrayList<>();
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

    public String getGameURI() {
        return gameURI;
    }

    public void setGameURI(String gameURI) {
        this.gameURI = gameURI;
    }

    public List<String> getAllPawns() {
        List<String> ret = new ArrayList<>();
        for (Field f: fields) {
            ret.addAll(f.getPawns());
        }
        return ret;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public BoardDTO toBoardDTO(){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(this.uri);
        boardDTO.setGame(this.gameURI);
        boardDTO.setPositions(this.positions);
        boardDTO.setFields(this.fields);
        return boardDTO;
    }

    public String getPlayerListURI() {
        return playerListURI;
    }

    public void setPlayerListURI(String playerListURI) {
        this.playerListURI = playerListURI;
    }

    public int[] getPositions() {
        return positions;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }

    public void updateFields(List<Field> fields) {
        this.fields = fields;
    }

}
