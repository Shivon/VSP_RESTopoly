package client.view;

import client.adapter.PlayerAdapter;
import client.model.gameModels.Player;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class PlayerLogInWindow {

    private String _playerPawn;
    private PlayerLoginWindowUI _playerWindowUI;
    private GamesWindowUI _gamesWindowUI;
    private VstTableModel _gamesTableModel;
    private PlayerAdapter _playerAdapter;
    private List<String> _pawnList;

    public PlayerLogInWindow(VstTableModel tableModel, GamesWindowUI gamesWindow){
        this._gamesWindowUI = gamesWindow;
        _pawnList = Arrays.asList("Car", "Shoe", "Hat", "Dog", "Ship");
        _playerWindowUI = new PlayerLoginWindowUI();
        _playerPawn = new String();
        this._gamesTableModel = tableModel;
        _playerWindowUI.getPlayerNameFrame().setVisible(true);
        _playerAdapter = new PlayerAdapter();
        registerSubmitPlayerPawn();
    }

    private List<String> showAvailablePawns() throws UnirestException {
        Player[] playerList  = _playerAdapter.getPlayers(_gamesTableModel, _gamesWindowUI);
        for (int i = 0 ; i <= playerList.length; i++) {
            for (int j = 0; j < _pawnList.size(); j++) {
                if (playerList[i].getPawn().equals(_pawnList.get(j))) {
                    _pawnList.remove(j);
                }
            }
        }
        return _pawnList;
    }

    private void registerSubmitPlayerPawn() {
        _playerWindowUI.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!_playerWindowUI.getPlayerPawnArea().getText().isEmpty()) {
                    _playerPawn = _playerWindowUI.getPlayerPawnArea().getText();
                    try {
                        if(!showAvailablePawns().contains(_playerPawn)){
                            JOptionPane.showMessageDialog(null, "pawn not available", "choose an other pawn!",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println(_playerPawn);
                    //Semaphore
                    try {
                      PlayerAdapter.postPlayer(_gamesTableModel, _gamesWindowUI, _playerPawn);
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    //Semaphore Ende
                }else{
                    JOptionPane.showMessageDialog(null, "No pawn chosen", "enter pawn",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
