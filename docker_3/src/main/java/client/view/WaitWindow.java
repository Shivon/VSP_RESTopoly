package client.view;

import client.logic.GamesLogic;
import client.logic.UserLogic;
import client.logic.WaitLogic;
import clientUI.WaitWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.awt.*;

/**
 * Created by Jana Mareike on 17.06.2016.
 */
public class WaitWindow {

    private WaitWindowUI _waitWindowUI;
    private WaitLogic _waitLogic;
    private GamesLogic _gamesLogic;
    private UserLogic _userLogic;

    public WaitWindow(WaitWindowUI waitWindowUI, WaitLogic waitLogic, GamesLogic gamesLogic, UserLogic userLogic) throws UnirestException {
        _waitWindowUI = waitWindowUI;
        _waitLogic = waitLogic;
        _gamesLogic = gamesLogic;
        _userLogic = userLogic;
//        setSaldo();
    }

    public void showEvent(Event event){
        _waitWindowUI.getWaitText().setText(getWindowText() + "New Event!\n" + event);

    }

    public String getWindowText(){
        return  _waitWindowUI.getWaitText().getText() + "\n";
    }

    public void setWaitWindowText() {
        _waitWindowUI.getWaitText().setText(getWindowText() + "Now it´s your turn to roll!");
        _waitWindowUI.getWaitText().setForeground(Color.RED);
    }

    public void showGameHasStarted(){
        _waitWindowUI.getWaitText().setText("Game has started");
    }

//    public void setSaldo() throws UnirestException {
//        int saldo = _waitLogic.getSaldo(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser());
//        _waitWindowUI.getSaldoTextArea().setText("" + saldo);
//    }

    public WaitWindowUI getWaitWindowUI(){return _waitWindowUI;}
}
