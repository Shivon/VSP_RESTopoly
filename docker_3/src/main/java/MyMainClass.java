
import client.view.ClientUIController;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 26.04.2016.
 */
public class MyMainClass {

    public MyMainClass(){

    }

    public static void main(String[] args) throws UnirestException {

//        /*GUI starten*/
        new ClientUIController();

    }

}
