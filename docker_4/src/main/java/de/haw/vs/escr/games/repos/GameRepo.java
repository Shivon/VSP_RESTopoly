package de.haw.vs.escr.games.repos;

import de.haw.vs.escr.games.models.Game;
import de.haw.vs.escr.games.util.PersistenceService;

import javax.persistence.EntityManager;

/**
 * Created by Christian on 30.04.2016.
 */
public class GameRepo {
    private EntityManager em = PersistenceService.getEntityManager();

    public Game saveGame(Game game) {
        Game savedGame = null;
        try {
            em.getTransaction().begin();
            savedGame = em.merge(game);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return savedGame;
    }

    public void deleteGame(Game game) {
        try {
            em.getTransaction().begin();
            em.remove(game);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public Game findGame(int gameid) {
        try {
            em.getTransaction().begin();
            Game game = em.find(Game.class, gameid);
            em.getTransaction().commit();
            return game;
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return null;
    }
}
