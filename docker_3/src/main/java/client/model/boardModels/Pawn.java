package client.model.boardModels;


import client.model.dtos.PawnDTO;

/**
 * Created by Eric on 25.05.2016.
 */
public class Pawn {
    private String id;
    private String pawnURI;
    private String throwsURI;
    private String movesURI;
    private String playerURI;
    private int position;
    private String PlaceURI;

    public Pawn() {
    }


    public String getPlayerURI() {
        return playerURI;
    }

    public void setPlayerURI(String playerURI) {
        this.playerURI = playerURI;
    }

    public String getMovesURI() {
        return movesURI;
    }

    public void setMovesURI(String movesURI) {
        this.movesURI = movesURI;
    }

    public String getThrowsURI() {
        return throwsURI;
    }

    public void setThrowsURI(String throwsURI) {
        this.throwsURI = throwsURI;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPlaceURI() {
        return PlaceURI;
    }

    public void setPlaceURI(String placeURI) {
        PlaceURI = placeURI;
    }

    public PawnDTO toPawnDTO() {
        PawnDTO pawnDTO = new PawnDTO();
        pawnDTO.setPosition(this.position);
        pawnDTO.setPlace(this.getPlaceURI());
        pawnDTO.setPawnURI(this.getPawnURI());
        pawnDTO.setId(this.getId());
        return pawnDTO;
    }

    public String getPawnURI() {
        return pawnURI;
    }

    public void setPawnURI(String pawnURI) {
        this.pawnURI = pawnURI;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
