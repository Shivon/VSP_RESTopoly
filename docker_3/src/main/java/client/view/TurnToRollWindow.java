package client.view;

import client.adapter.DiceAdapter;
import clientUI.TurnToRollWindowUI;
//import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class TurnToRollWindow {

//    soll aufgehen, wenn man an der Reihe ist
    private TurnToRollWindowUI _turnToRollWindowUI;
    private DiceAdapter _diceAdapter;

    public TurnToRollWindow(){
        _turnToRollWindowUI = new TurnToRollWindowUI();
        _diceAdapter = new DiceAdapter();
        _turnToRollWindowUI.getDiceFrame().setVisible(true);
        registerRoll();
    }

    private void registerRoll(){
        _turnToRollWindowUI.getDiceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int number = _diceAdapter.getDiceRollNumber();
                    _turnToRollWindowUI.getDiceNumber().setText("" + number);
                } catch (UnirestException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
