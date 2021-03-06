package de.haw.vs.escr.boards.repos;

import de.haw.vs.escr.boards.models.entities.Pawn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 07.06.2016.
 */
public class PawnRepo {
    private int counter;

    private List<Pawn> pawnList;

    public PawnRepo() {
        this.pawnList = new ArrayList<>();
        this.counter = 1;
    }

    public Pawn savePawn(Pawn pawn) {
        if (this.pawnList.stream().anyMatch(b -> b.getName().equals(pawn.getName()))) return this.updatePawn(pawn);
        this.pawnList.add(pawn);
        return this.pawnList.stream().filter(b -> b.getPawnURI() == pawn.getPawnURI()).findFirst().get();
    }

    private Pawn updatePawn(Pawn pawn) {
        this.deletePawn(pawn);
        this.pawnList.add(pawn);
        System.out.println(pawn.getName());
        return pawn;
    }


    public void deletePawn(Pawn pawn) {
        this.pawnList.removeIf((b -> b.getName().equals(pawn.getName())));
    }


    public List<Pawn> findAllPawns() {
        return this.pawnList;
    }

    public Pawn findPawnByPawnName(String pawnName) {
        Pawn pawn = null;
        try {
            pawn = this.pawnList.stream().filter(p -> p.getName().equals(pawnName)).findFirst().get();
        } catch (Exception e) {}
        return pawn;
    }
}
