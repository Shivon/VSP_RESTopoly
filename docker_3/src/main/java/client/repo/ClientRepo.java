package client.repo;

import client.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.*;

/**
 * Created by jana on 30.04.16.
 */

public class ClientRepo {
   private EntityManager entityManager = Persistence.createEntityManagerFactory("client").createEntityManager();

    public Client findClient(String id) {
        try {
            entityManager.getTransaction().begin();
            Client client = entityManager.find(Client.class, id);
            entityManager.getTransaction().commit();
            return client;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }


    public Client saveClient(Client client) {
        try {
            entityManager.getTransaction().begin();
            client = entityManager.merge(client);
            entityManager.getTransaction().commit();
            System.out.println("Client wurde gespeichert");
            return client;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("Client konnte nicht gespeichert werden");
            return null;
        }
    }

    public void deleteClient(Client client) {
        try {
            entityManager.getTransaction().begin();
            System.out.println("vor dem Löschen: " + client);
            entityManager.remove(client);
            System.out.println("repo: client wurde gelöscht");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }
}
