package client.model.dtos;



import client.model.Event;

import java.util.List;

/**
 * Created by Eric on 10.06.2016.
 */
public class EventsDTO {
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
