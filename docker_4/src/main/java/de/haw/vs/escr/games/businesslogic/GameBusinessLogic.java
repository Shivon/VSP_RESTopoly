package de.haw.vs.escr.games.businesslogic;

import de.haw.vs.escr.games.models.Game;
import de.haw.vs.escr.games.repos.GameRepo;

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
        return g2;
    }

    public Game findGame(int gameId) {
        Game g = this.gameRepo.findGame(gameId);
        return g;
    }
}
