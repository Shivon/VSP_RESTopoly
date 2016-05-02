package events.service;

import com.google.gson.Gson;
import events.model.Event;
import events.repo.EventRepo;

import java.net.URI;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by jana on 030.04.16.
 */
public class ClientService {
    private Gson gson = new Gson();
    private ClientRepo clientRepo = new ClientRepo();
    private Client client;
    private List<Client> clientList;

    public ClientService() {

        get("/client", (request, response) -> {
//            URl: /client A service which acts as a representant of a player/client.
//            The endpoint may be found at any other uri, but it must offer the described mehtods
//            and the full uri must be submitted when registering a player
//            gets the details about the player
            String id = request.queryParams("id");
            String name = request.queryParams("name");
            String uri = request.queryParams("uri");

            client = clientRepo.findClient(id);

            if (client == null) {
                response.status(500);
                response.type("application/json");
                return "";
            }

            response.status(200);
            response.type("application/json");
            return gson.toJson(client);
        });

        post("/client/turn", (request, response) -> {
//            Informs the player, that it is his turn

        });

       post("/client/event", (request, response) -> {
//           inform a player about a new event

       });
    }
}
