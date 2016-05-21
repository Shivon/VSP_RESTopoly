package client.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by Jana Mareike on 19.05.2016.
 */
@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "uuid")
    @Expose(serialize = false)
    private String uuid;

    @Column(name = "nameId", unique = true, nullable = false)
    @Expose(serialize = false)
    private String nameId;

    @Column(name = "userId")
    @Expose
    private String id;

    @Column(name = "name")
    @Expose
    private String name;

    @Column(name = "uri")
    @Expose
    private String uri;

    public User() {

    }

    public User(String uuid, String nameId, String id, String name, String uri) {
        this.uuid = uuid;
        this.nameId = nameId;
        this.id = id;
        this.name = name;
        this.uri = uri;
    }

    public String getNameId() {
        return nameId;
    }

    public void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean hasName(String name){
        if(getName().equals(name)){
            return true;
        }
        return false;
    }
}
