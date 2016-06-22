package client;

import client.adapter.*;
import client.logic.*;
import client.model.Client;
import client.model.User;
import client.model.boardModels.Place;
import client.model.gameModels.Player;
import client.service.ClientService;
import client.view.*;
import clientUI.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.utils.ClientFactory;

/**
 * Created by Jana Mareike on 26.04.2016.
 */
public class MyMainClass {

    public MyMainClass(){

    }

    public static void main(String[] args) throws UnirestException {

        IPAdresses ipAdresses = new IPAdresses();

        UserAdapter userAdapter = new UserAdapter();
        BanksAdapter banksAdapter = new BanksAdapter();
        PlayerAdapter playerAdapter = new PlayerAdapter(ipAdresses);
        DiceAdapter diceAdapter = new DiceAdapter();
        BoardsAdapter boardAdapter = new BoardsAdapter();
        BrokerAdapter brokerAdapter = new BrokerAdapter();
        GamesAdapter gamesAdapter = new GamesAdapter(ipAdresses);

        WaitLogic waitLogic = new WaitLogic(banksAdapter);
        UserLogic userLogic = new UserLogic(userAdapter, ipAdresses);
        GamesLogic gamesLogic = new GamesLogic(gamesAdapter, userLogic, ipAdresses);
        PlayerLogic playerLogic = new PlayerLogic(playerAdapter, gamesLogic, userLogic);
        TurnToRollLogic turnToRollLogic = new TurnToRollLogic(diceAdapter, playerAdapter, gamesLogic, userLogic);
        BuyLogic buyLogic = new BuyLogic(brokerAdapter, turnToRollLogic);

        UserWindowUI userWindowUI = new UserWindowUI();
        GamesWindowUI gamesWindowUI = new GamesWindowUI();
        NewGameWindowUI newGameWindowUI = new NewGameWindowUI();
        PlayerLoginWindowUI playerLoginWindowUI = new PlayerLoginWindowUI();
        StartGameWindowUI startGameWindowUI = new StartGameWindowUI();
        WaitWindowUI waitWindowUI = new WaitWindowUI();
        TurnToRollWindowUI turnToRollWindowUI = new TurnToRollWindowUI();
        BuyPlaceWindowUI buyPlaceWindowUI = new BuyPlaceWindowUI();

        WaitWindow waitWindow = new WaitWindow(waitWindowUI, waitLogic, gamesLogic, userLogic);
        TurnToRollWindow turnToRollWindow = new TurnToRollWindow(turnToRollWindowUI, playerLogic, gamesLogic,
                 userLogic, turnToRollLogic, boardAdapter, buyLogic, waitWindow, buyPlaceWindowUI,
                waitLogic, brokerAdapter, playerAdapter, banksAdapter);
        StartGameWindow startGameWindow = new StartGameWindow(gamesLogic, startGameWindowUI, waitWindow, waitLogic, userLogic);
        PlayerLogInWindow playerLogInWindow = new PlayerLogInWindow(playerLoginWindowUI, userLogic,
                playerLogic, waitWindow, gamesLogic, waitLogic);
        NewGameWindow newGameWindow = new NewGameWindow(newGameWindowUI, playerLogInWindow, gamesLogic, playerLogic);
        GamesWindow gamesWindow = new GamesWindow(gamesWindowUI, gamesLogic, newGameWindow, playerLogInWindow, playerLogic);
        BuyPlaceWindow buyPlaceWindow = new BuyPlaceWindow(buyPlaceWindowUI, waitWindow, waitLogic, gamesLogic,userLogic
                , brokerAdapter, buyLogic, playerAdapter, banksAdapter, boardAdapter);


        ClientService clientService = new ClientService(waitLogic, turnToRollWindow, waitWindow, buyPlaceWindow, turnToRollLogic,
                                        userLogic, gamesLogic);

        new UserWindow(userWindowUI, userLogic, gamesWindow);
    }
}
