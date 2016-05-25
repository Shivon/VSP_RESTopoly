package de.haw.vs.escr.games.repos;

import de.haw.vs.escr.games.models.Player;
import de.haw.vs.escr.games.util.PersistenceService;

import javax.persistence.EntityManager;

/**
 * Created by Christian on 04.05.2016.
 */
public class PlayerRepo {
    private EntityManager em = PersistenceService.getEntityManager();

    public PlayerRepo() {
    }

    public Player savePlayer(Player player) {
        Player savedPlayer = null;
        try {
            em.getTransaction().begin();
            savedPlayer = em.merge(player);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return savedPlayer;
    }

    public void deletePlayer(Player player) {
        try {
            em.getTransaction().begin();
            em.remove(player);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
    }

    public Player findPlayer(int playerId) {
        try {
            em.getTransaction().begin();
            Player player = em.find(Player.class, playerId);
            em.getTransaction().commit();
            return player;
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return null;
    }
}
