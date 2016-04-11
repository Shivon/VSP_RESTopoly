package events.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
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

    // leerer Konstruktor notwendig, weil gson das braucht, sonst Probleme mit ID
    public Event(){
    }

    public Event(URI game, String type, String name, String reason, URI resource, URI player) {
        this.id = UUID.randomUUID().toString();
        this.game = game;
        this.type = type;
        this.name = name;
        this.reason = reason;
        this.resource = resource;
        this.player = player;
    }

    public String getId() {return id;}

    public URI getGame() {return game;}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != null ? !id.equals(event.id) : event.id != null) return false;
        if (!game.equals(event.game)) return false;
        if (!type.equals(event.type)) return false;
        if (!name.equals(event.name)) return false;
        if (!reason.equals(event.reason)) return false;
        if (resource != null ? !resource.equals(event.resource) : event.resource != null) return false;
        return player != null ? player.equals(event.player) : event.player == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + game.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + reason.hashCode();
        result = 31 * result + (resource != null ? resource.hashCode() : 0);
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }
}
