package de.haw.vs.escr.dice.service;

import static com.jayway.restassured.RestAssured.given;
import static spark.Spark.*;

import com.google.gson.Gson;
import com.jayway.restassured.response.Response;
import de.haw.vs.escr.dice.model.Dice;
import de.haw.vs.escr.dice.dto.Event;
import de.haw.vs.escr.dice.util.Util;

import java.net.URI;

/**
 * Created by Christian on 04.04.2016.
 */
public class DiceService {
    private Gson gson = new Gson();
    private Dice dice;

    public DiceService() {
        get("/dice", (req, res) -> {
            String game = req.queryParams("game");
            String player = req.queryParams("player");
            String resource = req.queryParams("resource");
            URI gameUri = null;
            URI playerUri = null;
            URI resourceUri = null;

            if (game == null || player == null || resource == null || !Util.validateURI(game) || !Util.validateURI(player) || !Util.validateURI(resource)) {
                res.status(200);
                dice = new Dice();
                dice.rollDice();
                res.type("application/json");
                return gson.toJson(dice);
            }

            gameUri = Util.uriFromString(game);
            playerUri = Util.uriFromString(player);
            resourceUri = Util.uriFromString(resource);

            dice = new Dice();
            dice.rollDice();

            Event diceEvent = new Event("/game/1", gameUri, "roll dice", "roll dice", "it was players turn to roll the dice",
                    resourceUri, playerUri);
            Response response = this.sendEventToGame(diceEvent);

            if (response.getStatusCode() == 201) res.status(200);
            else res.status(500);

            res.type("application/json");
            return gson.toJson(dice);
        });
      }

    private Response sendEventToGame(Event diceEvent) {
        return
                given().
                        queryParam("game", diceEvent.getGame().toString()).
                        queryParam("type", diceEvent.getType().toString()).
                        queryParam("name", diceEvent.getName().toString()).
                        queryParam("reason", diceEvent.getReason().toString()).
                        queryParam("player", diceEvent.getPlayer().toString()).
                when().
                        post("http://172.18.0.85:4567/events");
    }
}
