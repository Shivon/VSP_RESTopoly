package de.haw.vs.escr.boards.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.haw.vs.escr.boards.businesslogic.BoardServiceBusinessLogic;
import de.haw.vs.escr.boards.models.dtos.BoardDTO;
import de.haw.vs.escr.boards.models.dtos.BoardsDTO;
import de.haw.vs.escr.boards.models.dtos.PawnDTO;
import de.haw.vs.escr.boards.models.dtos.PlacesDTO;
import de.haw.vs.escr.boards.models.entities.*;
import de.haw.vs.escr.boards.restmodels.GameRestModel;
import de.haw.vs.escr.boards.util.yellowpages.YellowPagesService;
import de.haw.vs.escr.boards.util.yellowpages.model.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by Eric on 03.05.2016.
 */
public class BoardService {
    private static final Logger log = LoggerFactory.getLogger(BoardService.class);
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
            log.info("Received get on /boards/:gameId");

            String gameId;
            gameId = "/games/" + req.params(":gameId");
            Board board = this.boardServiceBusinessLogic.findBoardByGameURI(gameId);
            if (board != null) {
                return gson.toJson(board.toBoardDTO());
            }
            log.error("Failed to create Board. Board was null");
            res.status(400);
            return null;
        });

        put("/boards/:gameId", (request, response) -> {
            log.info("Received put on /boards/:gameId");
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
                log.error("wrong boardID");
                return null;
            }

            return gson.toJson(boardServiceBusinessLogic.updateBoard(givenBoard).toBoardDTO());
        });

        delete("/boards/:gameId", (req, res) -> {
            log.info("Received delete on /boards/:gameId by " + req.ip());
            String gameURI = "/games/" + req.params(":gameId");
            Board board = this.boardServiceBusinessLogic.findBoardByGameURI(gameURI);
            boardServiceBusinessLogic.deleteBoard(board);
            return gson.toJson(board.toBoardDTO());
        });

        get("/boards/:gameId/paths", (req, res) -> {
            log.info("Received get on /boards/:gameId");
            String gameId;
            gameId = "/games/" + req.params(":gameId");
            Board board = this.boardServiceBusinessLogic.findBoardByGameURI(gameId);
            if (board != null) {
                return gson.toJson(board.getPaths());
            }
            log.error("Board not found");
            res.status(404);
            return null;
        });
        //</editor-fold>

        //<editor-fold desc="/boards">
        get("/boards", (req, res) -> {
            BoardsDTO boards = this.boardServiceBusinessLogic.findAllBoards();
            return gson.toJson(boards);
        });

        post("/boards", (req, res) -> {
            log.info("Received post on /boards");
            BoardDTO boardDTO;
            Board board;
            GameRestModel gameRestModel = null;

            YellowPagesService yp = new YellowPagesService();
            Service games = yp.findServiceByName("fancy_games");
            try {
                gameRestModel = new GameRestModel(new URL(games.getUri()).toString());
            } catch (Exception e) {
                log.error("Failed to init games IP");
            }

            try {
                board = gson.fromJson(req.body(), BoardDTO.class).toEntity();
                if (!(board.getBoardId() > 0)) {
                    log.error("Board Id is < 0");
                    res.status(400);
                    return null;
                }
            } catch (Exception e) {
                log.error("Failed to create Board from Body");
                res.status(400);
                e.printStackTrace();
                return null;
            }

            Paths p = new Paths();
            p.setGame(gameRestModel.getPath() + "/games/" + board.getBoardId());
            board.setPaths(p);
            Board b = boardServiceBusinessLogic.createBoard(board);
            BoardDTO bDTO = b.toBoardDTO();
            return gson.toJson(bDTO);
        });
        //</editor-fold>

        post("/boards/:gameId/components", (req, res) -> {
            log.info(" Received POST on /boards/" + req.params(":gameId") + "/components");
            Paths paths;
            Board board;

            try {
                board = this.boardServiceBusinessLogic.findBoardByGameURI("/games/" + req.params(":gameid"));
            } catch (Exception e) {
                log.error("Board with id = " + req.params(":gameId") + " not found");
                res.status(404);
                e.printStackTrace();
                return null;
            }

            try {
                paths = gson.fromJson(req.body(), Paths.class);
            } catch (Exception e) {
                log.error("Failed to get Paths from body. Body: { " + req.body() + " \r\n } Body End");
                res.status(400);
                e.printStackTrace();
                return null;
            }

            return board.toBoardDTO();
        });

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
            log.info("Received post on /boards/:gameId/pawns/:pawnId/roll");
            Throw thrw;
            Board b;
            Pawn p;

            try {
                thrw = gson.fromJson(req.body(), Throw.class);
            } catch (Exception e) {
                log.error("Failed to construct Throw from body. Body: { " + req.body() + " } Body End");
                res.status(400);
                e.printStackTrace();
                return null;
            }
            try {
                b = this.boardServiceBusinessLogic.findBoardByGameURI("/games/" + req.params(":gameid"));
                p = this.boardServiceBusinessLogic.findPawnByPawnName(req.params(":pawnId"), b);
            } catch (Exception e) {
                log.error("Failed to find board or pawn");
                res.status(404);
                e.printStackTrace();
                return null;
            }

            Board board = this.boardServiceBusinessLogic.movePawn(p, thrw, b);
            if (board == null) {
                log.error("Pawn not allowed to move");
                res.status(403);
                return null;
            }
            Board newBoard = this.boardServiceBusinessLogic.findBoardByGameURI(b.getGameURI());
            return gson.toJson(newBoard.toBoardDTO());
        });

        get("/boards/:gameid/pawns/:pawnId/roll", (req, res) -> {
            log.info("Received get on /boards/:gameId/pawns/:pawnId/roll");
            Board b;
            Pawn p;

            try {
                b = this.boardServiceBusinessLogic.findBoardByGameURI("/games/" + req.params(":gameid"));
                p = this.boardServiceBusinessLogic.findPawnByPawnName(req.params(":pawnId"), b);
            } catch (Exception e) {
                log.error("Failed to find board or pawn");
                res.status(404);
                return null;
            }

            return gson.toJson(boardServiceBusinessLogic.getRollsOfPawn(b, p));
        });

        post("/boards/:gameid/pawns/:pawnId/move", (req, res) -> {
            log.info("Received post on /boards/:gameId/pawns/:pawnId/move");
            Move move;
            Board board;
            Pawn pawn;
            try {
                move = gson.fromJson(req.body(), Move.class);
            } catch (Exception e) {
                log.error("Failed to build Move out of Body. Body: [ " + req.body() + " ] Body end");
                res.status(400);
                e.printStackTrace();
                return null;
            }

            try {
                board = this.boardServiceBusinessLogic.findBoardByGameURI("/games/" + req.params(":gameid"));
                pawn = this.boardServiceBusinessLogic.findPawnByPawnName(req.params(":pawnId"), board);
            } catch (Exception e) {
                log.error("Failed to find board or pawn");
                res.status(404);
                e.printStackTrace();
                return null;
            }
            Board newBoard = this.boardServiceBusinessLogic.movePawn(pawn, move, board);
            return gson.toJson(newBoard.toBoardDTO());
        });

        get("/boards/:gameid/pawns/:pawnId", (req, res) -> {
            log.info("Received get on /boards/:gameId/pawns/:pawnId");
            String pawnId = req.params(":pawnId");
            String gameURI = "/games/" + req.params("gameId");
            Board board;
            Pawn pawn;
            try {
                board = this.boardServiceBusinessLogic.findBoardByGameURI(gameURI);
                pawn = this.boardServiceBusinessLogic.findPawnByPawnName(pawnId, board);
            } catch (Exception e) {
                log.error("Failed to find board or pawn");
                res.status(404);
                e.printStackTrace();
                return null;
            }
            return gson.toJson(pawn.toPawnDTO());
        });

        put("/boards/:gameid/pawns/:pawnId", (req, res) -> {
            log.info("Received put on /boards/:gameId/pawns/:pawnId");
            String gameURI = "/games/" + req.params(":gameId");
            String pawnId = req.params(":pawnId");
            Board board;
            Pawn givenPawn;
            Pawn pawnByPawnId;

            try {
                givenPawn = gson.fromJson(req.body(), PawnDTO.class).toEntity();
            } catch (Exception e) {
                log.error("Failed to build Pawn out of Body. Body: [ " + req.body() + " ] Body end");
                res.status(400);
                return null;
            }

            try {
                board = boardServiceBusinessLogic.findBoardByGameURI(gameURI);
                pawnByPawnId = boardServiceBusinessLogic.findPawnByPawnName(pawnId, board);
            } catch (Exception e) {
                log.error("Failed to find Board or Pawn");
                res.status(404);
                e.printStackTrace();
                return null;
            }

            return gson.toJson(boardServiceBusinessLogic.updatePawn(pawnByPawnId, givenPawn, board));

        });

        delete("/boards/:gameid/pawns/:pawnId", (req, res) -> {
            log.info("Received delete on /boards/:gameId/pawns/:pawnId");
            String gameURI = "/games/" + req.params(":gameId");
            String pawnId = req.params(":pawnId");
            Board board;
            Pawn pawnByPawnId;

            try {
                board = boardServiceBusinessLogic.findBoardByGameURI(gameURI);
                pawnByPawnId = boardServiceBusinessLogic.findPawnByPawnName(pawnId, board);
            } catch (Exception e) {
                log.error("Failed to find Board or Pawn");
                res.status(404);
                e.printStackTrace();
                return null;
            }
            boardServiceBusinessLogic.deletePawn(pawnByPawnId);

            return gson.toJson(pawnByPawnId);
        });
        //</editor-fold>

        //<editor-fold desc="/boards/:gameId/pawns">
        post("/boards/:gameid/pawns", (req, res) -> {
            log.info("Received post on /boards/:gameId/pawns");
            String gameURI = "/games/" + req.params("gameId");
            PawnDTO pawn;
            Board board;
            Pawn p;
            try {
                pawn = gson.fromJson(req.body(), PawnDTO.class);
                int id = Integer.parseInt(pawn.getPlace().substring(pawn.getPlace().lastIndexOf("/") + 1));
                if (id != pawn.getPosition()) {
                    res.status(400);
                    log.error("Pawn place =/= Pawn position. Place: " + id + " Position: " + pawn.getPosition());
                    return null;
                }
            } catch (Exception e) {
                log.error("Failed to build pawn from Body. Body: [ " + req.body() + " ] Body end");
                res.status(400);
                e.printStackTrace();
                return null;
            }

            try {
                board = this.boardServiceBusinessLogic.findBoardByGameURI(gameURI);
                p = this.boardServiceBusinessLogic.addPawn(pawn, board);
            } catch (Exception e) {
                log.error("Failed to find board or pawn");
                res.status(404);
                e.printStackTrace();
                return null;
            }

            if (p == null) {
                res.status(400);
                log.error("Error while adding pawn");
                return null;
            }
            return gson.toJson(p.toPawnPostDTO());
        });

        get("/boards/:gameid/pawns", (req, res) -> {
            log.info(" Received get on /boards/:gameId/pawns");
            String gameURI = "/games/" + req.params("gameId");
            Board board;
            try {
                board = this.boardServiceBusinessLogic.findBoardByGameURI(gameURI);
            } catch (Exception e) {
                log.error("Failed to find board");
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
            log.info("Received get on /boards/:gameId/places");
            int boardId;
            PlacesDTO places;

            try {
                boardId = Integer.parseInt(req.params(":gameId"));
                places = boardServiceBusinessLogic.getPlacesForBoard(boardId);
            } catch (Exception e) {
                log.error("Failed to get places");
                res.status(400);
                e.printStackTrace();
                return null;
            }
            return gson.toJson(places.getPlaces());
        });
        //</editor-fold>

        //<editor-fold desc="/boards/:gameId/places/:placeId">
        get("/boards/:gameId/places/:placeId", (req, res) -> {
            log.info("Received get on /boards/:gameId/places/:placeId");
            Board b;
            Place p;

            try {
                b = this.boardServiceBusinessLogic.findBoardByGameURI("/games/" + req.params(":gameid"));
                p = this.boardServiceBusinessLogic.findPlaceByPlaceId(req.params(":placeId"), b);
            } catch (Exception e) {
                log.error("Failed to find board or place");
                res.status(404);
                e.printStackTrace();
                return null;
            }
            return gson.toJson(p);
        });

        put("/boards/:gameId/places/:placeId", (req, res) -> {
            log.info("Received put on /boards/:gameId/places/:placeId");
            Board b;
            Place p;
            Place bodyPlace;

            try {
                bodyPlace = gson.fromJson(req.body(), Place.class);
            } catch (Exception e) {
                log.error("Failed to build Place from Body. Body : [ " + req.body() + " ] Body End");
                res.status(400);
                e.printStackTrace();
                return null;
            }

            try {
                b = this.boardServiceBusinessLogic.findBoardByGameURI("/games/" + req.params(":gameid"));
                p = this.boardServiceBusinessLogic.findPlaceByPlaceId(req.params(":placeId"), b);
            } catch (Exception e) {
                log.error("Failed to find board or place");
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
