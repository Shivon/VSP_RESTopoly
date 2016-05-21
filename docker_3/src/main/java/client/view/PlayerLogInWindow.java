package client.view;

import client.adapter.PlayerAdapter;
import client.model.gameModels.Player;
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
    private VstTableModel _gamesTableModel;
    private PlayerAdapter _playerAdapter;

    public PlayerLogInWindow(VstTableModel tableModel, GamesWindowUI gameswindow){
        this._gamesWindowUI = gameswindow;
        _playerWindowUI = new PlayerLoginWindowUI();
        _gamesTableModel = tableModel;
        _playerWindowUI.getPlayerNameFrame().setVisible(true);
        _playerAdapter = new PlayerAdapter();
        registerSubmitPlayerName();
    }
// gibt es gar keinen playerName?????????????????????
    private void registerSubmitPlayerName() {
        _playerWindowUI.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!_playerWindowUI.getPlayerNameArea().getText().isEmpty()) {
                    _playerName = _playerWindowUI.getPlayerNameArea().getText();
                    System.out.println(_playerName);
                    //Semaphore
                    try {
                        Player[] playerList  = _playerAdapter.getPlayers(_gamesTableModel, _gamesWindowUI);
                        for (int i = 0 ; i <= playerList.length; i++) {
                            playerList[i].
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
