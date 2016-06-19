package client;

import client.adapter.*;
import client.logic.*;
import client.model.Client;
import client.model.User;
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
        GamesLogic gamesLogic = new GamesLogic(gamesAdapter, userLogic);
        PlayerLogic playerLogic = new PlayerLogic(playerAdapter, gamesLogic, userLogic);
        TurnToRollLogic turnToRollLogic = new TurnToRollLogic(diceAdapter, playerAdapter, gamesLogic, userLogic);
//        BuyPlaceLogic buyPlaceLogic = new BuyPlaceLogic();

        UserWindowUI userWindowUI = new UserWindowUI();
        GamesWindowUI gamesWindowUI = new GamesWindowUI();
        NewGameWindowUI newGameWindowUI = new NewGameWindowUI();
        PlayerLoginWindowUI playerLoginWindowUI = new PlayerLoginWindowUI();
        StartGameWindowUI startGameWindowUI = new StartGameWindowUI();
        WaitWindowUI waitWindowUI = new WaitWindowUI();
        TurnToRollWindowUI turnToRollWindowUI = new TurnToRollWindowUI();
//        BuyPlaceWindowUI buyPlaceWindowUI = new BuyPlaceWindowUI();

        TurnToRollWindow turnToRollWindow = new TurnToRollWindow(waitWindowUI, turnToRollWindowUI,
               playerLogic, gamesLogic, userLogic, turnToRollLogic);
        WaitWindow waitWindow = new WaitWindow(waitWindowUI, waitLogic, gamesLogic, userLogic);
        StartGameWindow startGameWindow = new StartGameWindow(gamesLogic, startGameWindowUI, waitWindow);
        PlayerLogInWindow playerLogInWindow = new PlayerLogInWindow(playerLoginWindowUI, userLogic,
                playerLogic, startGameWindow, gamesLogic);
        NewGameWindow newGameWindow = new NewGameWindow(newGameWindowUI, playerLogInWindow, gamesLogic);
        GamesWindow gamesWindow = new GamesWindow(gamesWindowUI, gamesLogic, newGameWindow, playerLogInWindow, playerLogic);
//        BuyPlaceWindow buyPlaceWindow = new BuyPlaceWindow();


        ClientService clientService = new ClientService(waitLogic, turnToRollWindow, waitWindow);

//        ClientService clientService = new ClientService(null);
//        clientService.setWaitLogic(waitLogic);
        new UserWindow(userWindowUI, userLogic, gamesWindow);
    }
}
