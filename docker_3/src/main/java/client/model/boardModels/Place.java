package client.model.boardModels;

import com.google.gson.annotations.Expose;

/**
 * Created by Eric on 25.05.2016.
 */
//@Entity
//@Table(name = "Place")
public class Place {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    int placeId;

    @Expose
    //@Column(name = "Name")
    String name;

    @Expose
   // @Column(name = "BrokerURI")
    String brokerURI;

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
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
}
