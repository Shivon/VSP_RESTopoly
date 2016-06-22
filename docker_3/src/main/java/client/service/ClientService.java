package client.service;

import client.logic.TurnToRollLogic;
import client.logic.WaitLogic;
import client.model.Client;
import client.model.gameModels.Player;
import client.repo.ClientRepo;
import client.view.BuyPlaceWindow;
import client.view.TurnToRollWindow;
import client.view.WaitWindow;
import com.google.gson.Gson;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by jana on 30.04.16.
 */
public class ClientService {
    private Gson gson = new Gson();
    private ClientRepo clientRepo = new ClientRepo();
    private Client client;
    private WaitLogic _waitLogic;
    private TurnToRollWindow _turnToRollWindow;
    private WaitWindow _waitWindow;
    private BuyPlaceWindow _buyPlaceWindow;
    private TurnToRollLogic _turnToRollLogic;

    public ClientService(WaitLogic waitLogic, TurnToRollWindow turnToRollWindow,
                         WaitWindow waitWindow, BuyPlaceWindow buyPlaceWindow, TurnToRollLogic turnToRollLogic) {

        _waitLogic = waitLogic;
        _turnToRollWindow = turnToRollWindow;
        _waitWindow = waitWindow;
        _buyPlaceWindow = buyPlaceWindow;
        _turnToRollLogic = turnToRollLogic;

        get("/client", (request, response) -> {
//            URl: /client A service which acts as a representant of a player/client.
//            The endpoint may be found at any other uri, but it must offer the described mehtods
//            and the full uri must be submitted when registering a player
//            gets the details about the player
            String id = request.queryParams("id");

            client = clientRepo.findClient(id);

            if (client == null) {
                response.status(500);
                response.type("application/json");
                return "";
            }

            response.status(200);
            response.type("application/json");
            return gson.toJson(client);
        });

        post("/client/turn", (request, response) -> {
//            Informs the player, that it is his turn
            // würfel fenster geht auf
            // status vom user verändert sich
            // text anzeige ändert sich
            // logic.
            Player player = gson.fromJson(request.body(), Player.class);
            _waitLogic.playerTurn(player);
            _turnToRollWindow.getTurnToRollWindowUI().getDiceFrame().setVisible(true);

            return "";
        });

       post("/client/event", (request, response) -> {
//           TODO checken, ob es geht
//           inform a player about a new event
           client.model.boardModels.Event event = gson.fromJson(request.body(), client.model.boardModels.Event.class);

           if(event.getType().equals("start")){
           _waitWindow.showGameHasStarted();
           }else if(event.getType().equals("buy")){
               _waitWindow.getWaitWindowUI().getWaitText().
                       setText(_waitWindow.getWindowText() + "Buy: " + event.getReason());
               _buyPlaceWindow.getBuyPlaceWindowUI().getMainFrame().setVisible(true);
           }else if(event.getType().equals("rent")){
               _waitWindow.getWaitWindowUI().getWaitText().
                       setText(_waitWindow.getWindowText() + "Rent: " + event.getReason());
           }else if(event.getType().equals("move")){
               _waitWindow.getWaitWindowUI().getWaitText().
                       setText(_waitWindow.getWindowText() + "Move: " + event.getReason());
           }
           return "";
       });
    }
}
