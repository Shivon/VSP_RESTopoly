package de.haw.vs.escr.boards.businesslogic;

import de.haw.vs.escr.boards.models.dtos.*;
import de.haw.vs.escr.boards.models.entities.*;
import de.haw.vs.escr.boards.repos.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 03.05.2016.
 */
public class BoardServiceBusinessLogic {
    private BoardRepo boardRepo;
    private PawnRepo pawnRepo;
    private PlaceRepo placeRepo;
    private FieldRepo fieldRepo;
    private ThrowRepo throwRepo;
    private MoveRepo moveRepo;
    private String brokerURI;
    private final int MAX_FIELDS = 35;

    public BoardServiceBusinessLogic() {
        this.pawnRepo = new PawnRepo();
        this.boardRepo = new BoardRepo();
        this.placeRepo = new PlaceRepo();
        this.fieldRepo = new FieldRepo();
        this.throwRepo = new ThrowRepo();
        this.moveRepo = new MoveRepo();
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
        List<Field> fields = this.initFields(b1);
        b1.updateFields(fields);
        return boardRepo.saveBoard(b1);
    }

    public List<Field> initFields(Board b) {
        List<Field> fieldList = new ArrayList<>();
        for (int i = 0; i < MAX_FIELDS; i++) {
            Field f = new Field();
            f.setFieldId(fieldRepo.getAllFields().size());
            f.setBoardId(b.getBoardId());
            fieldRepo.saveField(f);
            Place place = new Place();
            place.setUri(b.getUri() + "/places/" + (i));
            place.setBrokerURI(this.brokerURI);
            place.setPlaceId(f.getFieldId());
            placeRepo.savePlace(place);
            f.setPlace(place);
            fieldRepo.saveField(f);
            fieldList.add(f);
        }
        return fieldList;
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
        Pawn p = pawn.toEntity();
        int lastSlash = pawn.getPlayer().lastIndexOf('/');
        String name = pawn.getPlayer().substring(lastSlash + 1);

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
        int id = Integer.parseInt(newPlace.getUri().substring(newPlace.getUri().lastIndexOf("/")+1));
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
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Test Event");
        eventDTO.setUri("/test/event");
        eventDTO.setAction("Testing");
        eventDTO.setReason("Event created by Boards for testing");
        ArrayList<EventDTO> eventDTOs = new ArrayList<>();
        eventDTOs.add(eventDTO);
        RollEventsDTO reDTO = new RollEventsDTO();
        reDTO.setEvents(eventDTOs);
        return reDTO;
    }

    public RollEventsDTO movePawn(Pawn pawn, Throw thrw, Board board) {
        throwRepo.addThrow(pawn.getThrowsURI(), thrw);
        Move move = new Move();
        move.setNumber(thrw.getRoll1().getNumber() + thrw.getRoll2().getNumber());
        return this.movePawn(pawn, move, board);
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
