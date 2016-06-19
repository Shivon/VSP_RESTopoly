package client.view;

import client.logic.GamesLogic;
import client.logic.PlayerLogic;
import client.logic.UserLogic;
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
    private StartGameWindow _startGameWindow;
    private UserLogic _userLogic;
    private GamesLogic _gamesLogic;

    public PlayerLogInWindow( PlayerLoginWindowUI playerLoginWindowUI, UserLogic userLogic, PlayerLogic playerLogic,
                              StartGameWindow startGameWindow, GamesLogic gamesLogic) throws UnirestException {

        _startGameWindow = startGameWindow;
        _playerWindowUI = playerLoginWindowUI;
        _playerLogic = playerLogic;
        _userLogic = userLogic;
        _gamesLogic = gamesLogic;
        // _playerWindowUI.getAvailablePawnsArea().setText(_playerLogic.getAvailablePawns(gamesLogic.getCurrentGame()).toString());
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
                            _startGameWindow.getStartGameWindowUI().getStartGameFrame().setVisible(true);
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
        if(!_playerLogic.getAvailablePawns(_gamesLogic.getCurrentGame()).contains(getPlayerPawn())){
            return true;
        }
        return false;
    }
}
