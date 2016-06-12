package de.haw.vs.escr.boards.models.dtos;

import java.util.List;

/**
 * Created by Eric on 10.06.2016.
 */
public class RollEventsDTO {
    private List<EventDTO> events;

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }
}
