package client.adapter;

import client.model.Client;
import client.model.User;
import client.model.Users;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Jana Mareike on 18.05.2016.
 */
public class UserAdapter {

    Gson gson = new Gson();
    private IPAdresses _ipAdresses;
    private Client _client;
    private User _user;

    public UserAdapter(){
        _ipAdresses = new IPAdresses();
    }

    public void putUser(String _userName) throws UnirestException {
        Unirest.put("http://"+_ipAdresses.usersIP()
                + "/users/" + _userName.toLowerCase()
                + "?name=" + _userName + "&uri=" + "http://192.168.255.174:4567:4567/client/" +
                _userName.toLowerCase()).asString().getBody();
//             Unirest.put(_ipAdresses.usersIP()
//        + "/" + _userName.toLowerCase()
//                + "?name=" + _userName + "&uri=" + "http://" + _ipAdresses.clientIP + "/client/" +
//                _userName.toLowerCase()).asString().getBody();
    }
// Only String, JsonNode and InputStream are supported, or an ObjectMapper implementation is required.
    public User getUsers() throws UnirestException {
        String users = Unirest.get("http://"+_ipAdresses.usersIP()+"/users").asString().getBody();
//        String users = Unirest.get(_ipAdresses.usersIP()).asString().getBody();
        System.out.println("users im getUsers:" + users);
        User usersObj = gson.fromJson(users, User.class);
        return usersObj;
    }

    public User getUser(String userName) throws UnirestException {
        System.out.println("Username im getUser: " +userName);
        String user = Unirest.get("http://"+_ipAdresses.usersIP()+"/users/"
                + userName.toLowerCase()).asString().getBody();
// String user = Unirest.get(_ipAdresses.usersIP() + "/"
//        + userName.toLowerCase()).asString().getBody();
        System.out.println("String...............\n" + user);

        User userObj = gson.fromJson(user, User.class);

        System.out.println("UserObject: " + userObj);
        return userObj;
    }

    public void postUser(User user) throws UnirestException {

        _user = user;
        String _userName = _user.getName();
        System.out.println( "im POST: " + _user + "  "
                + Unirest.post("http://"+_ipAdresses.usersIP()+ "/users")
                .body(this.gson.toJson(_user)).getBody());

        Unirest.post("http://"+_ipAdresses.usersIP()+ "/users")
                .body(this.gson.toJson(_user)).asJson();
//             Unirest.post(_ipAdresses.usersIP())
//        .body(this.gson.toJson(_user)).asJson();

        System.out.println("USER NACH POST: " + getUser(_userName.toLowerCase()).getName());
    }

}
