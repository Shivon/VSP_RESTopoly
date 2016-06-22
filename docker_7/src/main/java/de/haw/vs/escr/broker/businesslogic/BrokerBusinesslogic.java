package de.haw.vs.escr.broker.businesslogic;

import com.google.gson.Gson;
import com.jayway.restassured.response.Response;
import de.haw.vs.escr.broker.adapter.banks.dto.TransactionDTO;
import de.haw.vs.escr.broker.adapter.banks.rest.BankRestModel;
import de.haw.vs.escr.broker.adapter.board.dto.PlaceDTO;
import de.haw.vs.escr.broker.adapter.events.dto.EventDTO;
import de.haw.vs.escr.broker.adapter.games.dto.GameDTO;
import de.haw.vs.escr.broker.adapter.games.dto.PathDTO;
import de.haw.vs.escr.broker.adapter.games.dto.PlayerDTO;
import de.haw.vs.escr.broker.adapter.games.routes.GameRestModel;
import de.haw.vs.escr.broker.dto.PlaceConfirmationDTO;
import de.haw.vs.escr.broker.model.Broker;
import de.haw.vs.escr.broker.model.Place;
import de.haw.vs.escr.broker.repo.BrokerRepo;
import de.haw.vs.escr.broker.rest.RESTModel;
import de.haw.vs.escr.broker.util.yellowpages.IYellowPages;
import de.haw.vs.escr.broker.util.yellowpages.YellowPagesService;
import de.haw.vs.escr.broker.util.yellowpages.model.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
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
        logger.info("GET '" + place.getOwner() + "'");
        Response res = given().get(place.getOwner());
        logger.info("Response was: " + res.body().asString());
        return res.body().asString();
    }

    public List<String> findPlacesForBroker(int gameId) {
        Broker broker = this.findBroker(gameId);
        List<String> uris = new ArrayList<>();
        broker.getPlaces().stream().forEach(p -> uris.add(p.getId()));
        return uris;
    }

    public PlaceConfirmationDTO buyPlace(int gameId, Place place, String player, String resource) {
        if (place.getValue() != 0 && place.getOwner() != null) {
            //Get Bank Uri
            PathDTO pDto = this.getComponentDTO(gameId);

            //Get Player account
            PlayerDTO playerDto = this.getPlayer(player);

            //Start Bank Transaction
            TransactionDTO transDto = this.startNewBankTransaction(pDto.getBank());

            //Pay or dont
            BankRestModel brm = new BankRestModel(pDto.getBank());
            logger.info("POST '" + brm.getFromRoute(gameId, player, place.getValue()) + "'");
            Response res = given().queryParam("transaction", transDto.getId()).post(brm.getFromRoute(gameId, player, place.getValue()));
            logger.info("Response was: " + res.body().asString());

            EventDTO eDto = new EventDTO();
            eDto.setGame("/games/" + gameId);
            eDto.setType("buy");
            eDto.setName("Player " + playerDto.getUri() + " bought Place " + place.getId());
            eDto.setPlayer(playerDto.getUri());
            eDto.setReason("Player wanted to buy this place");
            eDto.setResource(resource);
            //Make event
            EventDTO event = this.postEvent(eDto, pDto.getEvents());

            PlaceConfirmationDTO pcdto = new PlaceConfirmationDTO();
            pcdto.setType(eDto.getType());
            pcdto.setDescription(event.getResource());

            //set Place
            place.setOwner(playerDto.getUri());

            return pcdto;
        }
        return null;
    }

    private EventDTO postEvent(EventDTO eDto, String eventPath) {
        logger.info("POST '" + eventPath + "': " + this.gson.toJson(eDto));
        Response res = given().body(this.gson.toJson(eDto)).post(eventPath);
        logger.info("Response was: " + res.body().asString());
        try {
            EventDTO e = this.gson.fromJson(res.body().asString(), EventDTO.class);
            return e;
        } catch (Exception e) {
            logger.info("Could not convert response to EventDTO");
        }
        return null;
    }

    private PlayerDTO getPlayer(String player) {
        GameRestModel gameRestModel = this.findGameService();
        logger.info("GET '" + gameRestModel.getGameAddress(player) + "'");
        Response res1 = given().get(gameRestModel.getGameAddress(player));
        logger.info("Response was: " + res1.body().asString());
        return this.gson.fromJson(res1.body().asString(), PlayerDTO.class);
    }

    private PathDTO getComponentDTO(int gameId) {
        GameRestModel gameRestModel = this.findGameService();
        logger.info("GET '" + gameRestModel.getGamePath(gameId) + "'");
        Response res1 = given().get(gameRestModel.getGamePath(gameId));
        logger.info("Response was: " + res1.body().asString());
        GameDTO gDto = this.gson.fromJson(res1.body().asString(), GameDTO.class);
        logger.info("GET '" + gameRestModel.getGameAddress(gDto.getComponents()) + "'");
        Response res2 = given().get(gameRestModel.getGameAddress(gDto.getComponents()));
        logger.info("Response was: " + res1.body().asString());
        PathDTO pathDto = this.gson.fromJson(res2.body().asString(), PathDTO.class);
        return pathDto;
    }

    private TransactionDTO startNewBankTransaction(String bank) {
        logger.info("POST '" + bank + "/transaction" + "'");
        Response res = given().post(bank + "/transaction");
        return this.gson.fromJson(res.body().asString(), TransactionDTO.class);
    }

    private GameRestModel findGameService() {
        YellowPagesService yp = new YellowPagesService();
        Service games = yp.findServiceByName("fancy_games");
        try {
            return new GameRestModel(new URL(games.getUri()).getAuthority().toString());
        } catch (Exception e) {
            System.out.println("Faild to init games IP");
        }
        return null;
    }

    public PlaceConfirmationDTO visitPlace(int gameId, Place place, String player, String resource) {
        //Get Bank Uri
        PathDTO pDto = this.getComponentDTO(gameId);

        //Get Player account
        PlayerDTO playerDto = this.getPlayer(player);

        //Move Event
        EventDTO mvEvent = this.postMoveEvent(gameId, place, playerDto, resource, pDto);
        
        if (this.hasOwner(place)) {
            if (place.getOwner() != player) {
                TransactionDTO transDto = this.startNewBankTransaction(pDto.getBank());

                //Transfer Money
                BankRestModel brm = new BankRestModel(pDto.getBank());
                logger.info("POST '" + brm.getFromToAmountRoute(gameId, place.getOwner(), player, place.getRent().get(0)) + "' (transaction: " + transDto.getId() + ")");
                Response res = given().queryParam("transaction", transDto.getId()).post(brm.getFromToAmountRoute(gameId, place.getOwner(), player, place.getRent().get(0)));
                logger.info("Response was: " + res.body().asString());

                EventDTO rentEvent = this.postRentEvent(gameId, place, playerDto, resource, pDto);

                PlaceConfirmationDTO pcdto = new PlaceConfirmationDTO();
                pcdto.setType("rent");
                pcdto.setDescription(resource);
                return pcdto;
            }
        }
        return null;
    }

    private boolean hasOwner(Place place) {
        return place.getOwner() != null;
    }

    private EventDTO postMoveEvent(int gameId, Place place, PlayerDTO playerDto, String resource, PathDTO pDto) {
        EventDTO eDto = new EventDTO();
        eDto.setGame("/games/" + gameId);
        eDto.setType("move");
        eDto.setName("Player " + playerDto.getUri() + " moved to Place " + place.getId());
        eDto.setPlayer(playerDto.getUri());
        eDto.setReason("Player had to move to this place");
        eDto.setResource(resource);
        //Make event
        EventDTO event = this.postEvent(eDto, pDto.getEvents());
        return event;
    }

    private EventDTO postRentEvent(int gameId, Place place, PlayerDTO playerDto, String resource, PathDTO pDto) {
        EventDTO eDto = new EventDTO();
        eDto.setGame("/games/" + gameId);
        eDto.setType("rent");
        eDto.setName("Player " + playerDto.getUri() + " payed rent on Place " + place.getId());
        eDto.setPlayer(playerDto.getUri());
        eDto.setReason("Player had to pay rent on this place");
        eDto.setResource(resource);
        //Make event
        EventDTO event = this.postEvent(eDto, pDto.getEvents());
        return event;
    }
}
