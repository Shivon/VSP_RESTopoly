package main.client;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Jana Mareike on 26.04.2016.
 */
public class Main {

    public Main(){

    }

    public static void main(String[] args) {

         /* Test: Erzeuge Client und starte ihn. */
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        /*GUI starten*/
        Werkzeug ui = null;
        try {
            Socket socket = new Socket(host, port);
            ui = new Werkzeug(socket);
            receiverThread nachrichtenErhaltenThread = new receiverThread(ui, socket);
            nachrichtenErhaltenThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
