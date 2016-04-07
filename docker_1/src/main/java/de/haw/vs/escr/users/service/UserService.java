package de.haw.vs.escr.users.service;

import static spark.Spark.*;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.haw.vs.escr.users.businesslogic.UsersBusinesslogic;
import de.haw.vs.escr.users.model.User;
import de.haw.vs.escr.users.model.Users;

import java.net.URI;

/**
 * Created by Christian on 05.04.2016.
 */
public class UserService {
    private Gson gson = new Gson();
    private UsersBusinesslogic usersBL = new UsersBusinesslogic();

    public UserService() {
        GsonBuilder gb = new GsonBuilder();
        gb.excludeFieldsWithoutExposeAnnotation();
        gson = gb.create();

        get("/users", (req, res) -> {
            Users users = usersBL.getUsers();
            return this.gson.toJson(users);
        });

        post("/users", (req, res) -> {
            User user = this.gson.fromJson(req.body().toString(), User.class);
            usersBL.saveUserAndAddId(user);
            res.status(201);
            return "";
        });

        get("/users/:userid", (req, res) -> {
            String userid = req.params(":userid");
            User user = usersBL.getUser(userid);
            return gson.toJson(user);
        });

        put("/users/:userid", (req, res) -> {
            String userid = req.params(":userid");
            String name = req.queryParams("name");
            String uri = req.queryParams("uri");
            usersBL.putUser(userid, name, uri);
            res.status(201);
            return "";
        });

        delete("/users/:userid", (req, res) -> {
            String userid = req.params(":userid");
            usersBL.deleteUser(userid);
            res.status(200);
            return "";
        });
    }
}
