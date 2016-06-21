package client.model.boardModels;

import com.google.gson.annotations.Expose;
//import de.haw.vs.escr.boards.models.dtos.EventDTO;

/**
 * Created by Eric on 10.06.2016.
 */
public class Event {
    @Expose
    private String action;
    @Expose
    private String uri;
    @Expose
    private String id;
    @Expose
    private String game;
    @Expose
    private String type;
    @Expose
    private String name;
    @Expose
    private String reason;
    @Expose
    private String resource;

//    public EventDTO toDTO(){
//        EventDTO dto = new EventDTO();
//        dto.setName(this.name);
//        dto.setReason(this.reason);
//        dto.setAction(this.action);
//        dto.setUri(this.uri);
//        return dto;
//    }


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
