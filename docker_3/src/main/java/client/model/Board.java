package client.model;

import com.google.gson.annotations.Expose;
import com.sun.org.apache.xpath.internal.operations.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Eric on 03.05.2016.
 */
@Entity
@Table(name = "Board")
public class Board {
    @Id
    @Expose
    private int boardId;

    @Column(name = "uri")
    @Expose
    private String uri;

    public Board(){

    }

    public Board(String uri){
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }
}
