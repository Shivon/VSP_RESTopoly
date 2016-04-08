package events.service;

import com.google.gson.Gson;
import events.model.Event;
import events.repo.EventRepo;

import java.net.URI;
import java.util.UUID;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by marjan on 07.04.16.
 */
public class EventService {
    private Gson gson = new Gson();
    private EventRepo eventRepo = new EventRepo();
    private Event event;

    public EventService() {
        // "id": { "type":"string", "required":true, "description":"the url to the event on the event server"},
        // "game": {"type":"string", "required":true, "description":"the uri of the game this event belongs to"},
        // "type": { "type": "string", "required": true , "description":"internal type of the event (e.g bank transfer, rent, got to jail, estate transfer)" },
        // "name": { "type": "string", "required": true, "description":"human readable name for this event"  },
        // "reason": { "type": "string", "required": true, "description":"a description why this event occured"  },
        // "resource": {"type": "string", "description": "the uri of the resource related to this event where more information may be found (e.g. an uri to a transfer or similar)" },
        // "player": { "type": "string", "description": "The uri of the player having triggered the event" }

        get("/events/:eventId", (request, response) -> {
            UUID eventUuid;
            try {
                eventUuid = UUID.fromString(request.params(":eventId"));
            } catch (IllegalArgumentException e) {
                response.status(400);
                response.type("application/json");
                return gson.toJson("Invalid eventId");
            }

            event = eventRepo.findEvent(eventUuid);

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
            // TODO: implement
            return null;
        });

        post("/events", (request, response) -> {
            // Required params
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
            event = eventRepo.saveEvent(event);

            if (event == null) {
                response.status(500);
                response.type("application/json");
                return "";
            }

            response.status(201);
            response.type("application/json");
            return gson.toJson(event);
        });

//        delete("/events", (request, response) -> {
//
//        });
    }
}
