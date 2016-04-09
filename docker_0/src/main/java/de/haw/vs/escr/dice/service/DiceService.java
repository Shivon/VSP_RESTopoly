package de.haw.vs.escr.dice.service;

import static spark.Spark.*;

import com.google.gson.Gson;
import de.haw.vs.escr.dice.model.Dice;
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
            //return req.queryParams("game") + ", " + req.queryParams("player");
            String game = req.queryParams("game");
            String player = req.queryParams("player");
            String resource = req.queryParams("resource");
            URI gameUri = null;
            URI playerUri = null;
            URI resourceUri = null;

            if (game != null && Util.validateURI(game)) gameUri = Util.uriFromString(game);
            if (player != null && Util.validateURI(player)) playerUri = Util.uriFromString(player);
            if (resource != null && Util.validateURI(resource)) resourceUri = Util.uriFromString(resource);

            dice = new Dice();
            dice.rollDice();
            Event diceEvent = new Event(gameUri, "roll dice", "roll dice", "it was palyers turn to roll the dice",
                    resourceUri, playerUri);
            response.status(200);
            response.type("application/json");
            return gson.toJson(dice);
        });
      }
}
