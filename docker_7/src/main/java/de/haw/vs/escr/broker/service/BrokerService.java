package de.haw.vs.escr.broker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static spark.Spark.*;

/**
 * Created by Christian on 13.06.2016.
 */
public class BrokerService {
    private Gson gson;

    public BrokerService() {
        this.initializeGson();

        get("/broker", (req, res) -> {
            return "JO";
        });

        post("/broker", (req, res) -> {
            return null;
        });

        get("/broker/:gameid", (req, res) -> {
            return null;
        });

        put("/broker/:gameid", (req, res) -> {
            return null;
        });

        get("/broker/:gameid/places", (req, res) -> {
            return null;
        });

        get("/broker/:gameid/places/:placeid", (req, res) -> {
            return null;
        });

        put("/broker/:gameid/places/:placeid", (req, res) -> {
            return null;
        });

        get("/broker/:gameid/places/:placeid/owner", (req, res) -> {
            return null;
        });

        put("/broker/:gameid/places/:placeid/owner", (req, res) -> {
            return null;
        });

        post("/broker/:gameid/places/:placeid/owner", (req, res) -> {
            return null;
        });

        put("/broker/:gameid/places/:placeid/hypothecarycredit", (req, res) -> {
            return null;
        });

        post("/broker/:gameid/places/:placeid/hypothecarycredit", (req, res) -> {
            return null;
        });

        post("/broker/:gameid/places/:placeid/visit", (req, res) -> {
            return null;
        });
    }

    private void initializeGson() {
        GsonBuilder gb = new GsonBuilder();
        gb.excludeFieldsWithoutExposeAnnotation();
        this.gson = gb.create();
    }
}
