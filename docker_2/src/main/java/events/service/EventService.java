package events.service;

import com.google.gson.Gson;
import com.jayway.restassured.response.Response;
import events.model.Event;
import events.model.Subscription;
import events.repo.EventRepo;

import java.net.URI;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static spark.Spark.*;

//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by marjan on 07.04.16.
 */
public class EventService {
    private Gson gson = new Gson();
    private EventRepo eventRepo = new EventRepo();
    private Event event;
    private List<Event> eventList;

    public EventService() {
        // "id": { "type":"string", "required":true, "description":"the url to the event on the event server"},
        // "game": {"type":"string", "required":true, "description":"the uri of the game this event belongs to"},
        // "type": { "type": "string", "required": true , "description":"internal type of the event (e.g bank transfer, rent, got to jail, estate transfer)" },
        // "name": { "type": "string", "required": true, "description":"human readable name for this event"  },
        // "reason": { "type": "string", "required": true, "description":"a description why this event occured"  },
        // "resource": {"type": "string", "description": "the uri of the resource related to this event where more information may be found (e.g. an uri to a transfer or similar)" },
        // "player": { "type": "string", "description": "The uri of the player having triggered the event" }

        get("/events/:eventId", (request, response) -> {
            event = eventRepo.findEvent(request.params(":eventId"));

            if (event == null) {
                response.status(500);
                response.type("application/json");
                return "";
            }

            response.status(200);
            response.type("application/json");
            return gson.toJson(event);
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
            // list = repo.findEventsByGame(game);
            //

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
            System.out.println("post");

//            Event event = this.gson.fromJson(request.body().toString(), Event.class);
//
//            if(event.getGame().equals("") || event.getName().equals("") ||
//                    event.getType().equals("") || event.getReason().equals("")){
//                response.status(500);
//                response.type("application/json");
//                return "Param(s) missing, require \"game\", \"type\", \"name\", \"reason\"";
//            }
//            String id = "/events/" + event.getName().toLowerCase();
//            event.setId(id);
            String[] requiredParams = {"game", "type", "name", "reason"};
            for (int i = 0; i < requiredParams.length; i++) {
                if (request.queryParams(requiredParams[i]) == null) {
                    response.status(422);
                    response.type("application/json");
                    return gson.toJson("Param(s) missing, require \"game\", \"type\", \"name\", \"reason\"");
                }
            }
            String game = request.queryParams("game");
            String type = request.queryParams("type").toLowerCase();
            String name = request.queryParams("name").toLowerCase();
            String reason = request.queryParams("reason").toLowerCase();

            URI gameUri = new URI(game);

            // Optional params
            String resource = request.queryParams("resource");
            String player = request.queryParams("player");
            URI resourceUri = null;
            URI playerUri = null;

            if (resource != null) { resourceUri = new URI(resource); }
            if (player != null) { playerUri = new URI(player); }

            event = new Event(gameUri, type, name, reason, resourceUri, playerUri);
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

            response.type("application/json");
            return gson.toJson(event);
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
            URI gameUri = new URI(request.queryParams("game"));
            URI resourceUri = new URI(request.queryParams("uri"));
            String eventType = request.queryParams("event").toLowerCase();

            Subscription subscription = new Subscription(gameUri, resourceUri, eventType);
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
        return
                given().
                        queryParam("id", "/events/" + event.getId()).
                        queryParam("game", event.getGame().toString()).
                        queryParam("type", event.getType()).
                        queryParam("name", event.getName()).
                        queryParam("reason", event.getReason()).
                        queryParam("resource", event.getResource().toString()).
                        queryParam("player", event.getPlayer().toString()).
                when().
                        post(subscriber);
    }
}
