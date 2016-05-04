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
