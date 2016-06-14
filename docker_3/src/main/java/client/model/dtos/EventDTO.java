package client.model.dtos;

import client.model.Event;
import com.google.gson.annotations.Expose;


/**
 * Created by Eric on 10.06.2016.
 */
public class EventDTO {
    @Expose
    private String action;
    @Expose
    private String uri;
    @Expose
    private String name;
    @Expose
    private String reason;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Event toEntity(){
        Event e = new Event();
        e.setName(this.name);
        e.setReason(this.reason);
        e.setUri(this.uri);
        return e;
    }
}
