package client.view;

import client.model.User;
import client.model.gameModels.Game;
import clientUI.WaitWindowUI;

import java.awt.*;

/**
 * Created by Jana Mareike on 07.06.2016.
 */
public class WaitWindow {

    private WaitWindowUI _waitWindowUI;
    private TurnToRollWindow _turnToRollWindow;
    private User _user;
    private Game _game;

    public WaitWindow(User user , Game game){
        _waitWindowUI = new WaitWindowUI();
        _waitWindowUI.getWaitFrame().setVisible(true);
        _user = user;
        _game = game;
        registerplayersTurn();
    }

    public void registerplayersTurn(){
        //                    TODO     post/client/turn registrieren
//                        if(post/client/turn registriert){
                        _waitWindowUI.getWaitText().setText("Now it´s your turn to roll!");
                        _waitWindowUI.getWaitText().setForeground(Color.RED);
                        _turnToRollWindow = new TurnToRollWindow(_game, _user);

//                    }
    }

}
