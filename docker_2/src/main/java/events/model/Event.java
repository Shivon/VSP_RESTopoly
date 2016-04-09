package events.model;

import java.net.URI;
import java.util.UUID;

/**
 * Created by marjan on 07.04.16.
 */
@Entity
@Table(name = "Event")
public class Event {
    @Id
    @Column(name = "id")
    // @Expose on a field you're telling Gson to include that property into your JSON String
    @Expose(serialize = false)
    private String id;

    // required params
    @Column(name = "game")
    @Expose
    private URI game;

    @Column(name = "type")
    @Expose
    private String type;

    @Column(name = "name")
    @Expose
    private String name;

    @Column(name = "reason")
    @Expose
    private String reason;

    // optional params, can be null
    @Column(name = "resource")
    @Expose
    private URI resource;

    @Column(name = "player")
    @Expose
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

    public URI getGame() {
        return game;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }

    public URI getResource() {
        return resource;
    }

    public URI getPlayer() {
        return player;
    }
}
