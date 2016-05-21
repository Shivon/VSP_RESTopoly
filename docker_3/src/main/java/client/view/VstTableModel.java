package client.view;

import client.adapter.GamesAdapter;
import client.model.gameModels.Game;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.eclipse.jetty.server.Authentication;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jana Mareike on 04.05.2016.
 */
public class VstTableModel  extends AbstractTableModel {

    private GamesAdapter gamesAdapter;
    private Game[] _gamesList;


    public VstTableModel(Game[] gamesList) throws UnirestException {
        this.gamesAdapter = new GamesAdapter();
        this._gamesList = gamesList;
    }

    @Override
    public int getRowCount() {
        return _gamesList.length;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object value = "??";

         Game game = _gamesList[rowIndex];
        switch (columnIndex) {
            case 0:
                value = game.getGameId();
                break;
            case 1:
                value = game.getName();
                break;
            case 2:
                value = game.getPlayers();
                break;
            case 3:
                value = game.getServices();
                break;
        }

        return value;

    }

    public Game getGameAt(int row) {
        return _gamesList[row];
    }
//    @Override
//    public Class<?> getColumnClass(int columnIndex) {
//        return // Return the class that best represents the column...
//    }

    /* Override this if you want the values to be editable...
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //....
    }
    */

    /**
     * This will return the user at the specified row...
     * @param row
     * @return
     */


}

