package client.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Eric on 10.06.2016.
 */
public class Event {
    @Expose
    private String action;
    @Expose
    private String uri;
    private String id;
    private String game;
    private String type;
    private String name;
    private String reason;
    private String resource;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

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
}
