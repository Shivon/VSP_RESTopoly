package de.haw.vs.escr.boards.repos;

import de.haw.vs.escr.boards.models.entities.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 12.06.2016.
 */
public class MoveRepo {
    private Map<String, List<Move>> moveMap;

    public MoveRepo(){
        this.moveMap = new HashMap<>();
    }

    public List<Move> addMove(String movesURI, Move move){
        if(moveMap.containsKey(movesURI)) {
            List<Move> moves = moveMap.get(movesURI);
            moves.add(move);
            moveMap.put(movesURI, moves);
            return moves;
        }
        List<Move> moves = new ArrayList<>();
        moves.add(move);
        moveMap.put(movesURI, moves);
        return moves;
    }

    public List<Move> findAllMovesByMoveURI(String moveURI){
        return moveMap.get(moveURI);
    }
}
