package client.adapter;

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

    public UserAdapter(){
        _ipAdresses = new IPAdresses();
    }

    public void putUser(String _userName) throws UnirestException {
//        System.out.println(_userName);
// TODO uri vom client
//        System.out.println("IM PUT"+ Unirest.put("http://"+_ipAdresses.usersIP()
//                + "/users/" + _userName.toLowerCase()
//                + "?name=" + _userName + "&uri=" + "http://192.168.255.174:4567/client/" +
//                _userName.toLowerCase()).asString().getBody());

        Unirest.put("http://"+_ipAdresses.usersIP()
                + "/users/" + _userName.toLowerCase()
                + "?name=" + _userName + "&uri=" + "http://192.168.255.174:4567:4567/client/" +
                _userName.toLowerCase()).asString().getBody();
    }
// Only String, JsonNode and InputStream are supported, or an ObjectMapper implementation is required.
    public User getUsers() throws UnirestException {
//        System.out.println("get users");
        String users = Unirest.get("http://"+_ipAdresses.usersIP()+"/users").asString().getBody();
        System.out.println("users im getUsers:" + users);
        User usersObj = gson.fromJson(users, User.class);
//        System.out.println("UsersObj: " + usersObj);
        return usersObj;
    }

    public User getUser(String userName) throws UnirestException {
        System.out.println("Username im getUser: " +userName);
        String user = Unirest.get("http://"+_ipAdresses.usersIP()+"/users/"
                + userName.toLowerCase()).asString().getBody();

        System.out.println("String...............\n" + user);

        User userObj = gson.fromJson(user, User.class);

        System.out.println("UserObject: " + userObj);
        return userObj;
    }

    public void postUser(String _userName) throws UnirestException {

        User user = new User();
        user.setName(_userName);
        user.setNameId("/users/" + _userName.toLowerCase());
        user.setUri("http://somehost:4567/client/" + _userName.toLowerCase());
        System.out.println( "im POST: " + user + "  "
                + Unirest.post("http://"+_ipAdresses.usersIP()+ "/users")
                .body(this.gson.toJson(user)).getBody());

        Unirest.post("http://"+_ipAdresses.usersIP()+ "/users")
                .body(this.gson.toJson(user)).asJson();

        System.out.println("USER NACH POST: " + getUser(_userName.toLowerCase()).getName());
//        System.out.println("USERS NACH POST: " + getUsers());

    }

}
