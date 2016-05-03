package de.haw.vs.escr.boards.repos;

import de.haw.vs.escr.boards.models.Board;
import de.haw.vs.escr.boards.util.PersistenceService;

import javax.persistence.EntityManager;

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
}
