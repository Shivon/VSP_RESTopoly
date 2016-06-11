package de.haw.vs.escr.boards.repos;

import com.google.gson.annotations.Expose;
import de.haw.vs.escr.boards.models.Board;
import de.haw.vs.escr.boards.util.PersistenceService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Eric on 03.05.2016.
 */
public class BoardRepo {
    private final EntityManager entityManager;

    public BoardRepo() {
        entityManager = PersistenceService.getEntityManager();
    }

    public Board saveBoard(Board board) {
        Board savedBoard = null;
        try {
            entityManager.getTransaction().begin();
            savedBoard = entityManager.merge(board);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return savedBoard;
    }

    public void deleteBoard(Board board) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(board);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public Board findBoard(int boardId) {
        try {
            entityManager.getTransaction().begin();
            Board board = entityManager.find(Board.class, boardId);
            entityManager.getTransaction().commit();
            return board;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return null;
    }

    public List<Board> findAllBoards(){
        try{
            entityManager.getTransaction().begin();
            List<Board> boards = entityManager.createQuery("select bo from Board bo").getResultList();
            entityManager.getTransaction().commit();
            return boards;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return null;
    }

    public Board findBoardByGameId(int gameId) {
        try{
            entityManager.getTransaction().begin();
            //Board board = (Board) entityManager.createQuery("select bo from Board bo where bo.gameId = :gameId").getSingleResult();
            Query q = entityManager.createQuery("select bo from Board bo where bo.gameId = :gameId");
            q.setParameter("gameId", gameId);
            Board board = (Board) q.getSingleResult();
            entityManager.getTransaction().commit();
            return board;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return null;
    }
}
