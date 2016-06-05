package de.haw.vs.escr.games.util.yellowpages.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 26.05.2016.
 */
public class Service {
    @Expose(serialize = false)
    private String _uri;

    @Expose
    private String description;

    @Expose
    private String name;

    @Expose
    private String service;

    @Expose(serialize = false)
    private String status;

    @Expose
    private String uri;

    public Service() {
    }

    public Service(String description, String service, String name, String uri) {
        this.description = description;
        this.service = service;
        this.name = name;
        this.uri = uri;
    }

    public String get_uri() {
        return _uri;
    }

    public void set_uri(String _uri) {
        this._uri = _uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service1 = (Service) o;

        if (!description.equals(service1.description)) return false;
        if (!name.equals(service1.name)) return false;
        return service.equals(service1.service);

    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + service.hashCode();
        return result;
    }
}
