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
        System.out.println(_userName);

        Unirest.put(_ipAdresses.usersIP() + _userName.toLowerCase()
                + "?name=" + _userName + "&uri=" + "http://"+_ipAdresses.usersIP()+"/client/" +
                _userName.toLowerCase());
    }
// Only String, JsonNode and InputStream are supported, or an ObjectMapper implementation is required.
    public List<User> getUsers() throws UnirestException {
        System.out.println("get useres");
        String users = Unirest.get("http://"+_ipAdresses.usersIP()+"/users").asString().getBody();
        System.out.println(users);
        Users usersObj = gson.fromJson(users, Users.class);
        return usersObj.getUsers();
    }

}
