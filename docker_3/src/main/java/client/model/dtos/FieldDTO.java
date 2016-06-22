package client.model.dtos;

import client.model.boardModels.Field;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 12.06.2016.
 */
public class FieldDTO {
    @Expose
    private String place;
    @Expose
    private List<String> pawns;

    public FieldDTO(){
        this.pawns = new ArrayList<>();
    }

    public List<String> getPawns() {
        return pawns;
    }

    public void setPawns(List<String> pawns) {
        this.pawns = pawns;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Field toEntity(){
        Field f = new Field();
        f.setPawns(this.pawns);
        return f;
    }
}
