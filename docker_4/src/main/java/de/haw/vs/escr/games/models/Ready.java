package de.haw.vs.escr.games.models;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

/**
 * Created by Christian on 03.05.2016.
 */
@Entity
@Table(name = "Ready")
public class Ready {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ready")
    @Expose
    private boolean ready;

    public Ready() {
        this.ready = false;
    }

    public Ready(boolean ready) {
        this.ready = ready;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
