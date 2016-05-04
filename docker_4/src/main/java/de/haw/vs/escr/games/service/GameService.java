package de.haw.vs.escr.games.service;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.xml.internal.security.algorithms.implementations.IntegrityHmac;
import de.haw.vs.escr.games.businesslogic.GameBusinessLogic;
import de.haw.vs.escr.games.dtos.GameDTO;
import de.haw.vs.escr.games.dtos.PlayerURI;
import de.haw.vs.escr.games.dtos.StatusDTO;
import de.haw.vs.escr.games.models.Game;
import de.haw.vs.escr.games.models.Paths;
import de.haw.vs.escr.games.models.Player;
import de.haw.vs.escr.games.repos.GameRepo;
import de.haw.vs.escr.games.repos.PlayerRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 29.04.2016.
 */
public class GameService {
    private Gson gson;
    private GameRepo gameRepo;
    private PlayerRepo playerRepo;
    private GameBusinessLogic gameBL;

    public GameService() {
        this.initializeGson();
        this.gameRepo = new GameRepo();
        this.playerRepo = new PlayerRepo();
        this.gameBL = new GameBusinessLogic(gameRepo, playerRepo);

        get("/games", (req, res) -> {
            List<Game> games = this.gameBL.findAllGames();
            List<GameDTO> gDTO = new ArrayList<GameDTO>();
            for (Game g : games) {
                gDTO.add(g.toDTO());
            }
            return gson.toJson(gDTO);
            //return "GET /games";
        });

        post("/games", (req, res) -> {
            Game game = gson.fromJson(req.body(), Game.class);
            Game g = this.gameBL.createGame(game);
            return gson.toJson(g);
            //return "POST /games";
        });

        get("/games/:gameid", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            Game game = this.gameBL.findGame(gameId);
            GameDTO gDTO = game.toDTO();

            return gson.toJson(gDTO);
            //return "GET /games/" + req.params(":gameid");
        });

        get("/games/:gameid/status", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            StatusDTO status = this.gameBL.findStatusForGameId(gameId);

            return gson.toJson(status);
            //return "GET /games/" + req.params(":gameid") + "/status";
        });

        put("/games/:gameid/status", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            StatusDTO reqStatus = gson.fromJson(req.body(), StatusDTO.class);

            if (reqStatus.getStatus() == null) {
                res.status(401);
                return null;
            }

            StatusDTO status = this.gameBL.updateStatusForGameId(gameId, reqStatus);

            return gson.toJson(status);
            //return "PUT /games/" + req.params(":gameid") + "/status";
        });

        get("/games/:gameid/services", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            Game game = this.gameBL.findGame(gameId);
            Paths services = game.getServices();

            return gson.toJson(services);
            //return "GET /games/" + req.params(":gameid") + "/services";
        });

        put("/games/:gameid/services", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            Paths reqServices = gson.fromJson(req.body(), Paths.class);
            if (reqServices == null) {
                res.status(401);
                return null;
            }

            Paths services = this.gameBL.updateServices(gameId, reqServices);

            return gson.toJson(services);
            //return "PUT /games/" + req.params(":gameid") + "/services";
        });

        get("/games/:gameid/components", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            Game game = this.gameBL.findGame(gameId);
            Paths components = game.getComponents();

            return gson.toJson(components);
            //return "GET /games/" + req.params(":gameid") + "/components";
        });

        put("/games/:gameid/components", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            Paths reqComponents = gson.fromJson(req.body(), Paths.class);
            if (reqComponents == null) {
                res.status(401);
                return null;
            }

            Paths components = this.gameBL.updateComponents(gameId, reqComponents);

            return gson.toJson(components);
            //return "PUT /games/" + req.params(":gameid") + "/components";
        });

        get("/games/:gameid/players", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            Game g = this.gameBL.findGame(gameId);
            PlayerURI pu = g.getPlayerURIs();

            return gson.toJson(pu);
            //return "GET /games/" + req.params(":gameid") + "/players";
        });

        post("/games/:gameid/players", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            Player reqPlayer = gson.fromJson(req.body(), Player.class);
            Player player = this.gameBL.createPlayerAndAddToGame(gameId, reqPlayer);

            return gson.toJson(player);
            //return "POST /games/" + req.params(":gameid") + "/players";
        });

        get("/games/:gameid/players/:playerid", (req, res) -> {
            return "GET /games/" + req.params(":gameid") + "/players/" + req.params(":playerid");
        });

        put("/games/:gameid/players/:playerid", (req, res) -> {
            return "PUT /games/" + req.params(":gameid") + "/players/" + req.params(":playerid");
        });

        delete("/games/:gameid/players/:playerid", (req, res) -> {
            return "DELETE /games/" + req.params(":gameid") + "/players/" + req.params(":playerid");
        });

        get("/games/:gameid/players/:playerid/ready", (req, res) -> {
            return "GET /games/" + req.params(":gameid") + "/players/" + req.params(":playerid") + "/ready";
        });

        put("/games/:gameid/players/:playerid/ready", (req, res) -> {
            return "PUT /games/" + req.params(":gameid") + "/players/" + req.params(":playerid") + "/ready";
        });

        get("/games/:gameid/players/current", (req, res) -> {
            return "GET /games/" + req.params(":gameid") + "/players/current";
        });

        get("/games/:gameid/players/turn", (req, res) -> {
            return "GET /games/" + req.params(":gameid") + "/players/turn";
        });

        put("/games/:gameid/players/turn", (req, res) -> {
            return "PUT /games/" + req.params(":gameid") + "/players/turn";
        });

        delete("/games/:gameid/players/turn", (req, res) -> {
            return "DELETE /games/" + req.params(":gameid") + "/players/turn";
        });
    }

    private void initializeGson() {
        GsonBuilder gb = new GsonBuilder();
        gb.excludeFieldsWithoutExposeAnnotation();
        this.gson = gb.create();
    }
}
