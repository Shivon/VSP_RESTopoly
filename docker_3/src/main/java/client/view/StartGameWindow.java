package client.view;

import client.adapter.PlayerAdapter;
import client.logic.GamesLogic;
import client.logic.UserLogic;
import client.logic.WaitLogic;
import client.model.User;
import client.model.gameModels.Game;
import client.service.ClientService;
import clientUI.StartGameWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jana Mareike on 11.06.2016.
 */
public class StartGameWindow {

    private StartGameWindowUI _startGameWindowUI;
    private GamesLogic _gamesLogic;
    private WaitWindow _waitWindow;
    private WaitLogic _waitLogic;
    private UserLogic _userLogic;

    public StartGameWindow(GamesLogic gamesLogic, StartGameWindowUI startGameWindowUI,
                           WaitWindow waitWindow, WaitLogic waitLogic, UserLogic userLogic) throws UnirestException {

        _gamesLogic = gamesLogic;
        _startGameWindowUI = startGameWindowUI;
        _waitWindow = waitWindow;
        _waitLogic = waitLogic;
        _userLogic = userLogic;

        registerStartGame( );
    }

//    if game was started by an other user
    public void handleGameStartPost() throws UnirestException {
        _startGameWindowUI.getStartGameFrame().setVisible(false);
        _waitWindow.getWaitWindowUI().getSaldoTextArea().
                setText("" + _waitLogic.getSaldo(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser()));
        _waitWindow.getWaitWindowUI().getWaitFrame().setVisible(true);
    }

    public void registerStartGame(){
        _startGameWindowUI.getStartGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    _gamesLogic.startGame(_gamesLogic.getCurrentGame());
                    _startGameWindowUI.getStartGameFrame().setVisible(false);
//                    _waitWindow.getWaitWindowUI().getSaldoTextArea().
//                            setText("" + _waitLogic.getSaldo(_gamesLogic.getCurrentGame(), _userLogic.getCurrentUser()));
                    _waitWindow.getWaitWindowUI().getWaitFrame().setVisible(true);
                } catch (UnirestException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public StartGameWindowUI getStartGameWindowUI(){ return _startGameWindowUI; }
}
