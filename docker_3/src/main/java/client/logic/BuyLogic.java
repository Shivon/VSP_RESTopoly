package client.logic;

import client.adapter.BrokerAdapter;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 21.06.2016.
 */
public class BuyLogic {

    private BrokerAdapter _brokerAdapter;
    private TurnToRollLogic _turnToRollLogic;

    public BuyLogic(BrokerAdapter brokerAdapter, TurnToRollLogic turnToRollLogic){
        _brokerAdapter = brokerAdapter;
        _turnToRollLogic = turnToRollLogic;
    }

    public boolean checkPlaceIsBuyable() throws UnirestException {
        if(_brokerAdapter.getOwner(_turnToRollLogic.getCurrentField()) == null){
            return true;
        }
        return false;
    }

    public String getPrice() throws UnirestException {
       return  _brokerAdapter.getPriceOfPlace(_turnToRollLogic.getCurrentField());
    }

    public String getRent() throws UnirestException {
        return  _brokerAdapter.getRent(_turnToRollLogic.getCurrentField());
    }

}
