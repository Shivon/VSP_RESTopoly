package client;

import client.logic.WaitLogic;
import client.util.yellowpages.IYellowPages;
import client.util.yellowpages.YellowPagesService;
import client.view.*;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 26.04.2016.
 */
public class MyMainClass {

    public MyMainClass(){

    }

    public static void main(String[] args) throws UnirestException {
        new UserWindow();
    }

}
