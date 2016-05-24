
import client.adapter.PlayerAdapter;
import client.service.MainThread;
//import client.view.ClientUIController;
import client.view.*;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 26.04.2016.
 */
public class MyMainClass {

    public MyMainClass(){

    }

    public static void main(String[] args) throws UnirestException {

//        new MainThread();
// new TurnToRollWindow();
        new UserWindow();
//        new GamesWindow();
    }

}
