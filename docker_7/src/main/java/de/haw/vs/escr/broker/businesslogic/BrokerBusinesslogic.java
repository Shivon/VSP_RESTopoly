package de.haw.vs.escr.broker.businesslogic;

import com.google.gson.Gson;
import com.jayway.restassured.response.Response;
import de.haw.vs.escr.broker.adapter.banks.dto.TransactionDTO;
import de.haw.vs.escr.broker.adapter.board.dto.PlaceDTO;
import de.haw.vs.escr.broker.dto.PlaceConfirmationDTO;
import de.haw.vs.escr.broker.model.Broker;
import de.haw.vs.escr.broker.model.Place;
import de.haw.vs.escr.broker.repo.BrokerRepo;
import de.haw.vs.escr.broker.rest.RESTModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by Christian on 15.06.2016.
 */
public class BrokerBusinesslogic {
    private static final Logger logger = LoggerFactory.getLogger(BrokerBusinesslogic.class);
    private final Gson gson;
    private final BrokerRepo brokerREPO;

    public BrokerBusinesslogic(BrokerRepo brokerREPO, Gson gson) {
        this.gson = gson;
        this.brokerREPO = brokerREPO;
    }

    public List<String> getUriList() {
        List<Broker> brokers = this.brokerREPO.getAll();
        List<String> brokerUris = new ArrayList<>();
        for (Broker b : brokers) {
            brokerUris.add(b.getId());
        }
        return brokerUris;
    }

    public Broker createBroker(Broker broker) {
        RESTModel restModel = new RESTModel();
        logger.info("Save broker:");
        String gameStr = broker.getGame().substring(broker.getGame().lastIndexOf('/'));
        int gameid = Integer.parseInt(gameStr.substring(1));
        logger.info("gameid = " + gameid);
        broker.setId(restModel.getBrokerRoute(gameid));
        broker.setGameId(gameid);
        broker.setEstates(restModel.getEstatesRoute(gameid));
        Broker res = this.brokerREPO.saveBroker(broker);
        logger.info("Saved Broker: " + this.gson.toJson(res));
        return res;
    }

    public Broker findBroker(int gameId) {
        logger.info("Find broker with gameid: " + gameId);
        Broker res = this.brokerREPO.findBrokerById(gameId);
        logger.info("Found broker: " + this.gson.toJson(res));
        return res;
    }

    public Broker updateBroker(int gameId, Broker brokerToUpdate) {
        logger.info("Update broker '" + gameId + "': " + brokerToUpdate);
        Broker broker = this.findBroker(gameId);
        if (brokerToUpdate.getGame() != null) broker.setGame(brokerToUpdate.getGame());
        if (brokerToUpdate.getEstates() != null) broker.setEstates(brokerToUpdate.getEstates());
        Broker updatedBroker = this.brokerREPO.saveBroker(broker);
        logger.info("Updated Broker: " + this.gson.toJson(updatedBroker));
        return updatedBroker;
    }

    public PlaceConfirmationDTO createAndPutPlace(int gameId, int placeid, PlaceDTO placeDTO) {
        RESTModel brokerRestModel = new RESTModel();
        Place place = new Place();
        place.setValue(placeDTO.getValue());
        if (placeDTO.getPlace() != null) place.setPlace(placeDTO.getPlace());
        if (placeDTO.getCost() != null) place.setCost(placeDTO.getCost());
        if (placeDTO.getRent() != null) place.setRent(placeDTO.getRent());
        place.setHouses(0);
        place.setVisit(brokerRestModel.getVisitRoute(gameId, placeid));
        place.setHypocredit(brokerRestModel.getHypoRoute(gameId, placeid));
        place.setOwner(brokerRestModel.getOwnerRoute(gameId, placeid));
        place.setId(brokerRestModel.getPlaceRoute(gameId, placeid));
        place.setPlaceId(placeid);

        Broker broker = this.findBroker(gameId);
        broker.addPlace(place);

        Broker savedBroker = this.brokerREPO.saveBroker(broker);

        PlaceConfirmationDTO pcdto = new PlaceConfirmationDTO();
        pcdto.setType("Initial Place");
        pcdto.setDescription(savedBroker.getPlaces().stream().filter(b -> b.getPlaceId() == placeid).findFirst().get().getId());

        return pcdto;
    }

    public boolean hasPlace(int gameId, int placeid) {
        Broker broker = this.findBroker(gameId);
        return broker.getPlaces().stream().anyMatch(p -> p.getPlaceId() == placeid);
    }

    public Place findPlace(int gameId, int placeid) {
        Broker broker = this.findBroker(gameId);
        return broker.getPlaces().stream().filter(p -> p.getPlaceId() == placeid).findFirst().get();
    }

    public String findOwner(Place place) {
        Response res = given().get(place.getOwner());
        return res.body().asString();
    }

    public List<String> findPlacesForBroker(int gameId) {
        Broker broker = this.findBroker(gameId);
        List<String> uris = new ArrayList<>();
        broker.getPlaces().stream().forEach(p -> uris.add(p.getId()));
        return uris;
    }

    public PlaceConfirmationDTO buyPlace(int gameId, Place place, String player) {
        //Start Bank Transaction

        //IF has enough money


        return null;
    }

    private TransactionDTO startNewBankTransaction() {
        Response res = given().get();
    }
}
