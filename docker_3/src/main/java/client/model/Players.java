package client.model;

import client.model.gameModels.Player;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jana Mareike on 24.05.2016.
 */
public class Players {

    @Expose
    private List<Player> players;

    public Players(List<Player> players) {
        this.players = players;
    }

    public Players() {
        players = new ArrayList<Player>();
    }

    public List<Player> getPlayers() {
        return players;
    }
}


