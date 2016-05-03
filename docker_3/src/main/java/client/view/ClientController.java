package client.view;

import org.omg.CORBA.DataOutputStream;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Jana Mareike on 03.05.2016.
 */
public class ClientController {

    private ClientView _view;
    private Socket _socket;
    private String _name;
    private DataOutputStream _outputStream;

    public ClientController() {
        _view = new ClientView();
//        _socket = socket;
//        _outputStream = new DataOutputStream(_socket.getOutputStream());
        registerSubmit();
    }

    public void registerSubmit(){
        System.out.println("hallo");
        _view.getLogInSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hallo");
//                TODO prüfen, ob name vergeben?
                if(! _view.getLogInArea().getText().isEmpty()){
                    _name = _view.getLogInArea().getText();
                    System.out.println("Name in der GUI: " + _view.getLogInArea().getText());
//                    TODO UserName speichern und übermitteln(?)
                    _view.getLogInFrame().setVisible(false);
                    _view.getMainFrame().setVisible(true);
                }else {
                    JOptionPane.showMessageDialog(null, "No user name", "no user name",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
