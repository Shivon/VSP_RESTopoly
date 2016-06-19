package client.logic;

import client.adapter.BanksAdapter;
import client.model.Accounts;
import client.model.User;
import client.model.gameModels.Game;
import client.model.gameModels.Player;
import client.model.gameModels.Ready;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Jana Mareike on 07.06.2016.
 */
public class WaitLogic {

    private BanksAdapter _banksAdapter;

    public WaitLogic(BanksAdapter banksAdapter) throws UnirestException {
        _banksAdapter = banksAdapter;
    }

    public void playerTurn(Player player) throws UnirestException {
        player.setReady(new Ready(false));
    }

    public int getSaldo(Game game, User user) throws UnirestException {
        System.out.println("User IM SALDO: " + user);
        System.out.println("GAME IM SALDO: " + game);
        Accounts account = _banksAdapter.getAccount(user, game);
        return account.getSaldo();
    }
}
