package main.client;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Jana on 15.11.2015.
 */
public class receiverThread extends  Thread {

    private Werkzeug _ui;
    private Socket _socket;
    private BufferedReader inFromServer;
    private DataOutputStream outToServer;
    private String sentence;

    public receiverThread(Werkzeug ui, Socket socket) {
        super();
        this._ui = ui;
        this._socket = socket;
    }

    @Override
    public void run() {

        try {
            /* Socket-Basisstreams durch spezielle Streams filtern */
            outToServer = new DataOutputStream(_socket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(
                    _socket.getInputStream()));

            while (!this.isInterrupted()) {
                sentence = readFromServer();
                if(sentence == null) {
                    this.interrupt();
                }
                else{
                    _ui.writeInChatArea(sentence);
                    System.out.println("TCP Client got from Server: " + sentence);
                }
            }
            /* Socket-Streams schliessen --> Verbindungsabbau */
            //_socket.close();
        } catch (IOException e) {
            System.err.println("Connection aborted by server!");
        }
        System.out.println("TCP Client stopped!");
    }
    private String readFromServer() throws IOException {
         /* Lies die Antwort (reply) vom Server */
        String reply = inFromServer.readLine();

        return reply;
    }
}