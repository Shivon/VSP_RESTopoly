package de.haw.vs.escr.broker.adapter.games.dto;

import com.google.gson.annotations.Expose;

/**
 * Created by Christian on 21.06.2016.
 */
public class PathDTO {
    @Expose
    private String game;

    @Expose
    private String dice;

    @Expose
    private String board;

    @Expose
    private String bank;

    @Expose
    private String broker;

    @Expose
    private String decks;

    @Expose
    private String events;

    public PathDTO() {
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getDice() {
        return dice;
    }

    public void setDice(String dice) {
        this.dice = dice;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getDecks() {
        return decks;
    }

    public void setDecks(String decks) {
        this.decks = decks;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }
}
