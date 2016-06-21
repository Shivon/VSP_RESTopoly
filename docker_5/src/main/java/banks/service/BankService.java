package banks.service;

import banks.model.Accounts;
import banks.model.Bank;
import banks.model.Transaction;
import banks.repo.BankRepo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by jana on 30.04.16.
 */
public class BankService {
    private Gson gson = new Gson();
    private BankRepo bankRepo = new BankRepo();
    private Bank bank;
    private List<Bank> bankList;

    public BankService() {
        System.out.println("hallo2");
        get("/banks", (request, response) -> {
//            list of availabe banks

            bankList = bankRepo.allBanks();
            System.out.println(bankList);
            for (Bank b : bankList) {
                System.out.println(gson.toJson(b));
            }

            if (bankList == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            response.status(200);
            response.type("application/json");
            return gson.toJson(bankList);
        });

        post("/banks", (request, response) -> {
//            creates a new bank
            String accounts = request.queryParams("accounts");
            String transfers = request.queryParams("transfers");

            URI accountsUri = null;
            URI transfersUri = null;

            if (accounts != null) {accountsUri = new URI(accounts);}
            if (transfers != null) {transfersUri = new URI(transfers);}

            bank = new Bank(accountsUri,transfersUri);
            System.out.println("im post: " + gson.toJson(bank).toString());
            bank = bankRepo.saveBank(bank);

            if (bank == null) {
                response.status(500);
                response.type("application/json");
                return "";
            }

            String uri = "/banks/" + bank.getId();
            JsonObject result = new JsonObject();

            result.addProperty("id", uri);
            result.addProperty("accounts", bank.getAccounts().toString());
            result.addProperty("transfers", bank.getTransfers().toString());

            response.status(201);
            response.type("application/json");
//            return gson.toJson(bank);
            return result;
        });

        get("/banks/:bankId", (request, response) -> {
            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            response.status(200);
            response.type("application/json");
            return gson.toJson(bank);
        });

        put("/banks/:bankId", (request, response) -> {
//            TO DO
            String accounts = request.queryParams("accounts");
            String transfers = request.queryParams("transfers");

            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

//             URI accountsUri = null;
//             URI transfersUri = null;

            if(accounts != null) {
               URI accountsUri = new URI(accounts);
            }

            if(transfers != null){
                URI transfersUri = new URI(transfers);
           }

            response.status(201);
            response.type("application/json");
            return gson.toJson(bank);

        });

        get("/banks/:bankId/transfers", (request, response) -> {
          //list of available transfers

            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            URI transfers = bank.getTransfers();

            System.out.println("" + transfers);


            response.status(200);
            response.type("application/json");
            return gson.toJson(transfers);
        });

//        get("/banks/:bankId/transfers/:transferId", (request, response) -> {
//            //Gets a <<resourcePathName>>
//            String from = request.queryParams("from"); // "uri of player account or \'bank\'
//            // from where the money comes from"
//            String to = request.queryParams("to"); //"uri of player account or \'bank\'
//            // to where the money goes"
//            String amount = request.queryParams("amount"); // int
//            String reason = request.queryParams("reason");
//        });


        post("/banks/:bankid/transfer/from/:from/to/:to/:amount", (request, response) -> {
// creates a new bank transfer from a account id to an other
            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            Accounts accountTo = bankRepo.findAccounts(request.params(":to"));
            Accounts accountFrom = bankRepo.findAccounts(request.params(":from"));
            int amount = Integer.parseInt(request.params(":amount"));

            if(amount <= 0){
                response.status(406);
                response.type("application/json");
                return gson.toJson("negative transfer not possible");
            }

            if(accountFrom.equals(accountTo)){
                response.status(406);
                response.type("application/json");
                return gson.toJson("transfer with yourself not possible");
            }

            bankRepo.transferFromAccountToAccount(accountFrom, accountTo, amount);

            response.status(201);
            response.type("application/json");
            return gson.toJson("A new bank transfer has been created");
        });

        post("/banks/:bankid/transfer/to/:to/:amount", (request, response) -> {
// creates a new bank transfer from the bank itself

            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            Accounts account = bankRepo.findAccounts(request.params(":to"));
            int amount = Integer.parseInt(request.params(":amount"));

            if(amount <= 0){
                response.status(406);
                response.type("application/json");
                return gson.toJson("negative transfer not possible");
            }

            bankRepo.transferFromBankToAccount(account, amount);

            response.status(201);
            response.type("application/json");
            return gson.toJson("A new bank transfer has been created");

        });

        post("/banks/:bankid/transfer/from/:from/:amount", (request, response) -> {
// creates a new bank transfer to the bank itself
            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            Accounts account = bankRepo.findAccounts(request.params(":to"));
            int amount = Integer.parseInt(request.params(":amount"));

            if(amount <= 0){
                response.status(403);
                response.type("application/json");
                return gson.toJson("insufficient fonds");
            }

            if(account.getSaldo() < amount ){
                amount = account.getSaldo();
//               and the player is dead, delete account?
            }

            bankRepo.transferToBankFromAccount(account, amount);

            response.status(201);
            response.type("application/json");
            return gson.toJson("A new bank transfer has been created");

        });

        post("/banks/:bankid/transaction", (request, response) -> {
// begins a new transaction  TO DO!!
            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            bankRepo.transactionBegin();

            response.status(201);
            response.type("application/json");
            return gson.toJson("");

        });
// TODO ???
        get("/banks/:bankid/transaction/:tid", (request, response) -> {
// returns the state of the transaction
            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            Transaction transaction = bankRepo.findTransaction(request.params(":tid"));

            if (transaction == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            bankRepo.transactionBegin();

            response.status(201);
            response.type("application/json");
            return gson.toJson("");
        });
//
//        put("/banks/:bankid/transaction/:tid", (request, response) -> {
//// commits/readies the transaction
//        });
//
//        TODO???
        delete("/banks/:bankid/transaction/:tid", (request, response) -> {
// abort/rollback an transaction
            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            Transaction transaction = bankRepo.findTransaction(request.params(":tid"));

            if (transaction == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            bankRepo.deleteTransaction(transaction);

            response.status(201);
            response.type("application/json");
            return gson.toJson("");
        });

        get("/banks/:bankid/accounts", (request, response) -> {
// List of available account
            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            URI account = bank.getAccounts();
            System.out.println("" + account);

            response.status(200);
            response.type("application/json");
            return gson.toJson(account);

        });

        post("/banks/:bankid/accounts", (request, response) -> {
        // creates a bank account
            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return "";
            }

            String player = request.queryParams("palyer");
            String saldo = request.queryParams("saldo");

            List<Accounts> accountList = bankRepo.allAccounts();
            for ( Accounts a : accountList) {
                if (a.getPlayer().toString() == player) {
                    response.status(409);
                    response.type("application/json");
                    return "player already got a bank account";
                }
            }

            Accounts account = null;
            if(player != null && saldo != null){
                account = new Accounts();
            }
            response.status(201);
            response.type("application/json");
            return gson.toJson(account + "bank account has been created");
        });

        get("/banks/:bankid/accounts/:accountid", (request, response) -> {
// returns account the saldo of the player
            Accounts account = bankRepo.findAccounts(request.params(":accountid"));

            int saldo = account.getSaldo();
            response.status(201);
            response.type("application/json");
            return gson.toJson(saldo);
        });

    }
}
