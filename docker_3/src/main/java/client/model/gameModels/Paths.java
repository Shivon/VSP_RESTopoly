package client.model.gameModels;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by Christian on 29.04.2016.
 */
@Entity
@Table(name = "Paths")
public class Paths {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "game")
    @Expose
    private String game;

    @Column(name = "dice")
    @Expose
    private String dice;

    @Column(name = "board")
    @Expose
    private String board;

    @Column(name = "bank")
    @Expose
    private String bank;

    @Column(name = "broker")
    @Expose
    private String broker;

    @Column(name = "decks")
    @Expose
    private String decks;

    @Column(name = "events")
    @Expose
    private String events;

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
