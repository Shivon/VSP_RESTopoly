package de.haw.vs.escr.boards.models;

import java.util.List;

/**
 * Created by Eric on 04.05.2016.
 */
public class Field {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    int fieldId;

    //@Column(name = "place")
    private Place place;

    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<String> players;

    public Field(){}

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

}
