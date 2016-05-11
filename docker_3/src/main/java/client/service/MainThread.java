package client.service;

import client.view.UserWindow;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class MainThread extends Thread {

    @Override
    public void run(){
        try {
            new UserWindow();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
