package de.haw.vs.escr.boards.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.haw.vs.escr.boards.businesslogic.BoardServiceBusinessLogic;
import de.haw.vs.escr.boards.models.dtos.*;
import de.haw.vs.escr.boards.models.entities.*;

import java.util.List;

import static spark.Spark.*;

/**
 * Created by Eric on 03.05.2016.
 */
public class BoardService {
    private final Gson gson;
    private final BoardServiceBusinessLogic boardServiceBusinessLogic;
    private GsonBuilder gb;

    public BoardService() {
        gb = new GsonBuilder();
        gb.excludeFieldsWithoutExposeAnnotation();
        this.gson = gb.create();
        boardServiceBusinessLogic = new BoardServiceBusinessLogic();

        //<editor-fold desc="/boards/:gameId">
        get("/boards/:gameId", (req, res) -> {
            System.out.println("------- Received get on /boards/:gameId");
            String gameId;
            gameId = "/games/" + req.params(":gameId");
            Board board = this.boardServiceBusinessLogic.findBoardByGameURI(gameId);
            if (board != null) {
                return gson.toJson(board.toBoardDTO());
            }
            res.status(400);
            return null;
        });

        put("/boards/:gameId", (request, response) -> {
            System.out.println("------- Received put on /boards/:gameId");
            Board boardByGameId;
            BoardDTO givenBoard;
            String gameURI = "/games/" + request.params(":gameId");

            try {
                boardByGameId = boardServiceBusinessLogic.findBoardByGameURI(gameURI);
                givenBoard = gson.fromJson(request.body(), BoardDTO.class);
            } catch (Exception e) {
                response.status(404);
                e.printStackTrace();
                return null;
            }
            if (!givenBoard.getId().equals(boardByGameId.getUri())) {
                response.status(404);
                System.out.println("wrong boardID");
                return null;
            }

            return gson.toJson(boardServiceBusinessLogic.updateBoard(givenBoard).toBoardDTO());
        });

        delete("/boards/:gameId", (req, res) -> {
            System.out.println("------- Received delete on /boards/:gameId by "+req.ip());
            String gameURI = "/games/" + req.params(":gameId");
            Board board = this.boardServiceBusinessLogic.findBoardByGameURI(gameURI);
            boardServiceBusinessLogic.deleteBoard(board);
            res.status(200);
            return gson.toJson(board.toBoardDTO());
        });

        get("/boards/:gameId/paths", (req, res) -> {
            System.out.println("------- Received get on /boards/:gameId");
            String gameId;
            gameId = "/games/" + req.params(":gameId");
            Board board = this.boardServiceBusinessLogic.findBoardByGameURI(gameId);
            if (board != null) {
                return gson.toJson(board.getPaths());
            }
            res.status(404);
            return null;
        });
        //</editor-fold>

        //<editor-fold desc="/boards">
        get("/boards", (req, res) -> {
            System.out.println("------- Received get on /boards");
            BoardsDTO boards = this.boardServiceBusinessLogic.findAllBoards();
            return gson.toJson(boards);
        });

        post("/boards", (req, res) -> {
            System.out.println("------- Received post on /boards");
            BoardDTO boardDTO;
            Board board;
            try {
                board = gson.fromJson(req.body(), BoardDTO.class).toEntity();
                if(!(board.getBoardId() > 0)){
                    res.status(400);
                    return null;
                }
            } catch (Exception e) {
                res.status(400);
                e.printStackTrace();
                return null;
            }

            Paths p = new Paths();
            p.setGame("http://"+req.ip()+":4567/games/"+board.getBoardId());
            board.setPaths(p);
            Board b = boardServiceBusinessLogic.createBoard(board);
            BoardDTO bDTO = b.toBoardDTO();
            return gson.toJson(bDTO);
        });
        //</editor-fold>

        //<editor-fold desc="/boards/:gameId/pawns/:pawnId">
        get("/test/", (req, res) -> {
            Roll r1 = new Roll();
            Roll r2 = new Roll();
            r1.setNumber(4);
            r2.setNumber(6);
            Throw t = new Throw();
            t.setRoll1(r1);
            t.setRoll2(r2);
            t.setRollId(1);
            return gson.toJson(t);
        });

        post("/boards/:gameid/pawns/:pawnId/roll", (req, res) -> {
            System.out.println("------- Received post on /boards/:gameId/pawns/:pawnId/roll");
            Throw thrw;
            Board b;
            Pawn p;

            try {
                thrw = gson.fromJson(req.body(), Throw.class);
            } catch (Exception e) {
                res.status(400);
                e.printStackTrace();
                return null;
            }
            try {
                b = this.boardServiceBusinessLogic.findBoardByGameURI("/games/" + req.params(":gameid"));
                p = this.boardServiceBusinessLogic.findPawnByPawnName(req.params(":pawnId"), b);
            } catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                return null;
            }

            RollEventsDTO rollEventsDTO = this.boardServiceBusinessLogic.movePawn(p, thrw, b);
            if(rollEventsDTO == null){
                System.out.println("Pawn not allowed to move");
                res.status(403);
                return null;
            }
            return gson.toJson(rollEventsDTO);
        });

        get("/boards/:gameid/pawns/:pawnId/roll", (req, res) -> {
            System.out.println("------- Received get on /boards/:gameId/pawns/:pawnId/roll");
            Board b;
            Pawn p;

            try {
                b = this.boardServiceBusinessLogic.findBoardByGameURI("/games/" + req.params(":gameid"));
                p = this.boardServiceBusinessLogic.findPawnByPawnName(req.params(":pawnId"), b);
            } catch (Exception e) {
                res.status(404);
                return null;
            }

            return gson.toJson(boardServiceBusinessLogic.getRollsOfPawn(b, p));
        });

        post("/boards/:gameid/pawns/:pawnId/move", (req, res) -> {
            System.out.println("------- Received post on /boards/:gameId/pawns/:pawnId/move");
            Move move;
            Board board;
            Pawn pawn;
            try {
                move = gson.fromJson(req.body(), Move.class);
            } catch (Exception e) {
                res.status(400);
                e.printStackTrace();
                return null;
            }

            try {
                board = this.boardServiceBusinessLogic.findBoardByGameURI("/games/" + req.params(":gameid"));
                pawn = this.boardServiceBusinessLogic.findPawnByPawnName(req.params(":pawnId"), board);
            } catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                return null;
            }
            RollEventsDTO rollEventsDTO = this.boardServiceBusinessLogic.movePawn(pawn, move, board);
            return gson.toJson(rollEventsDTO);
        });

        get("/boards/:gameid/pawns/:pawnId", (req, res) -> {
            System.out.println("------- Received get on /boards/:gameId/pawns/:pawnId");
            String pawnId = req.params(":pawnId");
            String gameURI = "/games/" + req.params("gameId");
            Board board;
            Pawn pawn;
            try {
                board = this.boardServiceBusinessLogic.findBoardByGameURI(gameURI);
                pawn = this.boardServiceBusinessLogic.findPawnByPawnName(pawnId, board);
            } catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                return null;
            }
            return gson.toJson(pawn.toPawnDTO());
        });

        put("/boards/:gameid/pawns/:pawnId", (req, res) -> {
            System.out.println("------- Received put on /boards/:gameId/pawns/:pawnId");
            String gameURI = "/games/" + req.params(":gameId");
            String pawnId = req.params(":pawnId");
            Board board;
            Pawn givenPawn;
            Pawn pawnByPawnId;
            try {
                board = boardServiceBusinessLogic.findBoardByGameURI(gameURI);
                pawnByPawnId = boardServiceBusinessLogic.findPawnByPawnName(pawnId, board);
                givenPawn = gson.fromJson(req.body(), PawnDTO.class).toEntity();
            } catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                return null;
            }

            return gson.toJson(boardServiceBusinessLogic.updatePawn(pawnByPawnId, givenPawn, board));

        });

        delete("/boards/:gameid/pawns/:pawnId", (req, res) -> {
            System.out.println("------- Received delete on /boards/:gameId/pawns/:pawnId");
            String gameURI = "/games/" + req.params(":gameId");
            String pawnId = req.params(":pawnId");
            Board board;
            Pawn pawnByPawnId;

            try {
                board = boardServiceBusinessLogic.findBoardByGameURI(gameURI);
                pawnByPawnId = boardServiceBusinessLogic.findPawnByPawnName(pawnId, board);
            } catch (Exception e) {
                res.status(404);
                res.body("Pawn not found");
                e.printStackTrace();
                return null;
            }
            boardServiceBusinessLogic.deletePawn(pawnByPawnId);

            return gson.toJson(pawnByPawnId);
        });
        //</editor-fold>

        //<editor-fold desc="/boards/:gameId/pawns">
        post("/boards/:gameid/pawns", (req, res) -> {
            System.out.println("------- Received post on /boards/:gameId/pawns");
            String gameURI = "/games/" + req.params("gameId");
            PawnDTO pawn;
            Board board;
            Pawn p;
            System.out.println("test");
            try {
                pawn = gson.fromJson(req.body(), PawnDTO.class);
                int id = Integer.parseInt(pawn.getPlace().substring(pawn.getPlace().lastIndexOf("/")+1));
                if (id != pawn.getPosition()){
                    res.status(400);
                    return null;
                }
            } catch (Exception e) {
                res.status(400);
                e.printStackTrace();
                return null;
            }

            try {
                board = this.boardServiceBusinessLogic.findBoardByGameURI(gameURI);
                p = this.boardServiceBusinessLogic.addPawn(pawn, board);
            } catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                return null;
            }

            if(p == null){
                res.status(400);
                return null;
            }
            return gson.toJson(p.toPawnPostDTO());
        });

        get("/boards/:gameid/pawns", (req, res) -> {
            System.out.println("------- Received get on /boards/:gameId/pawns");
            String gameURI = "/games/" + req.params("gameId");
            Board board;
            try {
                board = this.boardServiceBusinessLogic.findBoardByGameURI(gameURI);
            } catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                return null;
            }
            List<String> pawns = boardServiceBusinessLogic.getAllPawns(board);
            return gson.toJson(pawns);
        });
        //</editor-fold>

        //<editor-fold desc="/boards/:gameId/places">
        get("/boards/:gameId/places", (req, res) -> {
            System.out.println("------- Received get on /boards/:gameId/places");
            int boardId;

            try {
                boardId = Integer.parseInt(req.params(":gameId"));
            } catch (Exception e) {
                res.status(400);
                e.printStackTrace();
                return null;
            }
            PlacesDTO places = boardServiceBusinessLogic.getPlacesForBoard(boardId);
            return gson.toJson(places.getPlaces());
        });
        //</editor-fold>

        //<editor-fold desc="/boards/:gameId/places/:placeId">
        get("/boards/:gameId/places/:placeId", (req, res) -> {
            System.out.println("------- Received get on /boards/:gameId/places/:placeId");
            Board b;
            Place p;

            try {
                b = this.boardServiceBusinessLogic.findBoardByGameURI("/games/" + req.params(":gameid"));
                p = this.boardServiceBusinessLogic.findPlaceByPlaceId(req.params(":placeId"), b);
            } catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                return null;
            }
            return gson.toJson(p);
        });

        put("/boards/:gameId/places/:placeId", (req, res) -> {
            System.out.println("------- Received put on /boards/:gameId/places/:placeId");
            Board b;
            Place p;
            Place bodyPlace;

            try {
                bodyPlace = gson.fromJson(req.body(), Place.class);
            } catch (Exception e) {
                res.status(400);
                e.printStackTrace();
                return null;
            }

            try {
                b = this.boardServiceBusinessLogic.findBoardByGameURI("/games/" + req.params(":gameid"));
                p = this.boardServiceBusinessLogic.findPlaceByPlaceId(req.params(":placeId"), b);
            } catch (Exception e) {
                res.status(404);
                e.printStackTrace();
                return null;
            }
            Place retPlace = boardServiceBusinessLogic.updatePlace(p, bodyPlace, b);
            return gson.toJson(retPlace);
        });
        //</editor-fold>
    }
}
