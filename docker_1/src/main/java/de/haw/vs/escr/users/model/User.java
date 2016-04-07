package de.haw.vs.escr.users.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.net.URI;

/**
 * Created by Christian on 05.04.2016.
 */
@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "id")
    @Expose(serialize = false)
    private String nameId;

    @Column(name = "userid")
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

    public User(String nameId, String id, String name, String uri) {
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
}
