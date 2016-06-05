package de.haw.vs.escr.boards.businesslogic;

import de.haw.vs.escr.boards.models.Board;
import de.haw.vs.escr.boards.models.Pawn;
import de.haw.vs.escr.boards.repos.BoardRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 03.05.2016.
 */
public class BoardServiceBusinessLogic {
    private BoardRepo boardRepo;

    List<Board> boardList;

    public BoardServiceBusinessLogic() {
        this.boardRepo = new BoardRepo();
        this.boardList = new ArrayList<>();
    }

    public List<Board> findAllBoards() {
        return this.boardList;
    }

    public Board createBoard(String gameURI) {
        Board b1 = new Board();
        b1.setUri("/boards/" + boardList.size());
        b1.setGameURI(gameURI);
        boardList.add(b1);
        return b1;
    }

    public Board findBoardByGameId(String gameId) {
        for (Board b : boardList) {
            if(b.getGameURI() == gameId) return b;
        }
        return null;
    }

    public void deleteBoard(Board board) {
        boardRepo.deleteBoard(board);
    }

    public List<String> getAllPawns(Board board) {
        List<String> ret = new ArrayList<>();
        for(Pawn p : board.getAllPawns()){
            ret.add("/boards/"+board.getBoardId()+"/pawns/"+p.getId());
        }
        return ret;
    }

    public Pawn findPawnByPawnId(String pawnId, Board board) {
        for(Pawn p : board.getAllPawns()){
            if(p.getId().equals(pawnId)){
                return p;
            }
        }
        return null;
    }

    public void addPawn(Pawn pawn, Board board) {
        board.addPawn(pawn);
    }

    public void updatePawn(String pawnId, Board board) {
        List<Pawn> pawns = board.getAllPawns();
        Pawn pawn = this.findPawnByPawnId(pawnId, board);
        pawns.remove(pawn);
    }
}
