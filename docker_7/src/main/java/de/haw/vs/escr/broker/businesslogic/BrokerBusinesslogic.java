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
        Broker savedBroker = this.brokerREPO.saveBroker(broker);
        savedBroker.setId("/brokers/" + savedBroker.getBrokerId());
        return this.brokerREPO.saveBroker(savedBroker);
    }
}
