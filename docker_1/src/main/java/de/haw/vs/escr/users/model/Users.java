package de.haw.vs.escr.users.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Christian on 05.04.2016.
 */
public class Users {
    @Expose
    private ArrayList<String> users;

    public Users() {
        users = new ArrayList<String>();
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void addUserToList(String uri) {
        this.users.add(uri);
    }
}
