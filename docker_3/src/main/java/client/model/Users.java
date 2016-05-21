package client.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jana Mareike on 19.05.2016.
 */
public class Users {
    @Expose
    private List<User> users;

    public Users(List<User> users) {
        this.users = users;
    }

    public Users() {
        users = new ArrayList<User>();
    }

    public List<User> getUsers() {
        return users;
    }

}

