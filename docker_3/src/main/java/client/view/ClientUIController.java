package client.view;

import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.JsonNode;

import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.omg.CORBA.Object;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;


/**
 * Created by Jana Mareike on 03.05.2016.
 */
public class ClientUIController {

    private ClientView _view;
    private String _name;
    private Gson gson = new Gson();

    public ClientUIController() {
        _view = new ClientView();
        registerSubmitUserName();
        registerSubmitJoinTheGame();
    }


private void registerSubmitJoinTheGame() {
        _view.getTakePartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                wenn game markiert, dann
                System.out.println("JOIN THE GAME");
                _view.getMainFrame().setVisible(false);
                _view.getPlayerNameFrame().setVisible(true);
            }
        });
    }

    public void registerSubmitUserName(){
        System.out.println("hallo");
        _view.getLogInSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hallo");
                if(! _view.getLogInArea().getText().isEmpty()){
                    _name = _view.getLogInArea().getText();
                    System.out.println("Name in der GUI: " + _name);
                    //                TODO prüfen, ob name vergeben?
                    try {
                      String userNameListString =  Unirest.get("http://localhost:4567/users").asJson().toString();
                        String[] userNameList = gson.fromJson(userNameListString,?????????????);
                        for ( String name : userNameList) {
                            if(name.equals(_name)){
                                JOptionPane.showMessageDialog(null, "user name not available", "choose an other name!",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }

//                    TODO weiß nicht, ob richtig so?????

                    try {
                        Unirest.post("http://localhost:4567/users")
                                .field("name", _name)
                                .asJson();
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    _view.getLogInFrame().setVisible(false);
//                    TODO alle Games holen und im Tabel auflisten
                    try {
                        List<String> gamesList = (List<String>) Unirest.get("http://localhost:4567/games")
                                .asJson();
                        for (String game : gamesList) {
//
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    _view.getMainFrame().setVisible(true);
                }else {
                    JOptionPane.showMessageDialog(null, "No user name", "no user name",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
