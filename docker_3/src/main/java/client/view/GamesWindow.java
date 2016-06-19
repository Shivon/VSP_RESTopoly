package client.view;

import client.adapter.GamesAdapter;
import client.adapter.PlayerAdapter;
import client.logic.GamesLogic;
import client.logic.PlayerLogic;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.model.gameModels.Ready;
import clientUI.GamesWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.h2.util.New;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class GamesWindow {

    private GamesWindowUI _gamesWindowUI;
    private VstTableModel _gamesTableModel;
    private Game _selectedGame;
    private GamesLogic _gamesLogic;
    private NewGameWindow _newGamesWindow;
    private PlayerLogInWindow _playerLoginWindow;
    private PlayerLogic _playerLogic;

    public GamesWindow(GamesWindowUI gamesWindowUI, GamesLogic gamesLogic, NewGameWindow newGameWindow,
                       PlayerLogInWindow playerLogInWindow, PlayerLogic playerLogic) throws UnirestException {
        _gamesWindowUI = gamesWindowUI;
        _gamesLogic = gamesLogic;
        _newGamesWindow = newGameWindow;
        _playerLoginWindow = playerLogInWindow;
        _playerLogic = playerLogic;

        registerSubmitJoinTheGame();
        registerNewGame();
        selectRow();
    }

    public void buildGamesWindowUI(){
        try {
            _gamesTableModel =  new VstTableModel(_gamesLogic.getGamesWithStatusRegistration());
            for (int i = 0; i < _gamesLogic.getGamesWithStatusRegistration().size(); i++ ) {
                _gamesWindowUI.getTableModel()
                        .addRow(new java.lang.Object[]{_gamesTableModel.getValueAt(i, 1)});
            }
        } catch (UnirestException e1) {
            e1.printStackTrace();
        }
        _gamesWindowUI.getMainFrame().setVisible(true);
    }

    private Game selectRow(){
        _gamesWindowUI.getAllGameTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = _gamesWindowUI.getAllGameTable().rowAtPoint(evt.getPoint());
                if (_gamesLogic.checkIfRowIsChosen(row)) {
                    _selectedGame = _gamesTableModel.getGameAt(row);
                }
            }
        });
        _gamesLogic.setCurrentGame(_selectedGame);
        return _selectedGame;
    }

    private void registerSubmitJoinTheGame() {
        _gamesWindowUI.getTakePartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(_gamesLogic.checkIfThereAreNoGames()){
                    openNewGameWindow();
                } else if (isRowSelected()) {
                    _gamesWindowUI.getMainFrame().setVisible(false);
                    try {
                        _playerLoginWindow.getPlayerLoginWindowUI().getAvailablePawnsArea().
                                setText(_playerLogic.getAvailablePawns(_gamesLogic.getCurrentGame()).toString());
                    } catch (UnirestException e1) {
                        e1.printStackTrace();
                    }
                    _playerLoginWindow.getPlayerLoginWindowUI().getPlayerNameFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No game selected", "Select Game!",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void registerNewGame(){
        _gamesWindowUI.getNewGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewGameWindow();
            }
        });
    }

    private void openNewGameWindow(){
        _newGamesWindow.getNewGamesWindowUI().getLogInFrame().setVisible(true);
        _gamesWindowUI.getMainFrame().setVisible(false);
    }

    private boolean isRowSelected(){
        if (selectRow() != null){
            return true;
        }
        return false;
    }

    public GamesWindowUI getGamesWindowUI(){
        return _gamesWindowUI;
    }

}
