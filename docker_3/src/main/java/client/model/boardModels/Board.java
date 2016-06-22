package client.model.boardModels;

//import de.haw.vs.escr.boards.models.dtos.BoardDTO;
//import de.haw.vs.escr.boards.models.dtos.FieldDTO;

import client.model.dtos.FieldDTO;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eric on 03.05.2016.
 */
//@Entity
//@Table(name = "Board")
public class Board {

    @Expose
    private String id;
//    @Expose
//    private String uri;
//    @Expose
//    private Paths paths;
    @Expose
    private String game;

    /*
     * spieler (playerList) an der stelle i soll an stelle i in positions stehen
     * players: {A, B, C}
     * positions: [3, 1, 2}
     */
    @Expose
    private List<Integer> positions;
    @Expose
    private List<FieldDTO> fields;
    @Expose
    private String playerListURI;

    public Board() {
        this.positions = new ArrayList<>();
        this.fields = new ArrayList<>();
    }

//    public Board(String uri) {
//        this.uri = uri;
//    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

//    public String getUri() {
//        return uri;
//    }

//    public void setUri(String uri) {
//        this.uri = uri;
//    }

    public String getBoardId() {
        return id;
    }

    public String getGame() {
        return game;
    }

    public void setGameURI(String game) {
        this.game = game;
    }

    public List<FieldDTO> getFields(){return fields;}

    public List<String> getAllPawns() {
        List<String> ret = new ArrayList<>();
        for (FieldDTO f : fields) {
            ret.addAll(f.getPawns());
        }
        return ret;
    }

    public void setBoardId(int boardId) {
        this.id = id;
    }

//    public BoardDTO toBoardDTO() {
//        ArrayList<Integer> positions = this.positions.stream().map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
//        BoardDTO boardDTO = new BoardDTO();
//        boardDTO.setId(this.uri);
//        boardDTO.setGame(this.gameURI);
//        boardDTO.setPositions(positions);
//        List<FieldDTO> fieldDTOs = fields.stream().map(Field::toDTO).collect(Collectors.toList());
//        boardDTO.setFields(fieldDTOs);
//        return boardDTO;
//    }

//    public String getPlayerListURI() {
//        return playerListURI;
//    }

//    public void setPlayerListURI(String playerListURI) {
//        this.playerListURI = playerListURI;
//    }

    public void updateFields(List<FieldDTO> fields) {
        this.fields = fields;
    }

    public void addPawnToPosition(int placeId, int idOnBoard) {
        System.out.println("------ CHECK: "+this.positions.size() +" >= "+ placeId+1);
        if (this.positions.size() >= placeId + 1) this.positions.remove(placeId);
        this.positions.add(placeId, Integer.valueOf(idOnBoard));
    }

//    public Paths getPaths() {
//        return paths;
//    }

//    public void setPaths(Paths paths) {
//        this.paths = paths;
//    }
}
