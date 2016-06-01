package de.haw.vs.escr.users.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Christian on 05.04.2016.
 */
public class PersistenceService {
    private static final EntityManagerFactory entityManagerFactory;
    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("users");

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();

    }
}
