package de.haw.vs.escr.boards.models.dtos;

import com.google.gson.annotations.Expose;

/**
 * Created by Eric on 22.06.2016.
 */
public class ReadyDTO {
    @Expose
    private boolean ready;

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
