package de.haw.vs.escr.boards.service;

import com.google.gson.Gson;
import de.haw.vs.escr.boards.businesslogic.BoardServiceBusinessLogic;
import de.haw.vs.escr.boards.models.Board;
import de.haw.vs.escr.boards.models.Pawn;

import java.util.List;

import static spark.Spark.*;

/**
 * Created by Eric on 03.05.2016.
 */
public class BoardService {
    private final Gson gson;
    private final BoardServiceBusinessLogic boardServiceBusinessLogic;

    public BoardService() {
        gson = new Gson();
        boardServiceBusinessLogic = new BoardServiceBusinessLogic();

        get("/boards", (req, res) -> {
            List<Board> boards = this.boardServiceBusinessLogic.findAllBoards();
            return gson.toJson(boards);
        });

        post("/boards", (req, res) -> {
            String gameURI = gson.fromJson(req.body(), String.class);
            Board b = boardServiceBusinessLogic.createBoard(gameURI);
            return gson.toJson(b);
        });

        get("/boards/:gameid", (req, res) -> {
            String gameId;
            gameId = req.params(":gameid");
            Board board = this.boardServiceBusinessLogic.findBoardByGameId(gameId);
            if (board != null) {
                return gson.toJson(board);
            }
            res.status(400);
            return null;
        });

        delete("/boards/:gameid)", (req, res) -> {
            String gameId;
            gameId = req.params(":gameid");
            Board board = this.boardServiceBusinessLogic.findBoardByGameId(gameId);
            boardServiceBusinessLogic.deleteBoard(board);
            res.status(200);
            return gson.toJson(board);
        });

        get("/boards/:gameid/pawns", (req, res) -> {
            String gameURI = gson.fromJson(req.body(), String.class);
            Board board = this.boardServiceBusinessLogic.findBoardByGameId(gameURI);
            List<String> pawns = boardServiceBusinessLogic.getAllPawns(board);
            return gson.toJson(pawns);
        });

        get("/boards/:gameid/pawns/:pawnId", (req, res) -> {
            String pawnId = req.params(":pawnId");
            String gameURI = gson.fromJson(req.body(), String.class);
            Board board = this.boardServiceBusinessLogic.findBoardByGameId(gameURI);
            Pawn pawn = this.boardServiceBusinessLogic.findPawnByPawnId(pawnId, board);
            if (pawn == null) {
                res.status(401);
                return null;
            }
            return gson.toJson(pawn);
        });

        put("/boards/:gameid/pawns/:pawnId", (req, res) -> {
            int gameId;
            String pawnId = req.params(":pawnId");
            String gameURI = gson.fromJson(req.body(), String.class);
            Board board = this.boardServiceBusinessLogic.findBoardByGameId(gameURI);
            Pawn pawn = boardServiceBusinessLogic.findPawnByPawnId(pawnId, board);
            if (pawn == null) {
                res.status(400);
                return null;
            }
            this.boardServiceBusinessLogic.updatePawn(pawnId, board);
            return null;
        });


        post("/boards/:gameid/pawns", (req, res) -> {
            String gameURI = gson.fromJson(req.body(), String.class);
            Pawn pawn = gson.fromJson(req.body(), Pawn.class);
            Board board = this.boardServiceBusinessLogic.findBoardByGameId(gameURI);
            this.boardServiceBusinessLogic.addPawn(pawn, board);
            return null;
        });
    }
}
