package client.model.dtos;

import client.model.gameModels.GameStatus;
import com.google.gson.annotations.Expose;


/**
 * Created by Christian on 03.05.2016.
 */
public class StatusDTO {
    @Expose
    private GameStatus status;

    public StatusDTO() {
    }

    public StatusDTO(GameStatus status) {
        this.status = status;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
