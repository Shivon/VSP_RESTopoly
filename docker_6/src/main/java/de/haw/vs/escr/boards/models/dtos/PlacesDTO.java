package de.haw.vs.escr.boards.models.dtos;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 08.06.2016.
 */
public class PlacesDTO {
    @Expose
    private List<String> places;

    public PlacesDTO(){
        this.places = new ArrayList<>();
    }

    public List<String> getPlaces() {
        return places;
    }

    public void setPlaces(List<String> places) {
        this.places = places;
    }

    public void addPlace(String placeURI){
        this.places.add(placeURI);
    }
}
