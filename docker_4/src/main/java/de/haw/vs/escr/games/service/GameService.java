package de.haw.vs.escr.games.service;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Christian on 29.04.2016.
 */
public class GameService {
    private Gson gson;

    public GameService() {
        this.initializeGson();

        get("/games", (req, res) -> {
            return "GET /games";
        });

        post("/games", (req, res) -> {
            return "POST /games";
        });

        get("/games/:gameid", (req, res) -> {
            return "GET /games/" + req.params(":gameid");
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
