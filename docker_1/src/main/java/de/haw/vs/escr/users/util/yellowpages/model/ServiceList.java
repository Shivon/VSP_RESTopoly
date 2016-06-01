package de.haw.vs.escr.users.util.yellowpages.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 26.05.2016.
 */
public class ServiceList {
    @Expose
    List<String> services;

    public ServiceList() {
        this.services = new ArrayList<>();
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public String getFirstEntry() {
        return this.services.get(0);
    }
}
