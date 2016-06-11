package de.haw.vs.escr.games.dtos;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 05.06.2016.
 */
public class GameUriDTO {
    @Expose
    private String game;

    public GameUriDTO() {
    }

    public GameUriDTO(String game) {
        this.game = game;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
