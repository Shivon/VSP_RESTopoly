package de.haw.vs.escr.games.util.yellowpages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.response.Response;
import de.haw.vs.escr.games.util.inet.InetService;
import de.haw.vs.escr.games.util.yellowpages.model.Service;
import de.haw.vs.escr.games.util.yellowpages.model.ServiceList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by Christian on 26.05.2016.
 */
public class YellowPagesService implements IYellowPages {
    private static final Logger log = LoggerFactory.getLogger(YellowPagesService.class);
    private final String YELLOW_URL = "https://141.22.34.15/cnt/172.18.0.5/4567";
    private final String SERVICES_URL = YELLOW_URL + "/services";
    private final String OF_NAME_URL = SERVICES_URL + "/of/name";
    private final String OF_TYPE_URL = SERVICES_URL + "/of/type";
    private final String PORT = "4567";
    private final String PROTOCOL = "http://";
    private final String USER = "";
    private final String PW = "";
    private final Gson GSON;
    private String ownURL;
    private String uri;

    public YellowPagesService() {
        GsonBuilder gb = new GsonBuilder();
        gb.excludeFieldsWithoutExposeAnnotation();
        this.GSON = gb.create();
    }

    private void getAndSetOurURL() {
        try {
            log.info("Try to get IP...");
            InetAddress ia = InetService.getLocalHostLANAddress();
            log.info("IP is " + ia.getHostAddress());

            this.ownURL = this.PROTOCOL + ia.getHostAddress() + ":" + this.PORT;
            log.info("URL: " + this.ownURL);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String getUri() {
        return uri;
    }

    public String getURL() {
        if (ownURL == null) return null;
        return this.ownURL + this.uri;
    }

    @Override
    public Service registrateService(String uri, String name, String service, String description) {
        this.getAndSetOurURL();
        this.uri = uri;
        log.info("ServiceURL: " + this.getURL());

        Service s = new Service(description, service, name, this.getURL());
        Service existingService = this.findServiceByName(name);
        // if exists, delete
        while (existingService != null && s.equals(existingService)) {
            this.deleteService(existingService);
            existingService = this.findServiceByName(name);
        }

        // create
        String json = this.GSON.toJson(s);
        log.info("POST to Yellow Pages (" + this.SERVICES_URL + "): " + json);
        Response res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .authentication()
                .basic(USER, PW)
                .body(json)
                .post(this.SERVICES_URL);
        log.info("Response: " + res.body().asString());
        return this.GSON.fromJson(res.body().asString(), Service.class);
    }

    private void deleteService(Service existingService) {
        log.info("Delete Service: " + existingService.get_uri() + ", name: " + existingService.getName());
        String completeURL = this.YELLOW_URL + existingService.get_uri();
        given().relaxedHTTPSValidation()
                .contentType("application/json")
                .authentication()
                .basic(USER, PW)
                .delete(completeURL);
    }

    @Override
    public Service findServiceByName(String name) {
        ServiceList sl = this.findServicesByName(name);
        if (sl == null || sl.getServices().size() <= 0) return null;

        String serviceURI = sl.getFirstEntry();
        String completeURL = this.YELLOW_URL + serviceURI;
        log.info("Look up existing Services: " + completeURL);
        Response res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .authentication()
                .basic(USER, PW)
                .get(completeURL);
        String json = res.body().asString();
        log.info("Response: " + json);
        return this.GSON.fromJson(json, Service.class);
    }

    @Override
    public String findURIByServiceName(String name) {
        Service s = this.findServiceByName(name);
        if (s == null) return null;
        log.info("uri: " + s.getUri());
        return s.getUri();
    }

    private ServiceList findServicesByName(String name) {
        if (name == null) return null;
        ServiceList sl;

        String getURL = this.OF_NAME_URL + "/" + name;
        log.info("GET Service List from: " + getURL);
        Response res = given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .authentication()
                .basic(USER, PW)
                .get(getURL);
        log.info("Response: " + res.body().asString());

        sl = this.GSON.fromJson(res.body().asString(), ServiceList.class);
        return sl;
    }
}
