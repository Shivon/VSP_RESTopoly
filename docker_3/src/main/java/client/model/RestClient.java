package client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import sun.misc.IOUtils;
import javax.xml.ws.Response;
import java.awt.print.Book;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jana Mareike on 26.04.2016.
 */

public class RestClient {

    // leerer Konstruktor notwendig, weil gson das braucht, sonst Probleme mit ID
    public RestClient() {
    }
//gui mit allen games erzeugen
    public List<Game> getAllGames() {
        // Unirest.get...
        // return games;
    }
//post auf user
}
