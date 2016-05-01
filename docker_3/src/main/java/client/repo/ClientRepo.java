package client.repo;

import client.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.*;

/**
 * Created by jana on 30.04.16.
 */

public class ClientRepo {
   private EntityManager entityManager = Persistence.createEntityManagerFactory("event").createEntityManager();

    public List<Event> allEvents() {
        try {
            entityManager.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<Event> events = entityManager.createQuery("select e from Event e").getResultList();
            entityManager.getTransaction().commit();
            System.out.println("events bei allEvents"+events.get(0).getReason());
            return events;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

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

    public List<Event> findEventByAttributes(String gameRegex, String typeRegex, String nameRegex,
                                             String reasonRegex, String resourceRegex, String playerRegex){
        System.out.println("findEventsBy.. " + this.allEvents().get(0).getReason());
//        List<Event> allEvents = this.allEvents();
        try {
            List<Event> allEvents = this.allEvents();
            List<Event> eventsWithRequestedAttributes= new ArrayList();
            System.out.println("events bei findEventList" + allEvents.get(0).getReason());
            for (Event e : allEvents) {
                System.out.println("in der for.." + e.getReason());
                if(EventMatcher.matchesGame(e, gameRegex)
                        && EventMatcher.matchesType(e, typeRegex)
                        && EventMatcher.matchesName(e, nameRegex)
                        && EventMatcher.matchesReason(e, reasonRegex)
                        && EventMatcher.matchesResourceOrIsNull(e, resourceRegex)
                        && EventMatcher.matchesPlayerOrIsNull(e, playerRegex))
                    eventsWithRequestedAttributes.add(e);
                }
            return eventsWithRequestedAttributes;
        }catch (Exception e) {
            return null;
        }
    }

    public Event saveEvent(Event event) {
        try {
            entityManager.getTransaction().begin();
            event = entityManager.merge(event);
            entityManager.getTransaction().commit();
            System.out.println("Event wurde gespeichert");
            return event;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("Event konnte nicht gespeichert werden");
            return null;
        }
    }

    public void deleteEvent(Event event) {
        try {
            entityManager.getTransaction().begin();
            System.out.println("vor dem Löschen: " + event);
            entityManager.remove(event);
            System.out.println("repo: event wurde gelöscht");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }
}
