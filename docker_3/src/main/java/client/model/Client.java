package client.model;

//import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.annotations.Expose;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
//import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import sun.misc.IOUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.ws.Response;
import java.awt.print.Book;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Jana Mareike on 26.04.2016.
 */
@Entity
@Table(name = "Client")
public class Client {

    @Id
    @Column(name = "id")
    @Expose(serialize = false)
    private String id;

    @Column(name = "name")
    @Expose
    private String name;

    @Column(name = "uri")
    @Expose
    private String uri;

    // leerer Konstruktor notwendig, weil gson das braucht, sonst Probleme mit ID
    public Client() {
    }

    public Client(String name, String uri){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.uri = uri;

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

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != null ? !id.equals(client.id) : client.id != null) return false;
        if (!name.equals(client.name)) return false;
        return uri.equals(client.uri);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + uri.hashCode();
        return result;
    }
}
