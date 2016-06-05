package de.haw.vs.escr.users.repos;

import de.haw.vs.escr.users.model.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 05.04.2016.
 */
public class UserRepo {
    private List<User> userList;

    public UserRepo() {
        this.userList = new ArrayList<>();
    }

    public User saveUser(User user) {
        if (this.userList.stream().anyMatch(u -> u.getUuid().equals(user.getUuid()))) return this.updateUser(user);
        this.userList.add(user);
        return this.userList.stream().filter(u -> u.getUuid().equals(user.getUuid())).findFirst().get();
    }

    private User updateUser(User user) {
        this.deleteUser(user);
        return this.saveUser(user);
    }

    public List<User> listUser() {
        return this.userList;
    }

    public void deleteUser(User user) {
        this.userList.removeIf(u -> u.getUuid().equals(user.getUuid()));
    }

    public User findUserByNameId(String nameId) {
        return this.userList.stream().filter(u -> u.getNameId().equals(nameId)).findFirst().get();
    }

    /*private EntityManager entityManager = PersistenceService.getEntityManager();

    public User saveUser(User user) {
        try {
            entityManager.getTransaction().begin();
            user = entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return user;
    }

    public List<User> listUser() {
        try {
            entityManager.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<User> users = entityManager.createQuery("select u from User u").getResultList();
            entityManager.getTransaction().commit();
            return users;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return null;
    }

    public void deleteUser(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public User findUser(String userid) {
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, userid);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return null;
    }

    public User findUserByNameId(String nameId) {
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.createQuery("from User where nameId = :val", User.class).setParameter("val", nameId).getSingleResult();
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return null;
    }*/
}
