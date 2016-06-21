package client.model.boardModels;

import com.google.gson.annotations.Expose;

/**
 * Created by Eric on 25.05.2016.
 */
public class Place {
    int placeId;

    @Expose
    private String name;

    @Expose
    private String brokerURI;

    private String uri;

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
