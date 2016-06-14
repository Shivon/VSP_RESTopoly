package de.haw.vs.escr.boards.models.dtos;

import com.google.gson.annotations.Expose;
import de.haw.vs.escr.boards.models.entities.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 08.06.2016.
 */
public class BoardDTO {
    @Expose(serialize = false)
    private String game;

    @Expose
    private String id;

    @Expose
    private List<FieldDTO> fields;

    @Expose
    private List<Integer> positions;

    public BoardDTO(){
        this.positions = new ArrayList<>();
        this.fields = new ArrayList<>();
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    public List<FieldDTO> getFields() {
        return fields;
    }

    public void setFields(List<FieldDTO> fields) {
        this.fields = fields;
    }

    public Board toEntity() {
        Board b = new Board();
        b.setGameURI(this.game);
        int index = game.lastIndexOf("/");
        int id = Integer.parseInt(game.substring(index+1));
        b.setBoardId(id);
        return b;
    }
}
