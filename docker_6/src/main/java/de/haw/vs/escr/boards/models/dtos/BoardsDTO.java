package de.haw.vs.escr.boards.models.dtos;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 08.06.2016.
 */
public class BoardsDTO {
    @Expose
    private List<String> boards;

    public BoardsDTO(){
        this.boards = new ArrayList<>();
    }

    public List<String> getBoards() {
        return boards;
    }

    public void setBoards(List<String> boards) {
        this.boards = boards;
    }

    public void addBoard(String boardURI){
        this.boards.add(boardURI);
    }
}
