package client.view;

import client.adapter.BanksAdapter;
import client.adapter.BrokerAdapter;
import client.logic.BuyPlaceLogic;
import client.model.User;
import client.model.boardModels.Place;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import clientUI.BuyPlaceWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.activemq.broker.BrokerBroadcaster;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jana Mareike on 09.06.2016.
 */
public class BuyPlaceWindow {

    private BuyPlaceWindowUI _buyPlaceWindowUI;
    private BuyPlaceLogic _buyPlaceLogic;
    private Place _place;
    private BrokerAdapter _brokerAdapter;
    private Player _player;
    private BanksAdapter _banksAdapter;
    private Game _game;
    private User _user;

    public BuyPlaceWindow(Place place, Player player, User user, Game game){
        _place = place;
        _player = player;
        _user = user;
        _game = game;
        _banksAdapter = new BanksAdapter();

        _buyPlaceWindowUI = new BuyPlaceWindowUI();
        _buyPlaceWindowUI.getMainFrame().setVisible(true);
        _buyPlaceLogic = new BuyPlaceLogic(_buyPlaceWindowUI);

        showPlaceToBuy();
    }

    public void registerBuyPlaceButton(){
        _buyPlaceWindowUI.getPlaceToBuyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//      TODO regelt der Broker den Transfer mit der Bank? Woher kenne ich den Preis?
//                if(_banksAdapter.getAccount(_user, _game).getSaldo() >= Preis)
                try {
                    _brokerAdapter.buyPlace(_place, _player);
                } catch (UnirestException e1) {
                    e1.printStackTrace();
                }
                _buyPlaceLogic.closeBuyPlaceWindow();
            }
        });
    }

    public void registerDontBuyPlaceButton(){
        _buyPlaceWindowUI.getDontBuyPlaceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//      TODO
                _buyPlaceLogic.closeBuyPlaceWindow();
            }
        });
    }

    public void showPlaceToBuy(){

        _buyPlaceWindowUI.getPlaceToBuyArea().setText("You can buy: " + _place.getName());
    }
}
