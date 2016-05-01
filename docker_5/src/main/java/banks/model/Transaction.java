package banks.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URI;
import java.util.UUID;

/**
 * Created by Jana Mareike on 01.05.2016.
 */
@Entity
@Table(name = "Transaction")
public class Transaction {

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
    public Transaction(){
    }

    public Transaction(URI game, String type, String name, String reason, URI resource, URI player) {
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

        Transaction that = (Transaction) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (!game.equals(that.game)) return false;
        if (!type.equals(that.type)) return false;
        if (!name.equals(that.name)) return false;
        if (!reason.equals(that.reason)) return false;
        if (!resource.equals(that.resource)) return false;
        return player.equals(that.player);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + game.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + reason.hashCode();
        result = 31 * result + resource.hashCode();
        result = 31 * result + player.hashCode();
        return result;
    }
}
