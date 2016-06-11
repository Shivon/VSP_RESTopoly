package de.haw.vs.escr.boards.repos;

import de.haw.vs.escr.boards.models.entities.Field;
import de.haw.vs.escr.boards.models.entities.Pawn;
import de.haw.vs.escr.boards.models.entities.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eric on 04.05.2016.
 */
public class FieldRepo {
    private List<Field> fieldList;

    public FieldRepo() {
        this.fieldList = new ArrayList<>();
    }

    public Field saveField(Field field) {
        if (this.fieldList.stream().anyMatch(f -> f.getFieldId() == field.getFieldId())) return this.updateField(field);
        this.fieldList.add(field);
        return this.fieldList.stream().filter(f -> f.getFieldId() == field.getFieldId()).findFirst().get();
    }

    private Field updateField(Field field) {
        this.deleteField(field);
        this.fieldList.add(field);
        return field;
    }

    private void deleteField(Field field) {
        this.fieldList.removeIf(f -> f.getFieldId() == field.getFieldId());
    }

    public Field findFieldByFieldId(int fieldId) {
        return this.fieldList.get(fieldId-1);
        //return this.fieldList.stream().filter((f -> f.getFieldId() == fieldId)).findFirst().get();
    }

    public Field addPawn(Pawn pawn, Place place) {
        for (Field f : fieldList) {
            if (f.getPlace().getUri().equals(place.getUri())) {
                f.addPawn(pawn);
                return f;
            }
        }
        return null;
    }

    public List<Field> getAllFields() {
        return fieldList;
    }

    public List<Field> getAllFieldsByBoardId(int boardId){
        List<Field> ret = fieldList.stream().filter(f -> f.getBoardId() == boardId).collect(Collectors.toList());
        return ret;
    }

    public Field updatePawn(Pawn pawn, Place oldPlace, Place newPlace) {
        if(oldPlace.getUri().equals(newPlace.getUri())){
            return findFieldByFieldId(oldPlace.getPlaceId());
        }
        deletePawn(pawn, oldPlace);
        return addPawn(pawn, newPlace);
    }

    private void deletePawn(Pawn pawn, Place place) {
        for(Field f : fieldList){
            if(f.getFieldId() == place.getPlaceId()){
                f.removePawn(pawn);
                return;
            }
        }
    }
}
