package de.haw.vs.escr.boards.repos;

import de.haw.vs.escr.boards.models.entities.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 08.06.2016.
 */
public class PlaceRepo {
    private List<Place> placeList;

    public PlaceRepo(){
        this.placeList = new ArrayList<>();
    }

    public Place savePlace(Place place) {
        if (this.placeList.stream().anyMatch(p -> p.getUri().equals(place.getUri()))) return this.updatePlace(place);
        this.placeList.add(place);
        return this.placeList.stream().filter(b -> b.getPlaceId() == place.getPlaceId()).findFirst().get();
    }

    private Place updatePlace(Place place) {
        this.deletePlace(place);
        this.placeList.add(place);
        return place;
    }


    public void deletePlace(Place place) {
        this.placeList.removeIf((b -> b.getPlaceId() == place.getPlaceId()));
    }


    public List<Place> findAllPlaces() {
        return this.placeList;
    }
    
    
    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    public Place findPlaceById(int placeId) {
        return this.placeList.get(placeId);
        // return this.placeList.stream().filter(p -> p.getPlaceId() == placeId).findFirst().get();
    }

    public Place findPlaceByURI(String placeURI) {
       return this.placeList.stream().filter(p -> p.getUri().equals(placeURI)).findFirst().get();
    }
}
