package de.haw.vs.escr.broker.businesslogic;

import com.google.gson.Gson;
import de.haw.vs.escr.broker.model.Broker;
import de.haw.vs.escr.broker.repo.BrokerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
        logger.info("Save broker:");
        int gameid = Integer.parseInt(broker.getGame().substring(broker.getGame().lastIndexOf('/')));
        logger.info("gameid = " + gameid);
        broker.setId("/brokers/" + gameid);
        broker.setGameId(gameid);
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
}
