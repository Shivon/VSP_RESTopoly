package client.logic;

import client.adapter.IPAdresses;
import client.adapter.UserAdapter;
import client.model.Client;
import client.model.User;
import client.model.gameModels.Game;
import client.view.GamesWindow;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 08.06.2016.
 */
public class UserLogic {

    private UserAdapter _userAdapter;
    private IPAdresses _ipAdresses;
    private User _user;

    public UserLogic(UserAdapter userAdapter, IPAdresses ipAdresses){
        _userAdapter = userAdapter;
        _ipAdresses = ipAdresses;
    }

    public User getUser(String userName) throws UnirestException {
        return _userAdapter.getUser(userName.toLowerCase());
    }

    public boolean checkIfUserAlreadyExists(String userName) throws UnirestException {
        System.out.println(_userAdapter.getUsers().getUsers());
        for (String user : _userAdapter.getUsers().getUsers()) {
            if(user.equals(_ipAdresses.usersIP() + "/" + userName)){
                return true;
            }
        }
        return false;
    }

    public User getCurrentUser() {
        return _user;
    }

    public String setCurrentUser(String userName) throws UnirestException {
        Client client = new Client();
        _user = new User();
        client.setName(userName);
        _user.setName(userName);
        _user.setId("/users/" + userName.toLowerCase());
        client.setUri(_ipAdresses.clientIP() + "/client/" + userName.toLowerCase());
        _user.setUri(_ipAdresses.clientIP() + "/client/" + userName.toLowerCase());
       return  _userAdapter.postUser(_user);
    }
}
