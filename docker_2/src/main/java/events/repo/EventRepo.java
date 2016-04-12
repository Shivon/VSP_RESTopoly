package events.repo;

import events.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.*;

import static events.repo.EventMatcher.*;
/**
 * Created by marjan on 07.04.16.
 */

public class EventRepo {
    private EntityManager entityManager = Persistence.createEntityManagerFactory("event").createEntityManager();

    // get /events => find by attribute
    // get /event/:id => find by id
    // post /event => save
    // delete /event => delete

    public List<Event> allEvents() {
        try {
            entityManager.getTransaction().begin();
            List<Event> events = entityManager.createQuery("Select e from Event e").getResultList();
            entityManager.getTransaction().commit();
            return events;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Event findEvent(String id) {
        try {
            entityManager.getTransaction().begin();
            Event event = entityManager.find(Event.class, id);
            entityManager.getTransaction().commit();
            return event;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public List<Event> findEventByAttributes(String gameRegex, String type, String name, String reason, String resource, String player){
        try {
            entityManager.getTransaction().begin();
            List<Event> eventsWithRequestedAttributes= new ArrayList();
            for (Event e : this.allEvents()) {
                if(matchesGame(e, gameRegex)
                        && matchesType(e, type)
                        && matchesName(e, name)
                        && matchesReason(e, reason)
                        && matchesResourceOrIsNull(e, resource)
                        && matchesPlayerOrIsNull(e, player))
                    eventsWithRequestedAttributes.add(e);
                }
            entityManager.getTransaction().commit();
            return eventsWithRequestedAttributes;
        }catch (Exception e) {
            entityManager.getTransaction().rollback();
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
            entityManager.remove(event);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }
}
