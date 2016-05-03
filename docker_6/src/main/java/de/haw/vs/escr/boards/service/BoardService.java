package de.haw.vs.escr.boards.service;

import com.google.gson.Gson;
import de.haw.vs.escr.boards.businesslogic.BoardServiceBusinessLogic;

/**
 * Created by Eric on 03.05.2016.
 */
public class BoardService {
    private final Gson gson;
    private final BoardServiceBusinessLogic boardServiceBusinessLogic;

    public BoardService(){
        gson = new Gson();
        boardServiceBusinessLogic = new BoardServiceBusinessLogic();
    }

    
}
