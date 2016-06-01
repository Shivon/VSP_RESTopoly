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
    private UsersBusinesslogic usersBL;

    public UserService() {
        GsonBuilder gb = new GsonBuilder();
        gb.excludeFieldsWithoutExposeAnnotation();
        gson = gb.create();
        this.usersBL = new UsersBusinesslogic();

        get("/users", (req, res) -> {
            Users users = usersBL.getUsers();

            res.status(200);
            res.type("application/json");
            return this.gson.toJson(users);
        });

        post("/users", (req, res) -> {
            User user = this.gson.fromJson(req.body().toString(), User.class);

            if (user.getUri().equals("") || user.getName().equals("")) {
                res.status(400);
                res.type("application/json");
                return "";
            }

            User savedUser = usersBL.saveUserAndAddId(user);

            res.status(201);
            res.type("application/json");
            return gson.toJson(savedUser);
        });

        get("/users/:userid", (req, res) -> {
            String userid = req.params(":userid");
            User user = usersBL.getUser(userid);

            if (user == null) {
                res.status(404);
                res.type("application/json");
                return "";
            }

            res.status(200);
            res.type("application/json");
            return gson.toJson(user);
        });

        put("/users/:userid", (req, res) -> {
            String userid = req.params(":userid");
            String name = req.queryParams("name");
            String uri = req.queryParams("uri");
            User savedUser = usersBL.putUser(userid, name, uri);

            res.status(201);
            res.type("application/json");
            return gson.toJson(savedUser);
        });

        delete("/users/:userid", (req, res) -> {
            String userid = req.params(":userid");
            usersBL.deleteUser(userid);

            res.status(200);
            res.type("application/json");
            return "";
        });
    }
}
