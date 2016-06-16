package client.adapter;

import client.model.User;
import client.model.Users;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 18.05.2016.
 */
public class UserAdapter {

    Gson gson = new Gson();
    private IPAdresses _ipAdresses;

    public UserAdapter(){
        _ipAdresses = new IPAdresses();
    }

    public void putUser(String _userName) throws UnirestException {
             Unirest.put(_ipAdresses.usersIP()
                 + "/" + _userName.toLowerCase()
                + "?name=" + _userName + "&uri=" + "http://" + _ipAdresses.clientIP() + "/client/" +
                _userName.toLowerCase()).asString().getBody();
    }
// Only String, JsonNode and InputStream are supported, or an ObjectMapper implementation is required.
    public Users getUsers() throws UnirestException {
        String users = Unirest.get(_ipAdresses.usersIP()).asString().getBody();
        Users usersObj = gson.fromJson(users, Users.class);
        return usersObj;
    }

    public User getUser(String userName) throws UnirestException {
        String user = Unirest.get(_ipAdresses.usersIP() + "/"
                + userName.toLowerCase()).asString().getBody();

        User userObj = gson.fromJson(user, User.class);
        return userObj;
    }

    public void postUser(User user) throws UnirestException {
             Unirest.post(_ipAdresses.usersIP())
                .body(this.gson.toJson(user)).asJson().getBody();
    }
}
