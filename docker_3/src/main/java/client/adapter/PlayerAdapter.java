package client.adapter;

import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.view.GamesWindowUI;
import client.view.VstTableModel;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jana Mareike on 18.05.2016.
 */
public class PlayerAdapter {

    Gson gson = new Gson();
    private VstTableModel _gamesTableModel;
    private GamesWindowUI _gamesWindowUI;
    private String _player;

    public PlayerAdapter(){}

    public void postPlayer(VstTableModel gamesTableModel, GamesWindowUI gamesWindowUI, String playerPawn) throws UnirestException {
       this._gamesTableModel = gamesTableModel;
       this._gamesWindowUI = gamesWindowUI;
        this._player = playerPawn;

        Unirest.post("http://172.18.0.87:4567/games/"
                + gamesTableModel.getValueAt(gamesWindowUI.getAllGameTable().getSelectedRow(),0) + "/players")
                .field("pawn", playerPawn)
                .asJson();
    }

    public Player[] getPlayers(VstTableModel gamesTableModel, GamesWindowUI _gamesWindowUI) throws UnirestException {
        this._gamesWindowUI = _gamesWindowUI;
        this._gamesTableModel = gamesTableModel;
        String players = Unirest.get("http://172.18.0.87:4567/games/"
                + gamesTableModel.getValueAt(_gamesWindowUI.getAllGameTable().getSelectedRow(),0) + "/players")
                .asString().getBody();
        Player[] playerList = gson.fromJson(players, Player[].class);
       return  playerList;
    }

}
