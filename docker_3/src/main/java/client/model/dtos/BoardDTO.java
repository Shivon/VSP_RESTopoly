package client.model.dtos;

import client.model.boardModels.Board;
import client.model.boardModels.Field;
import com.google.gson.annotations.Expose;


import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 08.06.2016.
 */
public class BoardDTO {
    private String game;
    @Expose
    private String id;

    @Expose
    private List<Field> fields;

    @Expose
    private List<Integer> positions;

    public BoardDTO(){
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

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
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
