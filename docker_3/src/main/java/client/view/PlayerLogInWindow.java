package client.view;

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
public class PlayerLogInWindow {

    private String _playerName;
    private PlayerLoginWindowUI _playerWindowUI;
    private GamesWindowUI _gamesWindowUI;
    private VstTableModel gamesTableModel;

    public PlayerLogInWindow(GamesWindowUI gameswindow){
        this._gamesWindowUI = gameswindow;
        _playerWindowUI = new PlayerLoginWindowUI();
        registerSubmitPlayerName();
    }

    private void registerSubmitPlayerName() {
        _playerWindowUI.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!_playerWindowUI.getPlayerNameArea().getText().isEmpty()) {
                    _playerName = _playerWindowUI.getPlayerNameArea().getText();
                    //Semaphore
                    try {
//                         TODO player IP and Port
                        HttpResponse<ArrayList> playerNameListResponse =  Unirest.get("http://localhost:4567/games/"
                                + gamesTableModel.getValueAt(_gamesWindowUI.getAllGameTable().getSelectedRow(),0) + "/players")
                                .asObject(ArrayList.class);
                        ArrayList<HashMap> playerNameList = playerNameListResponse.getBody();
                        for (HashMap player : playerNameList) {
                            String name = player.get("name").toString();
                            if(name.equals(_playerName)){
                                JOptionPane.showMessageDialog(null, "player name not available", "choose an other name!",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        Unirest.post("http://localhost:4567/games/"
                                + gamesTableModel.getValueAt(_gamesWindowUI.getAllGameTable().getSelectedRow(),0) + "/players")
                                .field("name", _playerName)
                                .asJson();
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    //Semaphore Ende
                }else{
                    JOptionPane.showMessageDialog(null, "No player name", "add player name",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
