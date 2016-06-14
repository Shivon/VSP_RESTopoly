package de.haw.vs.escr.boards.repos;

import de.haw.vs.escr.boards.models.entities.Throw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 12.06.2016.
 */
public class ThrowRepo {
    private Map<String, List<Throw>> throwMap;

    public ThrowRepo(){
        this.throwMap = new HashMap<>();
    }

    public List<Throw> addThrow(String throwsURI, Throw thrw){
        if(throwMap.containsKey(throwsURI)) {
            List<Throw> thrws = throwMap.get(throwsURI);
            thrws.add(thrw);
            throwMap.put(throwsURI, thrws);
            return thrws;
        }
        List<Throw> thrws = new ArrayList<>();
        thrws.add(thrw);
        throwMap.put(throwsURI, thrws);
        return thrws;
    }
    
    public List<Throw> getThrowsByThrowURI(String throwURI){
        return throwMap.get(throwURI);
    }
}
