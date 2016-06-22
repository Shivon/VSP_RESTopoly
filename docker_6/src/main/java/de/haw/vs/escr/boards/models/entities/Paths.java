package de.haw.vs.escr.boards.models.entities;

import com.google.gson.annotations.Expose;

import java.util.UUID;

/**
 * Created by Christian on 29.04.2016.
 */
public class Paths {
    private String uuid;

    @Expose
    private String game = null;

    @Expose
    private String dice = null;

    @Expose
    private String board = null;

    @Expose
    private String bank = null;

    @Expose
    private String broker = null;

    @Expose
    private String decks = null;

    @Expose
    private String events = null;

    public Paths() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Paths(String game, String dice, String board, String bank, String broker, String decks, String events) {
        this.uuid = UUID.randomUUID().toString();
        this.game = game;
        this.dice = dice;
        this.board = board;
        this.bank = bank;
        this.broker = broker;
        this.decks = decks;
        this.events = events;
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
