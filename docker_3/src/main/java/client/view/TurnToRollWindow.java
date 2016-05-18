package client.view;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class TurnToRollWindow {

    private TurnToRollWindowUI _turnToRollWindowUI;

    public TurnToRollWindow(){
        _turnToRollWindowUI = new TurnToRollWindowUI();
        _turnToRollWindowUI.getDiceFrame().setVisible(true);
        registerRoll();
    }

    private void registerRoll(){
        _turnToRollWindowUI.getDiceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HttpResponse<Integer> number =  Unirest.get("http://172.18.0.67:4567/dice")
                            .asObject(Integer.class);
                    _turnToRollWindowUI.getDiceNumber().setText("" + number);
                } catch (UnirestException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
