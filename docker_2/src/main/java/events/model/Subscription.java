package events.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URI;
import java.util.UUID;

/**
 * Created by marjan on 22.06.16.
 */
@Entity
@Table(name = "Subscription")
public class Subscription {
    @Id
    @Column(name = "id")
    // @Expose on a field you're telling Gson to include that property into your JSON String
    @Expose(serialize = false)
    private UUID id;

    @Column(name = "game")
    @Expose
    private URI game;

    @Column(name = "uri")
    @Expose
    private URI interestedResource;

    @Column(name = "event")
    @Expose
    private String eventType;

    public Subscription() {}

    public Subscription(URI game, URI resource, String eventType) {
        this.id = UUID.randomUUID();
        this.game = game;
        this.interestedResource = resource;
        this.eventType = eventType;
    }

    public UUID getId() {
        return id;
    }

    public URI getGame() {
        return game;
    }

    public void setGame(URI game) {
        this.game = game;
    }

    public URI getInterestedResource() {
        return interestedResource;
    }

    public void setInterestedResource(URI interestedResource) {
        this.interestedResource = interestedResource;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription)) return false;

        Subscription that = (Subscription) o;

        if (!getId().equals(that.getId())) return false;
        if (!getGame().equals(that.getGame())) return false;
        if (!getInterestedResource().equals(that.getInterestedResource())) return false;
        return getEventType().equals(that.getEventType());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getGame().hashCode();
        result = 31 * result + getInterestedResource().hashCode();
        result = 31 * result + getEventType().hashCode();
        return result;
    }
}
