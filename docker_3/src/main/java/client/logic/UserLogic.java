package client.logic;

import client.adapter.IPAdresses;
import client.adapter.UserAdapter;
import client.model.Client;
import client.model.User;
import client.service.ClientService;
import client.view.GamesWindow;
import clientUI.UserWindowUI;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 08.06.2016.
 */
public class UserLogic {

    private UserWindowUI _userWindowUI;
    private String _userName;
    private User _user;
    private UserAdapter _userAdapter;
    private GamesWindow _gamesWindow;
    private Client _client;
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

        System.out.println( _userAdapter.getUsers());
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
        _user = new User();
        _client = new Client();
        _userName = userName;
        _client.setName(_userName);
        _user.setName(_userName);
        _user.setId("/users/" + _userName.toLowerCase());
        System.out.println("USER NAME : " + _userName);
        System.out.println("BANKIP: " + _ipAdresses.banksIP());
        System.out.println("IP: " + _ipAdresses.clientIP() );
        _client.setUri("http://" + _ipAdresses.clientIP() + "/client/" + _userName.toLowerCase());
        _user.setUri("http://" + _ipAdresses.clientIP() + "/client/" + _userName.toLowerCase());
        _userAdapter.postUser( _user );
    }

    public void closeUserUI(){
        _userWindowUI.getLogInFrame().setVisible(false);
    }

    public void openGamesWindow(User user) throws UnirestException {
        _gamesWindow = new GamesWindow(user);
    }
}
