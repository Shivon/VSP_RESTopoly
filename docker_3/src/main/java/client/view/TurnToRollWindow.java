package client.view;

import client.adapter.DiceAdapter;
import client.adapter.IPAdresses;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import clientUI.TurnToRollWindowUI;
//import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class TurnToRollWindow {

//    soll aufgehen, wenn man an der Reihe ist
    private TurnToRollWindowUI _turnToRollWindowUI;
    private IPAdresses _ipAdresses;
    private Game _game;
    private User _user;
    private DiceAdapter _diceAdapter;

    public TurnToRollWindow(Game game, User user){
        _turnToRollWindowUI = new TurnToRollWindowUI();
        _ipAdresses = new IPAdresses();
        _game = game;
        _user = user;
        _diceAdapter = new DiceAdapter();
        _turnToRollWindowUI.getDiceFrame().setVisible(true);
        registerRoll();
    }

    private void registerRoll(){
        _turnToRollWindowUI.getDiceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    _diceAdapter.postDiceRoll(_game, _user);
                    int number = _diceAdapter.getDiceRollNumber();
                    _turnToRollWindowUI.getDiceNumber().setText("" + number);
                } catch (UnirestException e1) {
                    e1.printStackTrace();
                }



            }
        });
    }

}
