package banks.service;

import banks.model.*;
import banks.repo.BankRepo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.util.List;
import java.util.Set;

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
            // list of available banks
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
//            String accounts = request.queryParams("accounts");
//            String transfers = request.queryParams("transfers");
//
//            URI accountsUri = null;
//            URI transfersUri = null;
//
//            if (accounts != null) {accountsUri = new URI(accounts);}
//            if (transfers != null) {transfersUri = new URI(transfers);}
//
//            result.addProperty("accounts", bank.getAccounts().toString());
//            result.addProperty("transfers", bank.getTransfers().toString());

            bank = new Bank();
            System.out.println("im post: " + gson.toJson(bank));
            bank = bankRepo.saveBank(bank);

            if (bank == null) {
                response.status(500);
                response.type("application/json");
                return "";
            }

            String uri = "/banks/" + bank.getId();
            JsonObject result = new JsonObject();

            result.addProperty("id", uri);

            response.status(201);
            response.type("application/json");
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
//            TODO
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
            // list of transfers
            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return gson.toJson("bank not found");
            }

            Set<Transfer> transfers = bank.getTransfers();

            System.out.println("" + transfers.toString());

            response.status(200);
            response.type("application/json");
            return gson.toJson(transfers);
        });

        get("/banks/:bankId/transfers/:transferId", (request, response) -> {
            // Gets a specific transfer
            bank = bankRepo.findBank(request.params(":bankId"));
            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return gson.toJson("bank not found");
            }

            String transferId = request.params(":transferId");
            Set<Transfer> transfers = bank.getTransfers();
            Transfer transfer = null;
            for (Transfer t : transfers) {
                if (t.getId().toString().equals(transferId)) {
                    transfer = t;
                }
            }

            if (transfer == null) {
                response.status(404);
                response.type("application/json");
                return gson.toJson("transfer not found");
            }

            String uriTransfer = "/banks/" + bank.getId() + "/transfers/" + transfer.getId();
            String uriTo = "/banks/" + bank.getId() + "/accounts/" + transfer.getTo();
            String uriFrom = "/banks/" + bank.getId() + "/accounts/" + transfer.getFrom();

            JsonObject result = new JsonObject();
            result.addProperty("id", uriTransfer);
            result.addProperty("from", uriTo);
            result.addProperty("to", uriFrom);
            result.addProperty("amount", transfer.getAmount());
            result.addProperty("reason", transfer.getReason());

            response.status(200);
            response.type("application/json");
            return result;
        });

        post("/banks/:bankid/transfer/from/:from/to/:to/:amount", (request, response) -> {
            // creates a new bank transfer from an account id to another
            bank = bankRepo.findBank(request.params(":bankId"));
            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return gson.toJson("bank not found");
            }

            Account accountTo = bankRepo.findAccounts(request.params(":to"));
            Account accountFrom = bankRepo.findAccounts(request.params(":from"));
            if (accountTo == null || accountFrom == null) {
                response.status(404);
                response.type("application/json");
                return gson.toJson("account not found");
            }
            if (accountFrom.equals(accountTo)) {
                response.status(406);
                response.type("application/json");
                return gson.toJson("transfer with yourself not possible");
            }

            int amount = Integer.parseInt(request.params(":amount"));
            if (amount <= 0) {
                response.status(406);
                response.type("application/json");
                return gson.toJson("negative or neutral transfer not possible");
            }

            String reason = request.queryParams("description");
            if (reason.length() == 0) {
                response.status(406);
                response.type("application/json");
                return gson.toJson("you need to enter a reason as query param for the transfer");
            }

            Transfer transfer = bankRepo.transferFromAccountToAccount(bank, accountFrom, accountTo, amount, reason);

            String uriTransfer = "/banks/" + bank.getId() + "/transfers/" + transfer.getId();
            String uriTo = "/banks/" + bank.getId() + "/accounts/" + accountTo.getId();
            String uriFrom = "/banks/" + bank.getId() + "/accounts/" + accountFrom.getId();

            JsonObject result = new JsonObject();
            result.addProperty("id", uriTransfer);
            result.addProperty("from", uriTo);
            result.addProperty("to", uriFrom);
            result.addProperty("amount", amount);
            result.addProperty("reason", reason);

            response.status(201);
            response.type("application/json");
            return result;
        });

        post("/banks/:bankid/transfer/to/:to/:amount", (request, response) -> {
            // creates a new bank transfer from the bank itself to an account
            bank = bankRepo.findBank(request.params(":bankId"));
            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return gson.toJson("bank not found");
            }

            Account account = bankRepo.findAccounts(request.params(":to"));
            if (account == null) {
                response.status(404);
                response.type("application/json");
                return gson.toJson("account not found");
            }

            int amount = Integer.parseInt(request.params(":amount"));
            if (amount <= 0) {
                response.status(406);
                response.type("application/json");
                return gson.toJson("negative or neutral transfer not possible");
            }

            String reason = request.queryParams("description");
            if (reason.length() == 0) {
                response.status(406);
                response.type("application/json");
                return gson.toJson("you need to enter a reason as query param for the transfer");
            }

            Transfer transfer = bankRepo.transferFromBankToAccount(bank, account, amount, reason);

            String uriTransfer = "/banks/" + bank.getId() + "/transfers/" + transfer.getId();
            String uriFrom = "/banks/" + bank.getId() + "/accounts/" + bank.getDummyAccount().getId();
            String uriTo = "/banks/" + bank.getId() + "/accounts/" + account.getId();

            JsonObject result = new JsonObject();
            result.addProperty("id", uriTransfer);
            result.addProperty("from", uriTo);
            result.addProperty("to", uriFrom);
            result.addProperty("amount", amount);
            result.addProperty("reason", reason);

            response.status(201);
            response.type("application/json");
            return result;

        });

        post("/banks/:bankid/transfer/from/:from/:amount", (request, response) -> {
            // creates a new bank transfer from the bank itself to an account
            bank = bankRepo.findBank(request.params(":bankId"));
            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return gson.toJson("bank not found");
            }

            Account account = bankRepo.findAccounts(request.params(":from"));
            if (account == null) {
                response.status(404);
                response.type("application/json");
                return gson.toJson("account not found");
            }

            int amount = Integer.parseInt(request.params(":amount"));
            if (amount <= 0) {
                response.status(406);
                response.type("application/json");
                return gson.toJson("negative or neutral transfer not possible");
            }

            String reason = request.queryParams("description");
            if (reason.length() == 0) {
                response.status(406);
                response.type("application/json");
                return gson.toJson("you need to enter a reason as query param for the transfer");
            }

            Transfer transfer = bankRepo.transferFromAccountToBank(bank, account, amount, reason);

            String uriTransfer = "/banks/" + bank.getId() + "/transfers/" + transfer.getId();
            String uriFrom = "/banks/" + bank.getId() + "/accounts/" + account.getId();
            String uriTo = "/banks/" + bank.getId() + "/accounts/" + bank.getDummyAccount().getId();

            JsonObject result = new JsonObject();
            result.addProperty("id", uriTransfer);
            result.addProperty("from", uriTo);
            result.addProperty("to", uriFrom);
            result.addProperty("amount", amount);
            result.addProperty("reason", reason);

            response.status(201);
            response.type("application/json");
            return result;

        });

//        TODO ??? - atm we are not doing this since there is no need due to hibernate
//        post("/banks/:bankid/transaction", (request, response) -> {
//          // begins a new transaction  TO DO!!
//            bank = bankRepo.findBank(request.params(":bankId"));
//
//            if (bank == null) {
//                response.status(404);
//                response.type("application/json");
//                return "";
//            }
//
//            bankRepo.transactionBegin();
//
//            response.status(201);
//            response.type("application/json");
//            return gson.toJson("");
//
//        });
//        get("/banks/:bankid/transaction/:tid", (request, response) -> {
//            // returns the state of the transaction
//            bank = bankRepo.findBank(request.params(":bankId"));
//            if (bank == null) {
//                response.status(404);
//                response.type("application/json");
//                return gson.toJson("bank not found");
//            }
//
//            Transfer transfer = bankRepo.findTransferBeta(request.params(":tid"));
//            if (transfer == null) {
//                response.status(404);
//                response.type("application/json");
//                return gson.toJson("transaction not found");
//            }
//
//            bankRepo.transactionBegin();
//
//            response.status(201);
//            response.type("application/json");
//            return gson.toJson("");
//        });
////
////        put("/banks/:bankid/transaction/:tid", (request, response) -> {
////// commits/readies the transaction
////        });
////
//        delete("/banks/:bankid/transaction/:tid", (request, response) -> {
//// abort/rollback an transaction
//            bank = bankRepo.findBank(request.params(":bankId"));
//
//            if (bank == null) {
//                response.status(404);
//                response.type("application/json");
//                return "";
//            }
//
//            Transaction transaction = bankRepo.findTransaction(request.params(":tid"));
//
//            if (transaction == null) {
//                response.status(404);
//                response.type("application/json");
//                return "";
//            }
//
//            bankRepo.deleteTransaction(transaction);
//
//            response.status(201);
//            response.type("application/json");
//            return gson.toJson("");
//        });

        get("/banks/:bankid/accounts", (request, response) -> {
            // List of available account
            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return gson.toJson("Bank not found");
            }

            Set<Account> accounts = bank.getAccounts();
            System.out.println("" + accounts);

            response.status(200);
            response.type("application/json");
            return gson.toJson(accounts);
        });

        post("/banks/:bankid/accounts", (request, response) -> {
            // creates a bank account
            bank = bankRepo.findBank(request.params(":bankId"));

            if (bank == null) {
                response.status(404);
                response.type("application/json");
                return gson.toJson("Bank not found");
            }

            String playerUri = request.queryParams("player");
            String saldo = request.queryParams("saldo");

            for (Account a : bank.getAccounts()) {
                if (a.getPlayer().equals(playerUri)) {
                    response.status(409);
                    response.type("application/json");
                    return "player already got a bank account for given bank";
                }
            }

            Account account = null;
            if (playerUri != null && saldo != null) {
                account = new Account(playerUri, Integer.parseInt(saldo));
                bank.addAccount(account);
            }

            String uriAccount = "/banks/" + bank.getId() + "/accounts/" + account.getId();

            JsonObject result = new JsonObject();
            result.addProperty("id", uriAccount);
            result.addProperty("player", playerUri);
            result.addProperty("saldo", saldo);

            response.status(201);
            response.type("application/json");
            return result;
        });

        get("/banks/:bankid/accounts/:accountid", (request, response) -> {
            // returns account for the given bank
            Account account = bankRepo.findAccounts(request.params(":accountid"));

             if (account == null) {
                 response.status(404);
                 response.type("application/json");
                 return gson.toJson("account not found");
             }

            response.status(201);
            response.type("application/json");
            return gson.toJson(account);
        });
    }
}
