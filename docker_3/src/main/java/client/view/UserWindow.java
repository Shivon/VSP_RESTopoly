package client.view;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class UserWindow {

    private UserWindowUI _userWindowUI;
    private GamesWindowUI _gamesWindowUI;
    private VstTableModel gamesTableModel;
    private String _userName;
    private Gson gson = new Gson();

    public UserWindow() throws UnirestException{
        _userWindowUI = new UserWindowUI();

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
//                         TODO user IP and Port
                        HttpResponse<ArrayList> userNameListResponse =  Unirest.get("http://localhost:4567/users")
                                .asObject(ArrayList.class);
                        ArrayList<HashMap> userNameList = userNameListResponse.getBody();
                        for (HashMap user : userNameList) {
                            String name = user.get("name").toString();
                            if(name.equals(_userName)){
                                JOptionPane.showMessageDialog(null, "user name not available", "choose an other name!",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }

//                    TODO weiß nicht, ob richtig so?????

                    try {
                        Unirest.put("http://localhost:4567/users")
                                .field("name", _userName)
                                .asJson();
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    //Semaphore Ende
                    _userWindowUI.getLogInFrame().setVisible(false);
//                    TODO alle Games holen und im Tabel auflisten
                    //Semaphore, weil max Anzahl Spieler
                    try {
                        HttpResponse<ArrayList> gamesListResponse =  Unirest.get("http://localhost:4567/games")
                                .asObject(ArrayList.class);
                        ArrayList<HashMap> gamesList = gamesListResponse.getBody();
                        gamesTableModel =  new VstTableModel(gamesList);

                        for (int i = 0; i < gamesList.size(); i++ ) {
                            _gamesWindowUI.getTableModel().addRow(new java.lang.Object[]{gamesTableModel.getValueAt(i, 1)});
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    //Semaphore Ende
                    _gamesWindowUI.getMainFrame().setVisible(true);
                }else {
                    JOptionPane.showMessageDialog(null, "No user name", "no user name",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
