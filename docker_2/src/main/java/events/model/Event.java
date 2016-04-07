package events.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.net.URI;
import java.util.UUID;

/**
 * Created by marjan on 07.04.16.
 */

@Entity
@Table(name = "Event")
public class Event {
    private UUID id;
    // required params
    private URI game;
    private String type;
    private String name;
    private String reason;
    // optional params, can be null
    private URI resource;
    private URI player;

    public Event(URI game, String type, String name, String reason, URI resource, URI player) {
        this.id = UUID.randomUUID();
        this.game = game;
        this.type = type;
        this.name = name;
        this.reason = reason;
        this.resource = resource;
        this.player = player;
    }
}
