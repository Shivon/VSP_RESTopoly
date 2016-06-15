package de.haw.vs.escr.boards.models.entities;

import de.haw.vs.escr.boards.models.dtos.BoardDTO;
import de.haw.vs.escr.boards.models.dtos.FieldDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eric on 03.05.2016.
 */
//@Entity
//@Table(name = "Board")
public class Board {
    private int boardId;
    private String uri;
    private Paths paths;
    private String gameURI;

    /*
     * spieler (playerList) an der stelle i soll an stelle i in positions stehen
     * players: {A, B, C}
     * positions: [3, 1, 2}
     */
    private List<String> positions;

    private List<Field> fields;

    private String playerListURI;

    public Board() {
        this.positions = new ArrayList<>();
        this.fields = new ArrayList<>();
    }

    public Board(String uri) {
        this.uri = uri;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
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
        for (Field f : fields) {
            ret.addAll(f.getPawns());
        }
        return ret;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public BoardDTO toBoardDTO() {
        ArrayList<Integer> positions = this.positions.stream().map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(this.uri);
        boardDTO.setGame(this.gameURI);
        boardDTO.setPositions(positions);
        List<FieldDTO> fieldDTOs = fields.stream().map(Field::toDTO).collect(Collectors.toList());
        boardDTO.setFields(fieldDTOs);
        return boardDTO;
    }

    public String getPlayerListURI() {
        return playerListURI;
    }

    public void setPlayerListURI(String playerListURI) {
        this.playerListURI = playerListURI;
    }

    public void updateFields(List<Field> fields) {
        this.fields = fields;
    }

    public void addPawnToPosition(int placeId, int idOnBoard) {
        System.out.println("------ CHECK: "+this.positions.size() +" >= "+ placeId+1);
        if (this.positions.size() >= placeId + 1) this.positions.remove(placeId);
        this.positions.add(placeId, String.valueOf(idOnBoard));
    }

    public Paths getPaths() {
        return paths;
    }

    public void setPaths(Paths paths) {
        this.paths = paths;
    }
}
