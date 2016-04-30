package de.haw.vs.escr.games.service;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.org.apache.xml.internal.security.algorithms.implementations.IntegrityHmac;
import de.haw.vs.escr.games.businesslogic.GameBusinessLogic;
import de.haw.vs.escr.games.models.Game;
import de.haw.vs.escr.games.repos.GameRepo;

/**
 * Created by Christian on 29.04.2016.
 */
public class GameService {
    private Gson gson;
    private GameRepo gameRepo;
    private GameBusinessLogic gameBL;

    public GameService() {
        this.initializeGson();
        this.gameRepo = new GameRepo();
        this.gameBL = new GameBusinessLogic(gameRepo);

        get("/games", (req, res) -> {
            return "GET /games";
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

            return gson.toJson(game);
            //return "GET /games/" + req.params(":gameid");
        });

        get("/games/:gameid/status", (req, res) -> {
            return "GET /games/" + req.params(":gameid") + "/status";
        });

        put("/games/:gameid/status", (req, res) -> {
            return "PUT /games/" + req.params(":gameid") + "/status";
        });

        get("/games/:gameid/services", (req, res) -> {
            return "GET /games/" + req.params(":gameid") + "/services";
        });

        put("/games/:gameid/services", (req, res) -> {
            return "PUT /games/" + req.params(":gameid") + "/services";
        });

        get("/games/:gameid/components", (req, res) -> {
            return "GET /games/" + req.params(":gameid") + "/components";
        });

        put("/games/:gameid/components", (req, res) -> {
            return "PUT /games/" + req.params(":gameid") + "/components";
        });

        get("/games/:gameid/players", (req, res) -> {
            return "GET /games/" + req.params(":gameid") + "/players";
        });

        post("/games/:gameid/players", (req, res) -> {
            return "POST /games/" + req.params(":gameid") + "/players";
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
