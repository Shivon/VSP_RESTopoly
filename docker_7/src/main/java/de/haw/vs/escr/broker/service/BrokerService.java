package de.haw.vs.escr.broker.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.haw.vs.escr.broker.adapter.board.dto.PlaceDTO;
import de.haw.vs.escr.broker.adapter.games.routes.GameRestModel;
import de.haw.vs.escr.broker.businesslogic.BrokerBusinesslogic;
import de.haw.vs.escr.broker.dto.PlaceConfirmationDTO;
import de.haw.vs.escr.broker.dto.PlayerUriDTO;
import de.haw.vs.escr.broker.model.Broker;
import de.haw.vs.escr.broker.model.Place;
import de.haw.vs.escr.broker.repo.BrokerRepo;
import de.haw.vs.escr.broker.util.yellowpages.YellowPagesService;
import de.haw.vs.escr.broker.util.yellowpages.model.Service;

import java.net.URL;
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

            //TODO
            List<String> uris = this.brokerBL.findPlacesForBroker(gameId);

            return this.gson.toJson(uris);
        });

        post("/broker/:gameid/places", (req, res) -> {
            int gameId;
            try {
                gameId = Integer.parseInt(req.params(":gameid"));
            }
            catch (Exception e) {
                res.status(401);
                return null;
            }

            List<PlaceDTO> pdto = this.gson.fromJson(req.body(), List.class);

            return this.gson.toJson(pdto);
        });

        get("/broker/:gameid/places/:placeid", (req, res) -> {
            int gameId = this.getNumberFromString(req.params("gameid"));
            int placeid = this.getNumberFromString(req.params("placeid"));

            Place place = this.brokerBL.findPlace(gameId, placeid);

            return this.gson.toJson(place);
        });

        put("/broker/:gameid/places/:placeid", (req, res) -> {
            int gameId = this.getNumberFromString(req.params("gameid"));
            int placeid = this.getNumberFromString(req.params("placeid"));
            PlaceDTO placeDTO = this.gson.fromJson(req.body(), PlaceDTO.class);

            if (this.brokerBL.hasPlace(gameId, placeid)) {
                PlaceConfirmationDTO placeConfDTO = new PlaceConfirmationDTO();
                placeConfDTO.setType("Exists");
                placeConfDTO.setDescription(this.brokerBL.findBroker(gameId).getPlaces().stream().filter(b -> b.getPlaceId() == placeid).findFirst().get().getId());
                res.status(200);
                res.type("application/json");
                return this.gson.toJson(placeConfDTO);
            }

            PlaceConfirmationDTO pcdto = this.brokerBL.createAndPutPlace(gameId, placeid, placeDTO);

            res.status(201);
            res.type("application/json");
            return this.gson.toJson(pcdto);
        });

        get("/broker/:gameid/places/:placeid/owner", (req, res) -> {
            int gameId = this.getNumberFromString(req.params("gameid"));
            int placeid = this.getNumberFromString(req.params("placeid"));

            Place place = this.brokerBL.findPlace(gameId, placeid);
            if (place.getOwner() == null) {
                res.status(401);
                return null;
            }

            String owner = this.brokerBL.findOwner(place);

            return owner;
        });

        put("/broker/:gameid/places/:placeid/owner", (req, res) -> {
            return null;
        });

        post("/broker/:gameid/places/:placeid/owner", (req, res) -> {
            String resource = "/broker/" + req.params("gameid") + "/places/" + req.params("placeid");
            int gameId = this.getNumberFromString(req.params("gameid"));
            int placeid = this.getNumberFromString(req.params("placeid"));
            PlayerUriDTO pu = this.gson.fromJson(req.body(), PlayerUriDTO.class);

            Place place = this.brokerBL.findPlace(gameId, placeid);
            if (place.getOwner() != null) {
                PlaceConfirmationDTO pcdto1 = new PlaceConfirmationDTO();
                pcdto1.setType("Place already sold or not buyable");
                pcdto1.setDescription(pu.getPlayer());
                res.status(409);
                return this.brokerBL.findOwner(place);
            }

            PlaceConfirmationDTO pcdto2 = this.brokerBL.buyPlace(gameId, place, pu.getPlayer(), resource);

            if (pcdto2 == null) {
                res.status(401);
                return null;
            }

            return this.gson.toJson(pcdto2);
        });

        put("/broker/:gameid/places/:placeid/hypothecarycredit", (req, res) -> {
            return null;
        });

        post("/broker/:gameid/places/:placeid/hypothecarycredit", (req, res) -> {
            return null;
        });

        post("/broker/:gameid/places/:placeid/visit", (req, res) -> {
            String resource = "/broker/" + req.params("gameid") + "/places/" + req.params("placeid");
            int gameId = this.getNumberFromString(req.params("gameid"));
            int placeid = this.getNumberFromString(req.params("placeid"));
            PlayerUriDTO pu = this.gson.fromJson(req.body(), PlayerUriDTO.class);

            Place place = this.brokerBL.findPlace(gameId, placeid);

            PlaceConfirmationDTO pcdto = this.brokerBL.visitPlace(gameId, place, pu.getPlayer(), resource);

            if (pcdto == null) {
                res.status(409);
                return null;
            }

            return this.gson.toJson(pcdto);
        });
    }

    private void initializeGson() {
        GsonBuilder gb = new GsonBuilder();
        gb.excludeFieldsWithoutExposeAnnotation();
        this.gson = gb.create();
    }

    private int getNumberFromString(String number) {
        int val;
        try {
            val = Integer.parseInt(number);
        }
        catch (Exception e) {
            return 0;
        }
        return val;
    }
}
