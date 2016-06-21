package client.view;

import client.adapter.BanksAdapter;
import client.adapter.BrokerAdapter;

import client.adapter.PlayerAdapter;
import client.logic.BuyLogic;
import client.logic.GamesLogic;
import client.logic.UserLogic;
import client.logic.WaitLogic;
import client.model.User;
import client.model.boardModels.Place;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import clientUI.BuyPlaceWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerBroadcaster;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jana Mareike on 09.06.2016.
 */
public class BuyPlaceWindow {

    private BuyPlaceWindowUI _buyPlaceWindowUI;
    private BrokerAdapter _brokerAdapter;
    private WaitLogic _waitLogic;
    private WaitWindow _waitWindow;
    private GamesLogic _gamesLogic;
    private UserLogic _userLogic;
    private BuyLogic _buyLogic;
    private PlayerAdapter _playerAdapter;

    public BuyPlaceWindow(BuyPlaceWindowUI buyPlaceWindowUI,
                          WaitWindow waitWindow, WaitLogic waitLogic, GamesLogic gamesLogic, UserLogic userLogic
                            , BrokerAdapter brokerAdapter, BuyLogic buyLogic, PlayerAdapter playerAdapter){

        _waitLogic = waitLogic;
        _buyPlaceWindowUI = buyPlaceWindowUI;
        _waitWindow = waitWindow;
        _gamesLogic = gamesLogic;
        _userLogic = userLogic;
        _brokerAdapter = brokerAdapter;
        _buyLogic = buyLogic;
        _playerAdapter = playerAdapter;

        registerBuyPlaceButton();
        registerDontBuyPlaceButton();
    }

    public void registerBuyPlaceButton(){
        _buyPlaceWindowUI.getPlaceToBuyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//      TODO
//                if((_banksAdapter.getAccount(_userLogic.getCurrentUser(), _gameLogic.getCurrentGame()).getSaldo()
//                      -_buyLogic.getCurrentPlace().getCost) < 0){
//                _waitWindow.getWaitWindowUI().getWaitText().
//                setText("You have not enough money!");
                _buyPlaceWindowUI.getMainFrame().setVisible(false);
//            }else{
                try {
//                    _brokerAdapter.buyPlace(_buyLogic.getCurrentPlace(),
//                                  _playerAdapter.getPlayer(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser()));
                    _waitWindow.getWaitWindowUI().getSaldoTextArea().
                            setText("" + _waitLogic.getSaldo(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser()) );
                } catch (UnirestException e1) {
                    e1.printStackTrace();
                }
                _buyPlaceWindowUI.getMainFrame().setVisible(false);
            }
        });
    }

    public void registerDontBuyPlaceButton(){
        _buyPlaceWindowUI.getDontBuyPlaceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//      TODO
                _buyPlaceWindowUI.getMainFrame().setVisible(false);

            }
        });
    }

    public BuyPlaceWindowUI getBuyPlaceWindowUI(){return _buyPlaceWindowUI;}
}
