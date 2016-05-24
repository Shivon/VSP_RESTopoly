package client.view;

import client.adapter.PlayerAdapter;
import client.model.gameModels.Game;
import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.control.TableSelectionModel;

import javax.swing.*;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class GamesWindow {

    private GamesWindowUI _gamesWindowUI;
    private PlayerLogInWindow _playerWindow;
    private PlayerAdapter _playerAdapter;
    private VstTableModel _gamesTableModel;
    private Gson gson = new Gson();
    private Game _selectedGame;

    public GamesWindow(VstTableModel gamesTableModel) throws UnirestException {
        this._gamesTableModel = gamesTableModel;
        _gamesWindowUI = new GamesWindowUI();
        _playerAdapter = new PlayerAdapter();
//        _playerWindowUI = new PlayerLoginWindowUI();
//        _gamesTableModel.addTableModelListener();
        registerSubmitJoinTheGame();
        selectRow();

    }

    private Game selectRow(){
        System.out.println("select row");
        _gamesWindowUI.getAllGameTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("maus click");
                int row = _gamesWindowUI.getAllGameTable().rowAtPoint(evt.getPoint());
                System.out.println(""+row);

                if (row >= 0){
                    System.out.println("" +_gamesWindowUI.getTableModel().getValueAt(row,0));
                   _selectedGame = _gamesTableModel.getGameAt(row);
                }
            }
        });
        System.out.println("selected Game"+ _selectedGame);
        return _selectedGame;
    }

    private void registerSubmitJoinTheGame() {
        _gamesWindowUI.getTakePartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                wenn game markiert, dann
                if (selectRow() != null){
                    try {
                        System.out.println("selected Game submit " + _selectedGame);
                        _playerWindow = new PlayerLogInWindow(_gamesTableModel, _gamesWindowUI, _selectedGame);
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No game selected", "Select Game!",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
        public GamesWindowUI getGamesWindowUI(){
        return _gamesWindowUI;
    }

}
