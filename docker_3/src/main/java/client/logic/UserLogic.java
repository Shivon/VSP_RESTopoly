package client.logic;

import client.adapter.IPAdresses;
import client.adapter.UserAdapter;
import client.model.Client;
import client.model.User;
import client.view.GamesWindow;
import clientUI.UserWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 08.06.2016.
 */
public class UserLogic {

    private UserWindowUI _userWindowUI;
    private UserAdapter _userAdapter;
    private IPAdresses _ipAdresses;

    public UserLogic(UserWindowUI userWindowUI, UserAdapter userAdapter){
        _userWindowUI = userWindowUI;
        _userAdapter = userAdapter;
        _ipAdresses = new IPAdresses();
    }

    public boolean isLoginAreaNotEmpty(){
        if(! _userWindowUI.getLogInArea().getText().isEmpty()){
            return true;
        }
        return false;
    }

    public String getUserNameFromLoginArea(){
        return _userWindowUI.getLogInArea().getText();
    }

    public User getUser(String userName) throws UnirestException {
        return _userAdapter.getUser(userName.toLowerCase());
    }

    public boolean checkIfUserAlreadyExists(String userName) throws UnirestException {
        for (String user : _userAdapter.getUsers().getUsers()) {
            if(user.equals(_ipAdresses.usersIP() + "/" + userName)){
                return true;
            }
        }
//        _user = getUser(userName);
//        if (_user != null){
//            return true;
//        }
        return false;
    }

    public void setUser( String userName) throws UnirestException {
        User user = new User();
        Client client = new Client();
        client.setName(userName);
        user.setName(userName);
        user.setId("/users/" + userName.toLowerCase());
        client.setUri("http://" + _ipAdresses.clientIP() + "/client/" + userName.toLowerCase());
        user.setUri("http://" + _ipAdresses.clientIP() + "/client/" + userName.toLowerCase());
        _userAdapter.postUser( user );
    }

    public void closeUserUI(){
        _userWindowUI.getLogInFrame().setVisible(false);
    }

    public void openGamesWindow(User user) throws UnirestException {
        new GamesWindow(user);
    }
}
