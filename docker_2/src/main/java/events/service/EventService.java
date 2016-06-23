package events.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;
import events.model.Event;
import events.model.Subscription;
import events.repo.EventRepo;

import java.net.URI;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static spark.Spark.*;


/**
 * Created by marjan on 07.04.16.
 */
public class EventService {
    private Gson gson = new Gson();
    private EventRepo eventRepo = new EventRepo();
    private Event event;
    private List<Event> eventList;

    public EventService() {
        get("/events/:eventId", (request, response) -> {
            event = eventRepo.findEvent(request.params(":eventId"));

            if (event == null) {
                response.status(500);
                response.type("application/json");
                return gson.toJson("Event not found");
            }

            String uriEvent = "/events/" + event.getId();

            JsonObject result = new JsonObject();
            result.addProperty("id", uriEvent);

            result.addProperty("game", event.getGame().toString());
            result.addProperty("type", event.getType());
            result.addProperty("name", event.getName());
            result.addProperty("reason", event.getReason());
            result.addProperty("resource", event.getResource().toString());
            result.addProperty("player", event.getPlayer().toString());

            response.status(200);
            response.type("application/json");
            return result;
        });

        get("/events", (request, response) -> {
            String game = request.queryParams("game");
            String type = request.queryParams("type");
            String name = request.queryParams("name");
            String reason = request.queryParams("reason");
            String resource = request.queryParams("resource");
            String player = request.queryParams("player");
            System.out.println(request.queryParams());
            System.out.println(reason);
            eventList = eventRepo.findEventByAttributes(game, type, name, reason, resource, player);

            System.out.println("event bei get ............." + eventList.get(0).getGame());
            System.out.println("events bei get" + eventList);

            if(eventList.isEmpty()){
                response.status(500);
                response.type("application/json");
                return "";
            }
            response.status(200);
            response.type("application/json");
            return gson.toJson(eventList);
        });

        post("/events", (request, response) -> {
            System.out.println("post /events");

            Event event = this.gson.fromJson(request.body(), Event.class);

            System.out.println(gson.toJson(event));
            event = eventRepo.saveEvent(event);

            if (event == null) {
                response.status(500);
                response.type("application/json");
                return gson.toJson("Event not saved");
            }

            response.status(201);

            List<Subscription> subscriptions = eventRepo.findSubscriptionsFor(event);
            for (Subscription subscription : subscriptions) {
                Response res = sendEventToSubscriber(subscription.getInterestedResource(), event);
                if (res.getStatusCode() != 201 || res.getStatusCode() != 200) {
                    response.status(500);
                }
            }

            String uriEvent = "/events/" + event.getId();

            JsonObject result = new JsonObject();
            result.addProperty("id", uriEvent);

            result.addProperty("game", event.getGame().toString());
            result.addProperty("type", event.getType());
            result.addProperty("name", event.getName());
            result.addProperty("reason", event.getReason());
            result.addProperty("resource", event.getResource().toString());
            result.addProperty("player", event.getPlayer().toString());

            response.type("application/json");
            return result;
        });

        delete("/events", (request, response) -> {
            String game = request.queryParams("game");
            String type = request.queryParams("type");
            String name = request.queryParams("name");
            String reason = request.queryParams("reason");
            String resource = request.queryParams("resource");
            String player = request.queryParams("player");
            eventList = eventRepo.findEventByAttributes(game, type, name, reason
            , resource, player);
            for (Event e : eventList) {
                System.out.println("bei delete: " +e);
                eventRepo.deleteEvent(e);
            }
            response.status(200);
            return gson.toJson("wurde gelöscht");
        });

        post("/events/subscriptions", (request, response) -> {
            Subscription subscription = gson.fromJson(request.body(), Subscription.class);

            subscription = eventRepo.saveSubscription(subscription);

            if (subscription == null) {
                response.status(500);
                response.type("application/json");
                return gson.toJson("Subscription not saved");
            }

            response.status(201);
            response.type("application/json");
            return gson.toJson(subscription);
        });
    }

    private Response sendEventToSubscriber(URI subscriber, Event event) {
        String uriEvent = "/events/" + event.getId();

        JsonObject result = new JsonObject();
        result.addProperty("id", uriEvent);

        result.addProperty("game", event.getGame().toString());
        result.addProperty("type", event.getType());
        result.addProperty("name", event.getName());
        result.addProperty("reason", event.getReason());
        result.addProperty("resource", event.getResource().toString());
        result.addProperty("player", event.getPlayer().toString());

        return given().body(result).post(subscriber);
    }
}
