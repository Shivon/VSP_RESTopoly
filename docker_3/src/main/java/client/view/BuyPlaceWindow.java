package client.view;

import client.adapter.BanksAdapter;
import client.adapter.BoardsAdapter;
import client.adapter.BrokerAdapter;

import client.adapter.PlayerAdapter;
import client.logic.BuyLogic;
import client.logic.GamesLogic;
import client.logic.UserLogic;
import client.logic.WaitLogic;
import clientUI.BuyPlaceWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

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
    private BanksAdapter _banksAdapter;
    private BoardsAdapter _boardsAdapter;

    public BuyPlaceWindow(BuyPlaceWindowUI buyPlaceWindowUI, WaitWindow waitWindow, WaitLogic waitLogic,
                          GamesLogic gamesLogic, UserLogic userLogic, BrokerAdapter brokerAdapter,
                          BuyLogic buyLogic, PlayerAdapter playerAdapter, BanksAdapter banksAdapter, BoardsAdapter boardsAdapter){

        _waitLogic = waitLogic;
        _buyPlaceWindowUI = buyPlaceWindowUI;
        _waitWindow = waitWindow;
        _gamesLogic = gamesLogic;
        _userLogic = userLogic;
        _brokerAdapter = brokerAdapter;
        _buyLogic = buyLogic;
        _playerAdapter = playerAdapter;
        _banksAdapter = banksAdapter;
        _boardsAdapter = boardsAdapter;

        registerBuyPlaceButton();
        registerDontBuyPlaceButton();
    }

    public void registerBuyPlaceButton(){
        _buyPlaceWindowUI.getPlaceToBuyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//      TODO
//                try {
//                    if ((_banksAdapter.getAccount(_userLogic.getCurrentUser(), _gamesLogic.getCurrentGame()).getSaldo()
//                            - _boardsAdapter.getCurrentPlace(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser()).getValue()) < 0) {
//                        _waitWindow.getWaitWindowUI().getWaitText().
//                                setText("You have not enough money!");
//                        _buyPlaceWindowUI.getMainFrame().setVisible(false);
//                    } else {
//                        try {
//                            _brokerAdapter.buyPlace(_boardsAdapter.getCurrentPlace(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser()),
//                                    _playerAdapter.findPlayerByUser(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser()));
//                            _waitWindow.getWaitWindowUI().getSaldoTextArea().
//                                    setText("" + _waitLogic.getSaldo(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser()));
//                        } catch (UnirestException e1) {
//                            e1.printStackTrace();
//                        }
//                        _buyPlaceWindowUI.getMainFrame().setVisible(false);
//                    }
//                } catch (UnirestException e1) {
//                    e1.printStackTrace();
//                }
            }
        });
    }

    public void registerDontBuyPlaceButton(){
        _buyPlaceWindowUI.getDontBuyPlaceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _buyPlaceWindowUI.getMainFrame().setVisible(false);

            }
        });
    }

    public BuyPlaceWindowUI getBuyPlaceWindowUI(){return _buyPlaceWindowUI;}
}
