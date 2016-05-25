package de.haw.vs.escr.games.dtos;

import de.haw.vs.escr.games.models.Player;

/**
 * Created by Christian on 25.05.2016.
 */
public class PlayerDetailDTO {
    private Player player;
    private boolean hasAlreadyTurn;
    private boolean hasTurnNow;

    public PlayerDetailDTO() {
    }

    public PlayerDetailDTO(Player player, boolean hasAlreadyTurn, boolean hasTurnNow) {
        this.player = player;
        this.hasAlreadyTurn = hasAlreadyTurn;
        this.hasTurnNow = hasTurnNow;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isHasAlreadyTurn() {
        return hasAlreadyTurn;
    }

    public void setHasAlreadyTurn(boolean hasAlreadyTurn) {
        this.hasAlreadyTurn = hasAlreadyTurn;
    }

    public boolean isHasTurnNow() {
        return hasTurnNow;
    }

    public void setHasTurnNow(boolean hasTurnNow) {
        this.hasTurnNow = hasTurnNow;
    }
}
