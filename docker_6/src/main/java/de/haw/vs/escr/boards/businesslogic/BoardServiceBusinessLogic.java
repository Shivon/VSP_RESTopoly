package de.haw.vs.escr.boards.businesslogic;

import com.google.gson.Gson;
import com.jayway.restassured.response.Response;
import de.haw.vs.escr.boards.models.dtos.*;
import de.haw.vs.escr.boards.models.entities.*;
import de.haw.vs.escr.boards.repos.*;
import de.haw.vs.escr.boards.restmodels.BrokerRestModel;
import de.haw.vs.escr.boards.restmodels.GameRestModel;
import de.haw.vs.escr.boards.util.FieldNames;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by Eric on 03.05.2016.
 */
public class BoardServiceBusinessLogic {
    private Gson gson;
    private BoardRepo boardRepo;
    private PawnRepo pawnRepo;
    private PlaceRepo placeRepo;
    private FieldRepo fieldRepo;
    private ThrowRepo throwRepo;
    private MoveRepo moveRepo;
    private String brokerURI;
    private FieldNames fieldNames;
    private final int MAX_FIELDS;

    public BoardServiceBusinessLogic() {
        this.fieldNames = new FieldNames();
        this.MAX_FIELDS = fieldNames.getGermanNames().size();
        this.pawnRepo = new PawnRepo();
        this.boardRepo = new BoardRepo();
        this.placeRepo = new PlaceRepo();
        this.fieldRepo = new FieldRepo();
        this.throwRepo = new ThrowRepo();
        this.moveRepo = new MoveRepo();
        this.gson = new Gson();
        this.brokerURI = "/test/broker";
    }

    public BoardsDTO findAllBoards() {
        List<Board> boardList = this.boardRepo.findAllBoards();
        BoardsDTO boardsDTO = new BoardsDTO();
        for (Board b : boardList) {
            boardsDTO.addBoard(b.getUri());
        }
        return boardsDTO;
    }

    public Board createBoard(Board b) {
        Board b1 = boardRepo.saveBoard(b);
        System.out.println("------- Creating GRM");
        GameRestModel grm = new GameRestModel(b1.getPaths().getGame());
        System.out.println("------- OK");
        Paths p2 = getPathsFromGames(grm);
        b1.setPaths(p2);
        System.out.println("------- Initializing fields.. ");
        List<Field> fields = this.initFields(b1);
        System.out.println("------- Initializing fields done");
        b1.updateFields(fields);
        return boardRepo.saveBoard(b1);
    }

    private Paths getPathsFromGames(GameRestModel grm) {
        System.out.println("------- Trying get() on: " + grm.getComponentsRoute());
        Response res = given().get(grm.getComponentsRoute());
        System.out.println("------- Response: " + res.body().asString());
        Paths paths = gson.fromJson(res.body().asString(), Paths.class);
        return paths;
    }

    public List<Field> initFields(Board b) {
        BrokerRestModel brm = new BrokerRestModel(b.getPaths().getBroker());
        List<Field> fieldList = new ArrayList<>();
        System.out.println("------- Trying to put places to: " + brm.getPlaceRoute());
        for (int i = 0; i < MAX_FIELDS; i++) {
            Field f = new Field();
            f.setFieldId(fieldRepo.getAllFields().size());
            f.setBoardId(b.getBoardId());
            fieldRepo.saveField(f);
            Place place = new Place();
            place.setUri(b.getUri() + "/places/" + (i));
            place.setName(fieldNames.getGermanNames().get(i));
            place.setBrokerURI(this.brokerURI);
            place.setPlaceId(f.getFieldId());
            placeRepo.savePlace(place);
            f.setPlace(place);
            fieldRepo.saveField(f);
            fieldList.add(f);
            sendPlaceToBroker(place, brm);
        }
        return fieldList;
    }

    private void sendPlaceToBroker(Place place, BrokerRestModel brm) {
        int placeId = place.getPlaceId();
        BrokerPlaceDTO brokerPlace = new BrokerPlaceDTO();
        List<Integer> cost = new ArrayList<>();
        List<Integer> rent = new ArrayList<>();
        brokerPlace.setValue((int) (220 * (1 + Math.random())));
        brokerPlace.setPlace(place.getUri());
        if (fieldNames.getNonBuyablePlaces().contains(placeId)) {
            if (placeId == 2) {
                rent.add(200);
            } else if (placeId == 38) {
                rent.add(800);
            }
        } else if (!fieldNames.getNotUpgradablePlaces().contains(placeId)) {
            cost.add(100);
            cost.add(200);
            cost.add(300);
            cost.add(500);
            cost.add(1200);
        }

        rent.add(12);
        rent.add(20);
        rent.add(55);
        rent.add(76);
        rent.add(120);

        brokerPlace.setCost(cost);
        brokerPlace.setPlace(place.getUri());
        brokerPlace.setRent(rent);
        brokerPlace.setValue((int) (220.0 * (1.0 + Math.random())));
        Response res = given().body(place).put(brm.getPlaceRoute() + "" + place.getPlaceId());
    }

    public Board findBoardByGameURI(String gameURI) {
        return boardRepo.findBoardByGameId(gameURI);
    }

    public void deleteBoard(Board board) {
        boardRepo.deleteBoard(board);
    }

    public List<String> getAllPawns(Board board) {
        List<Field> fields = fieldRepo.getAllFieldsByBoardId(board.getBoardId());
        List<String> ret = new ArrayList<>();
        for (Field f : fields) {
            ret.addAll(f.getPawns());
        }
        return ret;
    }

    public Pawn findPawnByPawnName(String pawnName, Board board) {
        return pawnRepo.findPawnByPawnName(pawnName);
    }

    public Pawn addPawn(PawnDTO pawn, Board board) {
        System.out.println("------ Adding Pawn for " + pawn.getPlayer());
        Pawn p = pawn.toEntity();
        int lastSlash = pawn.getPlayer().lastIndexOf('/');
        String name = pawn.getPlayer().substring(lastSlash + 1);
        System.out.println("------ Pawn Name: " + name);

        if (pawnRepo.findPawnByPawnName(name) != null) {
            return null;
        }
        p.setIdOnBoard(board.getPositions().size());
        p.setPawnURI(board.getUri() + "/pawns/" + name);
        p.setName(name);
        p.setMovesURI(p.getPawnURI());
        p.setThrowsURI(p.getPawnURI());
        p = pawnRepo.savePawn(p);
        Place place = placeRepo.findPlaceByURI(p.getPlaceURI());
        Field f = fieldRepo.addPawn(p, place);
        System.out.println("Trying to put pawn on field: " + f.getFieldId() + " id on board: " + p.getIdOnBoard());
        board.addPawnToPosition(f.getFieldId(), p.getIdOnBoard());
        boardRepo.saveBoard(board);
        return p;
    }

    private Place getPlace(int placeId) {
        return placeRepo.findPlaceById(placeId);
    }

    private Place getPlaceByPlaceURI(String placeURI) {
        try {
            int placeId = Integer.parseInt(placeURI.substring(placeURI.lastIndexOf("/") + 1));
            return this.getPlace(placeId);
        } catch (Exception e) {
            return null;
        }
    }

    public Pawn updatePawn(Pawn newPawn, Pawn oldPawn, Board board) {
        Place oldPlace = placeRepo.findPlaceByURI(newPawn.getPlaceURI());
        Place newPlace = placeRepo.findPlaceByURI(oldPawn.getPlaceURI());
        int id = Integer.parseInt(newPlace.getUri().substring(newPlace.getUri().lastIndexOf("/") + 1));
        if (oldPawn.getPlayerURI() != null) newPawn.setPlayerURI(oldPawn.getPlayerURI());
        if (oldPawn.getMovesURI() != null) newPawn.setMovesURI(oldPawn.getMovesURI());
        if (oldPawn.getThrowsURI() != null) newPawn.setThrowsURI(oldPawn.getThrowsURI());
        if (oldPawn.getName() != null) newPawn.setName(oldPawn.getName());
        if (oldPawn.getPlaceURI() != null) newPawn.setPlaceURI(oldPawn.getPlaceURI());
        newPawn.setPosition(id);
        fieldRepo.updatePawn(newPawn, oldPlace, newPlace);
        board.updateFields(fieldRepo.getAllFieldsByBoardId(board.getBoardId()));
        return pawnRepo.savePawn(newPawn);
    }

    public Board updateBoard(BoardDTO boardDTO) {
        Board board = boardDTO.toEntity();
        for (FieldDTO fieldDTO : boardDTO.getFields()) {
            Field f = fieldDTO.toEntity();
            f.setBoardId(board.getBoardId());
            this.updateField(f);
        }
        board.updateFields(fieldRepo.getAllFieldsByBoardId(board.getBoardId()));
        return boardRepo.saveBoard(board);
    }

    private Field updateField(Field field) {
        return this.fieldRepo.saveField(field);
    }

    public void deletePawn(Pawn pawn) {
        this.pawnRepo.deletePawn(pawn);
    }

    public RollEventsDTO movePawn(Pawn pawn, Move move, Board board) {
        Place currPlace = this.getPlaceByPlaceURI(pawn.getPlaceURI());
        int newPlaceId = (currPlace.getPlaceId() + move.getNumber()) % MAX_FIELDS;
        Place newPlace = placeRepo.findPlaceByURI(board.getUri() + "/places/" + newPlaceId);
        Pawn p2 = new Pawn();
        p2.setPosition(newPlaceId);
        p2.setPlaceURI(newPlace.getUri());
        moveRepo.addMove(pawn.getMovesURI(), move);
        this.updatePawn(pawn, p2, board);

        if (board.getPaths().getBroker() != null) {
            BrokerRestModel brm = new BrokerRestModel(board.getPaths().getBroker());
            System.out.println("------- Trying post() on: " + brm.getVisitRoute(newPlaceId) + " with body: " + pawn.getPawnURI());
            Response res = given().body(pawn.getPawnURI()).post(brm.getVisitRoute(newPlaceId));
            System.out.println("------- Response from post on Broker: " + res.body().asString());
            EventsDTO dto = gson.fromJson(res.body().asString(), EventsDTO.class);
            RollEventsDTO rollEventsDTO = new RollEventsDTO();
            rollEventsDTO.setEvents(dto.getEventsAsDto());
            return rollEventsDTO;
        }
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Error Event");
        eventDTO.setUri("/error/event");
        eventDTO.setAction("error");
        eventDTO.setReason("Event created by Boards, because Broker URI was not set. Broker URI: " + board.getPaths().getBroker());
        ArrayList<EventDTO> eventDTOs = new ArrayList<>();
        eventDTOs.add(eventDTO);
        RollEventsDTO reDTO = new RollEventsDTO();
        reDTO.setEvents(eventDTOs);
        return reDTO;
    }

    public RollEventsDTO movePawn(Pawn pawn, Throw thrw, Board board) {
        if (turnOfPawn(board, pawn)) {
            throwRepo.addThrow(pawn.getThrowsURI(), thrw);
            Move move = new Move();
            move.setNumber(thrw.getRoll1().getNumber() + thrw.getRoll2().getNumber());
            return this.movePawn(pawn, move, board);
        }
        return null;
    }

    private boolean turnOfPawn(Board board, Pawn p) {
        GameRestModel grm = new GameRestModel(board.getPaths().getGame());
        System.out.println("------- Trying get() on: " + grm.getTurnRoute());
        Response res = given().get(grm.getTurnRoute());
        System.out.println("------- Response: " + res.body().asString());
        PlayerDTO playerDTO = gson.fromJson(res.body().asString(), PlayerDTO.class);
        if (playerDTO.getPawn().equals(p.getPawnURI())) return true;
        return false;
    }

    public Place findPlaceByPlaceId(String placeId, Board b) {
        int id = Integer.parseInt(placeId);
        Field field = fieldRepo.findFieldByFieldId(id);
        return field.getPlace();
    }

    public PlacesDTO getPlacesForBoard(int boardId) {
        PlacesDTO places = new PlacesDTO();
        for (Field f : fieldRepo.getAllFieldsByBoardId(boardId)) {
            places.addPlace(f.getPlace().getUri());
        }
        return places;
    }

    public Place updatePlace(Place p, Place bodyPlace, Board b) {
        p.setName(bodyPlace.getName());
        placeRepo.savePlace(p);
        Field f = fieldRepo.findFieldByFieldId(p.getPlaceId());
        f.setPlace(p);
        fieldRepo.saveField(f);
        return p;
    }

    public ThrowDTO getRollsOfPawn(Board board, Pawn pawn) {
        List<Throw> throwList = this.throwRepo.getThrowsByThrowURI(pawn.getThrowsURI());
        ThrowDTO dto = new ThrowDTO();
        dto.addAll(throwList);
        return dto;
    }
}
