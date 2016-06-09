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
    private String _userName;
//    private String _clientUri;
    private User _user;
    private UserAdapter _userAdapter;
    private GamesWindow _gamesWindow;
    private Client _client;
    private IPAdresses _ipAdresses;

    public UserLogic(UserWindowUI userWindowUI){

        _userWindowUI = userWindowUI;
        _userAdapter = new UserAdapter();
        _ipAdresses = new IPAdresses();
//        _client = new Client();
    }

    public boolean isLoginAreaNotEmpty(){
        if(! _userWindowUI.getLogInArea().getText().isEmpty()){
            return true;
        }
        return false;
    }

    public String getUserNameFromLoginArea(){
     return _userName = _userWindowUI.getLogInArea().getText();
    }

    public User getUser() throws UnirestException {
       return  _user = _userAdapter.getUser(_userName.toLowerCase());
    }

    public boolean checkIfUserAlreadyExists() throws UnirestException {
        _user = getUser();
        if (_user != null){
            return true;
        }
        return false;
    }

    public void postUser(String userName) throws UnirestException {
        _client = new Client();
        _user = new User();
        _userName = userName;
        _client.setName(_userName);
        _user.setName(_userName);
        _user.setNameId("/users/" + _userName.toLowerCase());
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
        _user = user;
        _gamesWindow = new GamesWindow(_user);
    }
}
