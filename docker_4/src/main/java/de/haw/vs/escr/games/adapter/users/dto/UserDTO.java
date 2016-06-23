package de.haw.vs.escr.games.adapter.users.dto;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 22.06.2016.
 */
public class UserDTO {
    @Expose
    private String id;

    @Expose
    private String name;

    @Expose
    private String uri;

    public UserDTO() {
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
