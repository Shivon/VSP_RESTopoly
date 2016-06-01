package de.haw.vs.escr.users.repos;

import de.haw.vs.escr.users.model.User;
import de.haw.vs.escr.users.util.PersistenceService;

import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Christian on 05.04.2016.
 */
public class UserRepo {
    private EntityManager entityManager = PersistenceService.getEntityManager();

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

    /*public User findUser(String userid) {
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.find(User.class, userid);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return null;
    }*/

    public User findUserByNameId(String nameId) {
        try {
            entityManager.getTransaction().begin();
            User user = entityManager.createQuery("from User where nameId = :val", User.class)
                    .setParameter("val", nameId).getSingleResult();
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
        return null;
    }
}
