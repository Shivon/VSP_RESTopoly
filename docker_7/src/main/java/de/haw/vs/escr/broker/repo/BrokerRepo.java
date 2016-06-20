package de.haw.vs.escr.broker.repo;

import de.haw.vs.escr.broker.model.Broker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 13.06.2016.
 */
public class BrokerRepo {
    private List<Broker> brokerList = new ArrayList<>();
    private int brokerCounter = 1;

    public BrokerRepo() {
    }

    private int getBrokerCounter() {
        return brokerCounter++;
    }

    public Broker findBrokerById(int gameId) {
        return this.brokerList.stream().filter(b -> b.getGameId() == gameId).findFirst().get();
    }

    public List<Broker> getAll() {
        return this.brokerList;
    }

    public Broker saveBroker(Broker broker) {
        if (this.brokerList.stream().anyMatch(b -> broker.getGameId() == b.getGameId())) return this.updateBroker(broker);
        //broker.setBrokerId(this.getBrokerCounter());
        this.brokerList.add(broker);
        return this.findBrokerById(broker.getGameId());
    }

    public Broker updateBroker(Broker broker) {
        this.deleteBroker(broker);
        this.brokerList.add(broker);
        return this.findBrokerById(broker.getGameId());
    }

    public void deleteBroker(Broker broker) {
        this.brokerList.removeIf(b -> b.getGameId() == broker.getGameId());
    }
}
