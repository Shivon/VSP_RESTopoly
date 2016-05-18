package client.view;

import client.adapter.PlayerAdapter;
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
                        HttpResponse<ArrayList> playerNameListResponse = PlayerAdapter.getPlayers(gamesTableModel, _gamesWindowUI);
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
                      PlayerAdapter.postPlayer(gamesTableModel, _gamesWindowUI, _playerName);
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
