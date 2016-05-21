//package client.view;
//
//import com.google.gson.JsonArray;
//import com.google.gson.reflect.TypeToken;
//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;
//
//import com.google.gson.Gson;
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;
//import org.omg.CORBA.Object;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//
//
///**
// * Created by Jana Mareike on 03.05.2016.
// */
//public class ClientUIController {
//
//    private ClientView _view;
//    private String _userName;
//    private String _playerName;
//    private VstTableModel gamesTableModel;
//    private Gson gson = new Gson();
//
//    public ClientUIController() throws UnirestException {
//        _view = new ClientView();
//
//        registerSubmitUserName();
//        registerSubmitJoinTheGame();
//        registerSubmitPlayerName();
//    }
//
//    private void registerSubmitPlayerName() {
//        _view.getTakePartButton().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(!_view.getPlayerNameArea().getText().isEmpty()) {
//                    _playerName = _view.getPlayerNameArea().getText();
//                    try {
////                         TODO player IP and Port
//                        HttpResponse<ArrayList> playerNameListResponse =  Unirest.get("http://localhost:4567/games/"
//                                + gamesTableModel.getValueAt(_view.getAllGameTable().getSelectedRow(),0) + "/players")
//                                .asObject(ArrayList.class);
//                        ArrayList<HashMap> playerNameList = playerNameListResponse.getBody();
//                        for (HashMap player : playerNameList) {
//                            String name = player.get("name").toString();
//                            if(name.equals(_userName)){
//                                JOptionPane.showMessageDialog(null, "player name not available", "choose an other name!",
//                                        JOptionPane.ERROR_MESSAGE);
//                            }
//                        }
//                    } catch (UnirestException e1) {
//                        e1.printStackTrace();
//                    }
//                    try {
//                        Unirest.post("http://localhost:4567/games/"
//                                + gamesTableModel.getValueAt(_view.getAllGameTable().getSelectedRow(),0) + "/players")
//                                .field("name", _playerName)
//                                .asJson();
//                    } catch (UnirestException e1) {
//                        e1.printStackTrace();
//                    }
//                }else{
//                    JOptionPane.showMessageDialog(null, "No player name", "add player name",
//                            JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//    }
//
//
//    private void registerSubmitJoinTheGame() {
//        _view.getTakePartButton().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                wenn game markiert, dann
//                System.out.println("JOIN THE GAME");
//                gamesTableModel.getValueAt(_view.getAllGameTable().getSelectedRow(), 0);
//                _view.getPlayerNameFrame().setVisible(true);
//            }
//        });
//    }
//
//    public void registerSubmitUserName(){
//        System.out.println("hallo");
//        _view.getLogInSubmit().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("hallo");
//                if(! _view.getLogInArea().getText().isEmpty()){
//                    _userName = _view.getLogInArea().getText();
//                    System.out.println("Name in der GUI: " + _userName);
//                    //                TODO prüfen, ob name vergeben?
//                    try {
////                         TODO user IP and Port
//                      HttpResponse<ArrayList> userNameListResponse =  Unirest.get("http://localhost:4567/users")
//                              .asObject(ArrayList.class);
//                        ArrayList<HashMap> userNameList = userNameListResponse.getBody();
//                        for (HashMap user : userNameList) {
//                            String name = user.get("name").toString();
//                            if(name.equals(_userName)){
//                                JOptionPane.showMessageDialog(null, "user name not available", "choose an other name!",
//                                        JOptionPane.ERROR_MESSAGE);
//                            }
//                        }
//                    } catch (UnirestException e1) {
//                        e1.printStackTrace();
//                    }
//
////                    TODO weiß nicht, ob richtig so?????
//
//                    try {
//                        Unirest.post("http://localhost:4567/users")
//                                .field("name", _userName)
//                                .asJson();
//                    } catch (UnirestException e1) {
//                        e1.printStackTrace();
//                    }
//                    _view.getLogInFrame().setVisible(false);
////                    TODO alle Games holen und im Tabel auflisten
//                    try {
//                        HttpResponse<ArrayList> gamesListResponse =  Unirest.get("http://localhost:4567/games")
//                                .asObject(ArrayList.class);
//                        ArrayList<HashMap> gamesList = gamesListResponse.getBody();
//                        gamesTableModel =  new VstTableModel(gamesList);
//
//                        for (int i = 0; i < gamesList.size(); i++ ) {
//                            _view.getTableModel().addRow(new java.lang.Object[]{gamesTableModel.getValueAt(i, 1)});
//                        }
//                    } catch (UnirestException e1) {
//                        e1.printStackTrace();
//                    }
//                    _view.getMainFrame().setVisible(true);
//                }else {
//                    JOptionPane.showMessageDialog(null, "No user name", "no user name",
//                            JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//    }
//}
