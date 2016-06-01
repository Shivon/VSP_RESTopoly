package client.view;

import client.adapter.GamesAdapter;
import client.adapter.IPAdresses;
import client.adapter.UserAdapter;
import client.model.User;
import client.model.Users;
import client.model.gameModels.Game;
import clientUI.UserWindowUI;
import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class UserWindow {

    private UserWindowUI _userWindowUI;
    private GamesWindow _gamesWindow;
    private VstTableModel _gamesTableModel;
    private String _userName;
    private UserAdapter _userAdapter;
    private GamesAdapter _gamesAdapter;
//    private IPAdresses _ipAdresses;
    private User _user;
//    private Gson gson = new Gson();

    public UserWindow() throws UnirestException{
        _userWindowUI = new UserWindowUI();
        _userAdapter = new UserAdapter();
        _gamesAdapter = new GamesAdapter();
        registerSubmitUserName();
    }

    public void registerSubmitUserName(){
        System.out.println("hallo");
        _userWindowUI.getLogInSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hallo");
                if(! _userWindowUI.getLogInArea().getText().isEmpty()){
                    _userName = _userWindowUI.getLogInArea().getText();
                    //                Semaphore?
                    try {
                        _user = _userAdapter.getUser(_userName.toLowerCase());
                        if(_user != null){
                            JOptionPane.showMessageDialog(null, "user name not available", "choose an other name!",
                                        JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        _userAdapter.postUser(_userName);
                        System.out.println("user after post " + _userAdapter.getUser(_userName.toLowerCase()));
//                        _userAdapter.putUser(_userName);
//                        System.out.println("username after put"+_userName);
//                        System.out.println("users after put" +_userAdapter.getUsers());
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    //Semaphore Ende
                    _userWindowUI.getLogInFrame().setVisible(false);
//                    alle Games holen und im Tabel auflisten
                    //Semaphore, weil max Anzahl Spieler?
                    try {
                        Game[] gamesList = _gamesAdapter.getGames();
                        System.out.println(gamesList);
                        _gamesTableModel =  new VstTableModel(gamesList);
                        _user =  _userAdapter.getUser(_userName);
                        _gamesWindow = new GamesWindow(_gamesTableModel, _user);
                        for (int i = 0; i < gamesList.length; i++ ) {
                            _gamesWindow.getGamesWindowUI().getTableModel() // ui defaulttablemoodel
                                    .addRow(new java.lang.Object[]{_gamesTableModel.getValueAt(i, 1)});
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    //Semaphore Ende
                    _gamesWindow.getGamesWindowUI().getMainFrame().setVisible(true);
                }else {
                    JOptionPane.showMessageDialog(null, "No user name", "no user name",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

//    public VstTableModel getGamesTableModel(){
//        return _gamesTableModel;
//    }
}
