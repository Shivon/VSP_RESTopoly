package client.model.boardModels;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 20.06.2016.
 */
public class Place {
    private int placeId;

    @Expose
    private String id;

    @Expose
    private String place;

    @Expose
    private String owner;

    @Expose
    private int value;

    @Expose
    private List<Integer> rent;

    @Expose
    private List<Integer> cost;

    @Expose
    private int houses;

    @Expose
    private String visit;

    @Expose
    private String hypocredit;

    @Expose
    private String name;

    @Expose
    private String brokerURI;

    private String uri;

    public Place() {
        this.rent = new ArrayList<>();
        this.cost = new ArrayList<>();
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getBrokerURI() {
        return brokerURI;
    }

    public void setBrokerURI(String brokerURI) {
        this.brokerURI = brokerURI;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public int getHouses() {
        return houses;
    }

    public void setHouses(int houses) {
        this.houses = houses;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getHypocredit() {
        return hypocredit;
    }

    public void setHypocredit(String hypocredit) {
        this.hypocredit = hypocredit;
    }
}
