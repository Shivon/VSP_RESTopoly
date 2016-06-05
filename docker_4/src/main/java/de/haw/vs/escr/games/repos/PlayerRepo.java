package de.haw.vs.escr.games.repos;

import de.haw.vs.escr.games.models.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 04.05.2016.
 */
public class PlayerRepo {
    private List<Player> playerList;
    private int playerCounter = 1;

    public PlayerRepo() {
        this.playerList = new ArrayList<>();
    }

    public Player savePlayer(Player player) {
        if (this.playerList.stream().anyMatch(p -> p.getPlayerId() == player.getPlayerId())) return this.updatePlayer(player);
        player.setPlayerId(this.getPlayerCounter());
        this.playerList.add(player);
        return this.findPlayer(player.getPlayerId());
    }

    private Player updatePlayer(Player player) {
        this.deletePlayer(player);
        this.playerList.add(player);
        return this.findPlayer(player.getPlayerId());
    }

    public void deletePlayer(Player player) {
        this.playerList.removeIf(p -> p.getPlayerId() == player.getPlayerId());
    }

    public Player findPlayer(int playerId) {
        return this.playerList.stream().filter(p -> p.getPlayerId() == playerId).findFirst().get();
    }

    private int getPlayerCounter(){
        return this.playerCounter++;
    }

    /*private EntityManager em = PersistenceService.getEntityManager();

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
    }*/
}
