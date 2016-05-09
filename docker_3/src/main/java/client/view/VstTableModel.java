package client.view;

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

    private HttpResponse<ArrayList> gamesListResponse =  Unirest.get("http://localhost:4567/games")
            .asObject(ArrayList.class);
    ArrayList<HashMap> gamesList = gamesListResponse.getBody();

    public VstTableModel(ArrayList<HashMap> gamesList) throws UnirestException {

        this.gamesList = new ArrayList<HashMap>(gamesList);

    }

    @Override
    public int getRowCount() {
        return gamesList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Object value = "??";
         HashMap game = gamesList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                value = game.get("id").toString();
                break;
            case 1:
                value = game.get("name").toString();
                break;
            case 2:
                value = game.get("players").toString();
                break;
            case 3:
                value = game.get("services").toString();
                break;
        }

        return value;

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
    public HashMap getGameAt(int row) {
        return gamesList.get(row);
    }

}

