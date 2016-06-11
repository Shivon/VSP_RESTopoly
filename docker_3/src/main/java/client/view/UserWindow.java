package client.view;

import client.adapter.UserAdapter;
import client.logic.UserLogic;
import client.logic.WaitLogic;
import client.model.Client;
import client.model.User;
import client.service.ClientService;
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
    private UserAdapter _userAdapter;
    private UserLogic _userLogic;
    private String _userName;

    public UserWindow() throws UnirestException{
        _userWindowUI = new UserWindowUI();
        _userAdapter = new UserAdapter();
        _userLogic = new UserLogic(_userWindowUI, _userAdapter);
        registerSubmitUserName();
    }

    public void registerSubmitUserName(){
        System.out.println("hallo");
        _userWindowUI.getLogInSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hallo");
                if(_userLogic.isLoginAreaNotEmpty()){
                    _userName = _userLogic.getUserNameFromLoginArea();
                    try {
                        if(_userLogic.checkIfUserAlreadyExists(_userName)){
                            JOptionPane.showMessageDialog(null, "user name not available", "choose an other name!",
                                        JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        _userLogic.setUser(_userName);
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    _userLogic.closeUserUI();

                    try {
                        _userLogic.openGamesWindow(_userLogic.getUser(_userName));
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
