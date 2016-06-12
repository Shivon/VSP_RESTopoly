package de.haw.vs.escr.boards.models.entities;

import com.google.gson.annotations.Expose;

/**
 * Created by Eric on 25.05.2016.
 */
//@Entity
//@Table(name = "Roll")
public class Roll {

    @Expose
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
