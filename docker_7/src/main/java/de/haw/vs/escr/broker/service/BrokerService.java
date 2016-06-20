package de.haw.vs.escr.broker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.haw.vs.escr.broker.businesslogic.BrokerBusinesslogic;
import de.haw.vs.escr.broker.model.Broker;
import de.haw.vs.escr.broker.repo.BrokerRepo;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by Christian on 13.06.2016.
 */
public class BrokerService {
    private Gson gson;
    private final BrokerRepo brokerREPO;
    private final BrokerBusinesslogic brokerBL;

    public BrokerService() {
        this.initializeGson();
        this.brokerREPO = new BrokerRepo();
        this.brokerBL = new BrokerBusinesslogic(this.brokerREPO, this.gson);

        get("/broker", (req, res) -> {
            List<String> uris = this.brokerBL.getUriList();
            return this.gson.toJson(uris);
        });

        post("/broker", (req, res) -> {
            Broker broker = this.gson.fromJson(req.body(), Broker.class);
            Broker savedBroker = this.brokerBL.createBroker(broker);
            return this.gson.toJson(savedBroker);
        });

        get("/broker/:gameid", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (Exception e) {
                res.status(401);
                return null;
            }

            Broker broker = this.brokerBL.findBroker(gameId);

            return this.gson.toJson(broker);
        });

        put("/broker/:gameid", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (Exception e) {
                res.status(401);
                return null;
            }

            Broker brokerToUpdate = this.gson.fromJson(req.body(), Broker.class);
            Broker broker = this.brokerBL.updateBroker(gameId, brokerToUpdate);

            return broker;
        });

        get("/broker/:gameid/places", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (Exception e) {
                res.status(401);
                return null;
            }



            return null;
        });

        post("/borker/:gameid/places", (req, res) -> {
            //
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
