package de.haw.vs.escr.games.businesslogic;

import de.haw.vs.escr.games.dtos.PlayerDetailDTO;
import de.haw.vs.escr.games.dtos.StatusDTO;
import de.haw.vs.escr.games.models.Game;
import de.haw.vs.escr.games.models.Paths;
import de.haw.vs.escr.games.models.Player;
import de.haw.vs.escr.games.models.Ready;
import de.haw.vs.escr.games.repos.GameRepo;
import de.haw.vs.escr.games.repos.PlayerRepo;

import java.util.List;

/**
 * Created by Christian on 30.04.2016.
 */
public class GameBusinessLogic {
    private GameRepo gameRepo = null;
    private PlayerRepo playerRepo = null;

    public GameBusinessLogic(GameRepo gameRepo, PlayerRepo playerRepo) {
        this.gameRepo = gameRepo;
        this.playerRepo = playerRepo;
    }

    public Game createGame(Game game) {
        Game g1 = this.gameRepo.saveGame(game);
        g1.setUri("/games/" + g1.getGameId());
        Game g2 = this.gameRepo.saveGame(g1);
        //Component Links missing!!!
        return g2;
    }

    public Game findGame(int gameId) {
        Game g = this.gameRepo.findGame(gameId);
        return g;
    }

    public Game updateGame(Game game) {
        if (game.getGameId() < 0) return null;
        Game g = this.gameRepo.saveGame(game);
        return g;
    }

    public List<Game> findAllGames() {
        List<Game> games = this.gameRepo.findAllGames();
        return games;
    }

    public Paths updateServices(int gameId, Paths services) {
        Game g = this.gameRepo.findGame(gameId);
        g.setServices(services);
        Game g2 = this.gameRepo.saveGame(g);
        return g2.getServices();
    }

    public Paths updateComponents(int gameId, Paths components) {
        Game g = this.gameRepo.findGame(gameId);
        g.setComponents(components);
        Game g2 = this.gameRepo.saveGame(g);
        return g2.getComponents();
    }

    public StatusDTO findStatusForGameId(int gameId) {
        Game g = this.findGame(gameId);
        return g.toStatus();
    }

    public StatusDTO updateStatusForGameId(int gameId, StatusDTO statusDTO) {
        Game g = this.findGame(gameId);
        g.setStatus(statusDTO.getStatus());
        Game updatedGame = this.updateGame(g);
        return updatedGame.toStatus();
    }

    public Player createPlayer(int gameId, Player player) {
        Player savedPlayer = this.playerRepo.savePlayer(player);
        savedPlayer.setUri("/games/" + gameId + "/players/" + savedPlayer.getPlayerId());
        Player updatedPlayer = this.playerRepo.savePlayer(savedPlayer);
        return updatedPlayer;
    }

    public Player createPlayerAndAddToGame(int gameId, Player player) {
        Game g = this.findGame(gameId);
        Player savedPlayer = this.createPlayer(gameId, player);
        g.addPlayer(savedPlayer);
        this.updateGame(g);
        return savedPlayer;
    }

    public Player updatePlayer(Player p) {
        return this.playerRepo.savePlayer(p);
    }

    public Player compareAndUpdatePlayer(Player old, Player newP) {
        if (newP.getUri() != null) old.setUri(newP.getUri());
        if (newP.getUser() != null) old.setUser(newP.getUser());
        if (newP.getPawn() != null) old.setPawn(newP.getPawn());
        if (newP.getAccount() != null) old.setAccount(newP.getAccount());
        if (newP.getReady() != null) old.setReady(newP.getReady());
        return this.updatePlayer(old);
    }

    public void deletePlayer(int playerId) {
        Player p = this.findPlayer(playerId);
        this.deletePlayer(p);
    }

    public void deletePlayer(Player p) {
        this.playerRepo.deletePlayer(p);
    }

    private Player findPlayer(int playerId) {
        return this.playerRepo.findPlayer(playerId);
    }

    public Ready updateReady(Player p, Ready r) {
        if (r == null) return p.getReady();
        p.setReady(r);
        Player updatedPlayer = this.updatePlayer(p);
        return updatedPlayer.getReady();
    }

    public Player findPlayerHoldingTurn(List<Player> players) {
        for (Player p : players) {
            if (p.isTurn()) return p;
        }
        return null;
    }

    public PlayerDetailDTO tryAchieveTurn(List<Player> players, String uri) {
        Player holding = this.findPlayerHoldingTurn(players);
        if (holding != null && holding.getUri() == uri) {
            for (Player p : players) {
                if (p.getUri() == uri) {
                    PlayerDetailDTO pd = new PlayerDetailDTO(p, true, true);
                    pd.getPlayer().setTurn(true);
                    pd.setPlayer(this.updatePlayer(pd.getPlayer()));
                    return pd;
                }
            }
        }
        if (holding == null) {
            for (Player p : players) {
                if (p.getUri() == uri) {
                    PlayerDetailDTO pd = new PlayerDetailDTO(p, false, true);
                    pd.getPlayer().setTurn(true);
                    pd.setPlayer(this.updatePlayer(pd.getPlayer()));
                    return pd;
                }
            }
        }
        for (Player p : players) {
            if (p.getUri() == uri) {
                PlayerDetailDTO pd = new PlayerDetailDTO(p, false, false);
                pd.getPlayer().setTurn(false);
                pd.setPlayer(this.updatePlayer(pd.getPlayer()));
                return pd;
            }
        }
        return null;
    }

    public void releaseTurn(Game g) {
        for (Player p : g.getPlayers()) {
            p.setTurn(false);
            this.updatePlayer(p);
        }
    }
}
