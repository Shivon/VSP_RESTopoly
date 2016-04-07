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
        user.setId(id);
        return userRepo.saveUser(user);
    }

    public User getUser(String userid) {
        return userRepo.findUser(userid);
    }

    public void deleteUser(String userid) {
        User user = userRepo.findUser(userid);
        if (user != null) userRepo.deleteUser(user);
    }

    public void putUser(String userid, String name, String uri) {
        User u = userRepo.findUser(userid);
        if (name != null) u.setName(name);
        if (uri != null) u.setUri(uri);
        userRepo.saveUser(u);
    }
}
