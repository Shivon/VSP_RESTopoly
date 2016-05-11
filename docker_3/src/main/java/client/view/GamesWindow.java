package client.view;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class GamesWindow {

    private GamesWindowUI _gamesWindowUI;
    private PlayerLoginWindowUI _playerWindowUI;
    private VstTableModel gamesTableModel;
    private Gson gson = new Gson();

    public GamesWindow() throws UnirestException {
        _gamesWindowUI = new GamesWindowUI();
        _playerWindowUI = new PlayerLoginWindowUI();

        registerSubmitJoinTheGame();
    }

    private void registerSubmitJoinTheGame() {
        _gamesWindowUI.getTakePartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                wenn game markiert, dann
                if((gamesTableModel.getValueAt(_gamesWindowUI.getAllGameTable().getSelectedRow(), 0) != null)){
                    System.out.println("JOIN THE GAME");
                //    gamesTableModel.getValueAt(_gamesWindowUI.getAllGameTable().getSelectedRow(), 0);
                    _playerWindowUI.getPlayerNameFrame().setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "No game selected", "Select Game!",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
}
