package de.haw.vs.escr.users.businesslogic;

import de.haw.vs.escr.users.model.User;
import de.haw.vs.escr.users.model.Users;
import de.haw.vs.escr.users.repos.UserRepo;

import java.util.List;

/**
 * Created by Christian on 05.04.2016.
 */
public class UsersBusinesslogic {
    private UserRepo userRepo;

    public UsersBusinesslogic() {
        this.userRepo = new UserRepo();
    }

    public Users getUsers() {
        List<User> userList = userRepo.listUser();
        Users users = new Users();
        for (User user :
                userList) {
            users.addUserToList(user.getId());
        }
        return users;
    }

    public User saveUserAndAddId(User user) {
        String id = "/users/" + user.getName().toLowerCase();
        user.setNameId(user.getName().toLowerCase());
        //user.setId(id);
        return userRepo.saveUser(user);
    }

    public void postUserToGame(User user) {
        //TODO

    }

    public User getUser(String nameId) {
        return userRepo.findUserByNameId(nameId);
    }

    public void deleteUser(String nameId) {
        User user = userRepo.findUserByNameId(nameId);
        if (user != null) userRepo.deleteUser(user);
    }

    public User putUser(String nameId, String name, String uri) {
        User u = userRepo.findUserByNameId(nameId);
        if (name != null) u.setName(name);
        if (uri != null) u.setUri(uri);
        return userRepo.saveUser(u);
    }

    public boolean checkForExistingUser(String nameId) {
        return this.userRepo.userExists(nameId);
    }
}
