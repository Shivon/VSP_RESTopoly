package de.haw.vs.escr.games.businesslogic;

import de.haw.vs.escr.games.dtos.GameDTO;
import de.haw.vs.escr.games.dtos.StatusDTO;
import de.haw.vs.escr.games.models.Game;
import de.haw.vs.escr.games.models.Paths;
import de.haw.vs.escr.games.repos.GameRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 30.04.2016.
 */
public class GameBusinessLogic {
    private GameRepo gameRepo = null;

    public GameBusinessLogic(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
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
}
