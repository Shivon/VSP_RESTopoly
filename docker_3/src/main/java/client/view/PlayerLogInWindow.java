package client.view;

import client.EventSubscriptions;
import client.logic.GamesLogic;
import client.logic.PlayerLogic;
import client.logic.UserLogic;
import client.logic.WaitLogic;
import client.model.User;
import client.model.gameModels.Game;
import clientUI.PlayerLoginWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class PlayerLogInWindow {

    private PlayerLoginWindowUI _playerWindowUI;
    private PlayerLogic _playerLogic;
//    private StartGameWindow _startGameWindow;
    private UserLogic _userLogic;
    private GamesLogic _gamesLogic;
    private WaitWindow _waitWindow;
    private WaitLogic _waitLogic;

    public PlayerLogInWindow( PlayerLoginWindowUI playerLoginWindowUI, UserLogic userLogic, PlayerLogic playerLogic,
                              WaitWindow waitWindow, GamesLogic gamesLogic, WaitLogic waitLogic) throws UnirestException {

//        _startGameWindow = startGameWindow;
        _waitWindow = waitWindow;
        _playerWindowUI = playerLoginWindowUI;
        _playerLogic = playerLogic;
        _userLogic = userLogic;
        _gamesLogic = gamesLogic;
        _waitLogic = waitLogic;

        registerSubmitPlayerPawn();
    }

    private void registerSubmitPlayerPawn() {
        _playerWindowUI.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkPlayerPawnEntered()){
                    try {
                        if(checkIfPlayerPawnIsNotAvailable()){
                            JOptionPane.showMessageDialog(null, "pawn not available", "choose an other pawn!",
                                    JOptionPane.ERROR_MESSAGE);
                        }else{
                            String pawn = getPlayerPawn();
                            _playerLogic.registerPlayer(pawn);
                            closePlayerWindow();

//                            just 6 players possible, so no start Window
//                            _startGameWindow.getStartGameWindowUI().getStartGameFrame().setVisible(true);
//TODO Banks
                    _waitWindow.getWaitWindowUI().getSaldoTextArea().
                            setText("" + _waitLogic.getSaldo(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser()));
                           _waitWindow.getWaitWindowUI().getWaitFrame().setVisible(true);
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No pawn chosen", "enter pawn",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public boolean checkPlayerPawnEntered(){
        if(!_playerWindowUI.getPlayerPawnArea().getText().isEmpty()) {
            return true;
        }
        return false;
    }

    public String getPlayerPawn(){
        return  _playerWindowUI.getPlayerPawnArea().getText();
    }

    public PlayerLoginWindowUI getPlayerLoginWindowUI(){return _playerWindowUI;}

    public void closePlayerWindow(){
        _playerWindowUI.getPlayerNameFrame().setVisible(false);
    }

    public boolean checkIfPlayerPawnIsNotAvailable() throws UnirestException {
        if(!_playerLogic.getAvailablePawns().contains(getPlayerPawn())){
            return true;
        }
        return false;
    }
}
