package de.haw.vs.escr.boards.models.dtos;

import com.google.gson.annotations.SerializedName;
import de.haw.vs.escr.boards.models.entities.Throw;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 12.06.2016.
 */
public class ThrowDTO {
    @SerializedName("throws")
    List<Throw> thrws;

    public ThrowDTO(){
        this.thrws = new ArrayList<>();
    }

    public void addThrow(Throw t){
        this.thrws.add(t);
    }

    public void addAll(List<Throw> thrws){
        this.thrws.addAll(thrws);
    }
}
