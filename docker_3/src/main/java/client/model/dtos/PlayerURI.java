package client.model.dtos;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian on 03.05.2016.
 */
public class PlayerURI {
    @Expose
    private List<String> players;

    public PlayerURI() {
        this.players = new ArrayList<>();
    }

    public PlayerURI(List<String> players) {
        this.players = players;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public void addURIToPlayers(String uri) {
        this.players.add(uri);
    }
}
