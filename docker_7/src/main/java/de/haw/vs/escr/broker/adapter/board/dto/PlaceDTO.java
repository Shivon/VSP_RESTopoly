package de.haw.vs.escr.broker.adapter.board.dto;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 20.06.2016.
 */
public class PlaceDTO {
    @Expose
    private String place;

    @Expose
    private int value;

    @Expose
    private List<Integer> rent;

    @Expose
    private List<Integer> cost;

    public PlaceDTO() {
        this.rent = new ArrayList<>();
        this.cost = new ArrayList<>();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Integer> getRent() {
        return rent;
    }

    public void setRent(List<Integer> rent) {
        this.rent = rent;
    }

    public List<Integer> getCost() {
        return cost;
    }

    public void setCost(List<Integer> cost) {
        this.cost = cost;
    }
}
