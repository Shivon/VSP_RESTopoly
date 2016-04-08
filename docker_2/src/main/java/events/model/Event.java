package events.model;

import java.net.URI;
import java.util.UUID;

/**
 * Created by marjan on 07.04.16.
 */

public class Event {
    private String id;
    // required params
    private URI game;
    private String type;
    private String name;
    private String reason;
    // optional params, can be null
    private URI resource;
    private URI player;

    public Event(URI game, String type, String name, String reason, URI resource, URI player) {
        this.id = UUID.randomUUID().toString();
        this.game = game;
        this.type = type;
        this.name = name;
        this.reason = reason;
        this.resource = resource;
        this.player = player;
    }
}
