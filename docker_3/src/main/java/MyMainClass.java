
import client.view.ClientController;
import client.view.ClientView;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Jana Mareike on 26.04.2016.
 */
public class MyMainClass {

    public MyMainClass(){

    }

    public static void main(String[] args) {

        new ClientController();
//         /* Test: Erzeuge Client und starte ihn. */
//        String host = args[0];
//        int port = Integer.parseInt(args[1]);
//
//        /*GUI starten*/
//        ClientController view = null;
//        try {
//            Socket socket = new Socket(host, port);
//            view = new ClientController(socket);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
