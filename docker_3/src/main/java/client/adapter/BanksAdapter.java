package client.adapter;

import client.model.Accounts;
import client.model.Bank;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.URI;

/**
 * Created by Jana Mareike on 25.05.2016.
 */
public class BanksAdapter {

    Gson gson = new Gson();
    Game _game;
    Player _player;
    User _user;
    IPAdresses _ipAdresses;
    Bank _bank;
    Accounts _account;

    public void postAccount(Player player, Game game) throws UnirestException {
        this._player = player;
        this._game = game;
        String bankString = _game.getComponents().getBank();

        this._account = new Accounts();
        _account.setPlayer(URI.create(_player.getUri()));
        _account.setSaldo(4000);
        Unirest.post( bankString + "/accounts").body(this.gson.toJson(_account)).getBody();
    }

//    public Bank getBank(String bankID) throws UnirestException {
//
//        String bankString =  Unirest.get("http://" +  _ipAdresses.banksIP()
//                + "/banks/" + _game.getComponents().getBank()).asString().getBody();
//
//        Bank bank = gson.fromJson(bankString, Bank.class);
//        return bank;
//    }

    public Accounts getAccount(User user, Game game) throws UnirestException {
        this._user = user;
        this._game = game;
        String bankString = _game.getComponents().getBank();

        String accountString = Unirest.get(bankString + "/accounts/"
                + _user.getName().toLowerCase()).asString().getBody();

        Accounts account = gson.fromJson(accountString, Accounts.class);
        return account;

    }

}
