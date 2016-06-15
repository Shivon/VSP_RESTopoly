package de.haw.vs.escr.boards.repos;

import de.haw.vs.escr.boards.models.entities.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 03.05.2016.
 */
public class BoardRepo {

    private int counter;

    private List<Board> boardList;

    public BoardRepo() {
        this.boardList = new ArrayList<>();
        this.counter = 1;
    }

    public Board saveBoard(Board board) {
        System.out.println("------- BOARD REPO: Saving Board: "+board.getUri());
        if (this.boardList.stream().anyMatch(b -> b.getBoardId() == board.getBoardId())) return this.updateBoard(board);
        board.setUri("/boards/"+board.getBoardId());
        this.boardList.add(board);
        return this.boardList.stream().filter(b -> b.getBoardId() == board.getBoardId()).findFirst().get();
    }

    private Board updateBoard(Board board) {
        this.deleteBoard(board);
        this.boardList.add(board);
        return board;
    }


    public void deleteBoard(Board board) {
        this.boardList.removeIf((b -> b.getBoardId() == board.getBoardId()));
    }


    public List<Board> findAllBoards() {
        return this.boardList;
    }

    public Board findBoardByGameId(String gameId) {
        System.out.println("gameId = " + gameId);
        return this.boardList.stream().filter(b -> b.getGameURI().equals(gameId)).findFirst().get();
    }
}
