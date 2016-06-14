package de.haw.vs.escr.games.service;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.haw.vs.escr.games.businesslogic.GameBusinessLogic;
import de.haw.vs.escr.games.dtos.GameDTO;
import de.haw.vs.escr.games.dtos.PlayerDetailDTO;
import de.haw.vs.escr.games.dtos.PlayerURI;
import de.haw.vs.escr.games.dtos.StatusDTO;
import de.haw.vs.escr.games.models.Game;
import de.haw.vs.escr.games.models.Paths;
import de.haw.vs.escr.games.models.Player;
import de.haw.vs.escr.games.models.Ready;
import de.haw.vs.escr.games.repos.GameRepo;
import de.haw.vs.escr.games.repos.PlayerRepo;
import spark.Request;
import spark.Response;

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

            Game g = this.gameBL.initializeAndCreateGame(game);

            //Game g = this.gameBL.createGame(game);
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
            this.gameBL.checkGameStatus(gameId);

            return gson.toJson(player);
            //return "POST /games/" + req.params(":gameid") + "/players";
        });

        get("/games/:gameid/players/:playerid", (req, res) -> {
            if (req.params("playerid").equals("turn")) return this.getTurn(req, res);

            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            String playerId = req.params(":playerid");

            Game g = this.gameBL.findGame(gameId);

            Player p = null;
            for (Player pf : g.getPlayers()) {
                if (pf.getUser().equals(playerId)) p = pf;
            }

            if (p == null) {
                res.status(401);
                return null;
            }

            return gson.toJson(p);
            //return "GET /games/" + req.params(":gameid") + "/players/" + req.params(":playerid");
        });

        put("/games/:gameid/players/:playerid", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            String playerId = req.params(":playerid");

            Game g = this.gameBL.findGame(gameId);
            Player p = null;
            for (Player pf : g.getPlayers()) {
                if (pf.getUser().equals(playerId)) p = pf;
            }

            if (p == null) {
                res.status(401);
                return null;
            }

            Player playerToUpdate = this.gson.fromJson(req.body(), Player.class);

            Player updatedPlayer = this.gameBL.compareAndUpdatePlayer(p, playerToUpdate);

            return this.gson.toJson(updatedPlayer);
            //return "PUT /games/" + req.params(":gameid") + "/players/" + req.params(":playerid");
        });

        delete("/games/:gameid/players/:playerid", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            String playerId = req.params(":playerid");

            Game g = this.gameBL.findGame(gameId);
            Player p = null;
            for (Player pf : g.getPlayers()) {
                if (pf.getUser().equals(playerId)) p = pf;
            }

            if (p == null) {
                res.status(401);
                return null;
            }

            this.gameBL.deletePlayer(p);

            return this.gson.toJson("Successful");
            //return "DELETE /games/" + req.params(":gameid") + "/players/" + req.params(":playerid");
        });

        get("/games/:gameid/players/:playerid/ready", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            String playerId = req.params(":playerid");

            Game g = this.gameBL.findGame(gameId);
            Player p = null;
            for (Player pf : g.getPlayers()) {
                if (pf.getUser().equals(playerId)) p = pf;
            }

            if (p == null) {
                res.status(401);
                return null;
            }

            return this.gson.toJson(p.getReady());
            //return "GET /games/" + req.params(":gameid") + "/players/" + req.params(":playerid") + "/ready";
        });

        put("/games/:gameid/players/:playerid/ready", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            String playerId = req.params(":playerid");

            Game g = this.gameBL.findGame(gameId);
            Player p = null;
            for (Player pf : g.getPlayers()) {
                if (pf.getUser().equals(playerId)) p = pf;
            }

            if (p == null) {
                res.status(401);
                return null;
            }

            Ready r = this.gson.fromJson(req.body(), Ready.class);

            Ready updatedReady = this.gameBL.updateReady(p, r);

            return this.gson.toJson(updatedReady);
            //return "PUT /games/" + req.params(":gameid") + "/players/" + req.params(":playerid") + "/ready";
        });

        get("/games/:gameid/players/current", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            Game g = this.gameBL.findGame(gameId);

            Player p = this.gameBL.findPlayerHoldingTurn(g.getPlayers());

            if (p == null) {
                res.status(401);
                return null;
            }

            return this.gson.toJson(p);
            //return "GET /games/" + req.params(":gameid") + "/players/current";
        });

        put("/games/:gameid/players/turn", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            java.lang.String uriB = this.gson.fromJson(req.body(), java.lang.String.class);
            java.lang.String uriQ = req.queryParams("id");

            if (uriQ == null && uriB == null) {
                res.status(404);
                return null;
            }

            java.lang.String uri = "";
            if (uriQ != null) uri = uriQ;
            else uri = uriB;

            Game g = this.gameBL.findGame(gameId);

            PlayerDetailDTO pd = this.gameBL.tryAchieveTurn(g.getPlayers(), uri);

            if (pd == null) {
                res.status(401);
                return null;
            }

            if (pd.isHasAlreadyTurn() && pd.isHasTurnNow()) {
                res.status(200);
            }

            if (pd.isHasTurnNow() && !pd.isHasAlreadyTurn()) {
                res.status(201);
            }

            if (!pd.isHasAlreadyTurn() && !pd.isHasTurnNow()) {
                res.status(409);
            }

            return this.gson.toJson(pd.getPlayer());
            //return "PUT /games/" + req.params(":gameid") + "/players/turn";
        });

        delete("/games/:gameid/players/turn", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (NumberFormatException e) {
                res.status(401);
                return null;
            }

            Game g = this.gameBL.findGame(gameId);

            this.gameBL.releaseTurn(g);

            return "Successful";
            //return "DELETE /games/" + req.params(":gameid") + "/players/turn";
        });
    }

    private java.lang.String getTurn(Request req, Response res) {
        int gameId;
        try {
            gameId = Integer.parseInt(req.params(":gameid"));
        }
        catch (NumberFormatException e) {
            res.status(401);
            return null;
        }

        Game g = this.gameBL.findGame(gameId);

        Player p = this.gameBL.findPlayerHoldingTurn(g.getPlayers());

        if (p == null) {
            res.status(404);
            return null;
        }

        return this.gson.toJson(p);
    }

    private void initializeGson() {
        GsonBuilder gb = new GsonBuilder();
        gb.excludeFieldsWithoutExposeAnnotation();
        this.gson = gb.create();
    }
}
