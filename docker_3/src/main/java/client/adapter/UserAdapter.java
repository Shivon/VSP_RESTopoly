package client.adapter;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;

/**
 * Created by Jana Mareike on 18.05.2016.
 */
public class UserAdapter {

    public UserAdapter(){}

    public static void putUser(String _userName) throws UnirestException {
        Unirest.put("http://localhost:4567/users")
                .field("name", _userName)
                .asJson();
    }

    public static HttpResponse<ArrayList> getUsers() throws UnirestException {
        return  Unirest.get("http://localhost:4567/users")
                .asObject(ArrayList.class);
    }


}
