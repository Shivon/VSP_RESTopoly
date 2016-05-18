package client.adapter;

import client.view.GamesWindowUI;
import client.view.VstTableModel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.ArrayList;

/**
 * Created by Jana Mareike on 18.05.2016.
 */
public class PlayerAdapter {

    private VstTableModel _gamesTableModel;
    private GamesWindowUI _gamesWindowUI;
    private String _player;

    public PlayerAdapter(){}

    public static void postPlayer(VstTableModel gamesTableModel, GamesWindowUI gamesWindowUI, String playerName) throws UnirestException {
//       this._gamesTableModel = gamesTableModel;
//       this._gamesWindowUI = gamesWindowUI;
//        this._player = playerName;

        Unirest.post("http://localhost:4567/games/"
                + gamesTableModel.getValueAt(gamesWindowUI.getAllGameTable().getSelectedRow(),0) + "/players")
                .field("name", playerName)
                .asJson();
    }

    public static HttpResponse<ArrayList> getPlayers(VstTableModel gamesTableModel, GamesWindowUI _gamesWindowUI) throws UnirestException {
       return  Unirest.get("http://localhost:4567/games/"
                + gamesTableModel.getValueAt(_gamesWindowUI.getAllGameTable().getSelectedRow(),0) + "/players")
                .asObject(ArrayList.class);
    }
}
