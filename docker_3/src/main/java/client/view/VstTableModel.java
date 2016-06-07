package client.view;

import client.adapter.GamesAdapter;
import client.model.gameModels.Game;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.table.AbstractTableModel;

/**
 * Created by Jana Mareike on 04.05.2016.
 */
public class VstTableModel  extends AbstractTableModel {

    private GamesAdapter gamesAdapter;
    private Game[] _gamesList;


    public VstTableModel(Game[] gamesList) throws UnirestException {
        this.gamesAdapter = new GamesAdapter(null);
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

}

