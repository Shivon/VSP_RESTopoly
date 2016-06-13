package de.haw.vs.escr.users;

import de.haw.vs.escr.users.model.User;
import de.haw.vs.escr.users.repos.UserRepo;
import de.haw.vs.escr.users.service.UserService;
import de.haw.vs.escr.users.util.yellowpages.IYellowPages;
import de.haw.vs.escr.users.util.yellowpages.YellowPagesService;

import javax.jws.soap.SOAPBinding;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 05.04.2016.
 */
public class MyMainClass {
    public static void main(String[] args) {
        IYellowPages yp = new YellowPagesService();
        yp.registrateService("/users", "fancy_users", "fancy_users", "A fancy User Registration Service");
        new UserService();
    }
}
