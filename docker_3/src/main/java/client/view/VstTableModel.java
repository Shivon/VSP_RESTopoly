package client.view;

import client.model.gameModels.Game;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by Jana Mareike on 04.05.2016.
 */
public class VstTableModel  extends AbstractTableModel {

    private List<Game> _gamesList;


    public VstTableModel(List<Game> gamesList) throws UnirestException {
        this._gamesList = gamesList;
    }

    @Override
    public int getRowCount(){ return _gamesList.size();}

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object value = "??";
        Game game = _gamesList.get(rowIndex);
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

    public Game getGameAt(int row){
        return _gamesList.get(row);
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

