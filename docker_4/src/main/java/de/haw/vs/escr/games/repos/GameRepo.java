package de.haw.vs.escr.games.repos;

import de.haw.vs.escr.games.models.Game;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 30.04.2016.
 */
public class GameRepo {
    private List<Game> gameList;
    private int gameCounter = 1;

    public GameRepo() {
        this.gameList = new ArrayList<>();
    }

    public Game saveGame(Game game) {
        if (this.gameList.stream().anyMatch(g -> g.getGameId() == game.getGameId())) return this.updateGame(game);
        game.setGameId(this.getGameCounter());
        this.gameList.add(game);
        return this.findGame(game.getGameId());
    }

    private Game updateGame(Game game) {
        this.deleteGame(game);
        this.gameList.add(game);
        return this.findGame(game.getGameId());
    }

    public void deleteGame(Game game) {
        this.gameList.removeIf(g -> g.getGameId() == game.getGameId());
    }

    public Game findGame(int gameid) {
        return this.gameList.stream().filter(g -> g.getGameId() == gameid).findFirst().get();
    }

    public List<Game> findAllGames() {
        return this.gameList;
    }

    private int getGameCounter() {
        return this.gameCounter++;
    }
}
    /*private EntityManager em = PersistenceService.getEntityManager();

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

    public List<Game> findAllGames() {
        try {
            em.getTransaction().begin();
            List<Game> games = em.createQuery("select ga from Game ga").getResultList();
            em.getTransaction().commit();
            return games;
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        return null;
    }
}*/
