package client.view;

import client.adapter.GamesAdapter;
import client.adapter.UserAdapter;
import client.model.User;
import client.model.gameModels.Game;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
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
    private Gson gson = new Gson();

    public UserWindow() throws UnirestException{
        _userWindowUI = new UserWindowUI();
        _userAdapter = new UserAdapter("172.18.0.41:4567");
//        _gamesWindow = new GamesWindow(VstTableModel gamesTableModel );
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
                    System.out.println("Name in der GUI: " + _userName);
                    //                Semaphore?
                    try {
                        List<User> users = _userAdapter.getUsers();
                        System.out.println(""+users);
                        for (User user : users) {
//                             String name = user.getName();
                             if (user.hasName(_userName)) {
                                JOptionPane.showMessageDialog(null, "user name not available", "choose an other name!",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        _userAdapter.putUser(_userName);
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    //Semaphore Ende
                    _userWindowUI.getLogInFrame().setVisible(false);
//                    TODO alle Games holen und im Tabel auflisten
                    //Semaphore, weil max Anzahl Spieler?
                    try {
                        Game[] gamesList = _gamesAdapter.getGames();
                        _gamesTableModel =  new VstTableModel(gamesList);
                        _gamesWindow = new GamesWindow(_gamesTableModel);
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

    public VstTableModel getGamesTableModel(){
        return _gamesTableModel;
    }
}
