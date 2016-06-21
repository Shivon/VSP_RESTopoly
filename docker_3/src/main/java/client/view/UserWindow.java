package client.view;
import client.logic.UserLogic;
import clientUI.UserWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class UserWindow {

    private UserWindowUI _userWindowUI;
    private UserLogic _userLogic;
    private GamesWindow _gamesWindow;

    public UserWindow(UserWindowUI userWindowUI, UserLogic userLogic, GamesWindow gamesWindow) throws UnirestException{
        _userWindowUI = userWindowUI;
        _userLogic = userLogic;
        _gamesWindow = gamesWindow;
        registerSubmitUserName();
    }

    public void registerSubmitUserName(){
        _userWindowUI.getLogInSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! _userWindowUI.getLogInArea().getText().isEmpty()){
                    String userName = _userWindowUI.getLogInArea().getText();
//                    TODO wenn Username schon im Post vom User gecheckt wird
//                    try {
//                        if(_userLogic.checkIfUserAlreadyExists(userName)){
//                            JOptionPane.showMessageDialog(null, "user name not available", "choose an other name!",
//                                        JOptionPane.ERROR_MESSAGE);
//                        }
//                    } catch (UnirestException e1) {
//                        e1.printStackTrace();
//                    }
//
//                    try {
//                       _userLogic.setCurrentUser(userName);
//                    } catch (UnirestException e1) {
//                        e1.printStackTrace();
//                    }
                    try {
                        String userReturn = _userLogic.setCurrentUser(userName);
                        if(userReturn.equals("401")){
                            JOptionPane.showMessageDialog(null, "user name not available", "choose an other name!",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    _userWindowUI.getLogInFrame().setVisible(false);
                    _gamesWindow.buildGamesWindowUI();
                }else {
                    JOptionPane.showMessageDialog(null, "No user name", "no user name",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
