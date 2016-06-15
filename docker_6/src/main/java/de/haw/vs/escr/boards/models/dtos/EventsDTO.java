package de.haw.vs.escr.boards.models.dtos;

import com.google.gson.annotations.Expose;
import de.haw.vs.escr.boards.models.entities.Event;

import java.util.List;

/**
 * Created by Eric on 10.06.2016.
 */
public class EventsDTO {
    @Expose
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
