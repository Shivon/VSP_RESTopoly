package client.service;

import client.adapter.UserAdapter;
import client.model.gameModels.Player;
import client.logic.WaitLogic;
import com.google.gson.Gson;
import client.model.Client;
import client.repo.ClientRepo;

import java.awt.*;

import static spark.Spark.*;

/**
 * Created by jana on 030.04.16.
 */
public class ClientService {
    private Gson gson = new Gson();
    private ClientRepo clientRepo = new ClientRepo();
    private Client client;

    public ClientService(WaitLogic waitLogic) {

        get("/client", (request, response) -> {
//            URl: /client A service which acts as a representant of a player/client.
//            The endpoint may be found at any other uri, but it must offer the described mehtods
//            and the full uri must be submitted when registering a player
//            gets the details about the player
            String id = request.queryParams("id");

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
            // würfel fenster geht auf
            // status vom user verändert sich
            // text anzeige ändert sich
            // logic.
            Player player = gson.fromJson(request.body(), Player.class);
            waitLogic.playerTurn(player);

            return "";
        });

       post("/client/event", (request, response) -> {
//           TODO
//           inform a player about a new event
           Event event = gson.fromJson(request.body(), Event.class);
           waitLogic.registerAndShowEvent(event);
           return "";
       });
    }
}
