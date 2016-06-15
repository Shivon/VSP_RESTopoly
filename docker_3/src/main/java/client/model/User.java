package client.model;

import com.google.gson.annotations.Expose;

import java.util.UUID;

/**
 * Created by Christian on 05.04.2016.
 */
public class User {
    @Expose(serialize = false)
    private String uuid;

    @Expose(serialize = false)
    private String nameId;

    @Expose
    private String id;

    @Expose
    private String name;

    @Expose
    private String uri;
//
    public User() {
//        this.uuid = UUID.randomUUID().toString();
    }

    public User( String id, String name, String uri) {
//        this.uuid = UUID.randomUUID().toString();
//        this.nameId = nameId;
        this.id = id;
        this.name = name;
        this.uri = uri;
    }

//    public String getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(String uuid) {
//        this.uuid = uuid;
//    }
//
//    public String getNameId() {
//        return nameId;
//    }
//
//    public void setNameId(String nameId) {
//        this.nameId = nameId;
//    }

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
