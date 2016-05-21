
import client.service.MainThread;
//import client.view.ClientUIController;
import client.view.GamesWindow;
import client.view.TurnToRollWindow;
import client.view.UserWindow;
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
