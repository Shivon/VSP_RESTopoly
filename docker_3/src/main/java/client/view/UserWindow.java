package client.view;

import client.adapter.GamesAdapter;
import client.adapter.IPAdresses;
import client.adapter.PlayerAdapter;
import client.adapter.UserAdapter;
import client.logic.UserLogic;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.GameStatus;
import clientUI.UserWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static client.model.gameModels.GameStatus.*;
/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class UserWindow {

    private UserWindowUI _userWindowUI;
    private GamesWindow _gamesWindow;
    private VstTableModel _gamesTableModel;
    private String _userName;
    private UserAdapter _userAdapter;
    private PlayerAdapter _playerAdapter;
    private GamesAdapter _gamesAdapter;
    private User _user;

    private UserLogic _userLogic;


    public UserWindow() throws UnirestException{
        _userWindowUI = new UserWindowUI();

        _userLogic = new UserLogic(_userWindowUI);

        _userAdapter = new UserAdapter();
        _playerAdapter = new PlayerAdapter();
        _gamesAdapter = new GamesAdapter(_playerAdapter);
        registerSubmitUserName();
    }

    public void registerSubmitUserName(){
        System.out.println("hallo");
        _userWindowUI.getLogInSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hallo");
                if(_userLogic.isLoginAreaNotEmpty()){
                    _userLogic.getUserNameFromLoginArea();
                    try {
                        if(_userLogic.checkIfUserAlreadyExists()){
                            JOptionPane.showMessageDialog(null, "user name not available", "choose an other name!",
                                        JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        _userLogic.postUser(_userLogic.getUserNameFromLoginArea());
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        _userLogic.getUser();
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    _userLogic.closeUserUI();

                    try {
                        _userLogic.openGamesWindow(_userLogic.getUser());
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(null, "No user name", "no user name",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
