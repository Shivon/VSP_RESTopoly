package banks.repo;

import banks.model.Accounts;
import banks.model.Bank;
import banks.model.Transaction;
import banks.model.Transfers;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.*;

/**
 * Created by jana on 29.04.16.
 */

public class BankRepo {

    private EntityManager entityManager = Persistence.createEntityManagerFactory("bank").createEntityManager();


    public List<Bank> allBanks() {
        try {
            entityManager.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<Bank> banks = entityManager.createQuery("select b from Bank b").getResultList();
            entityManager.getTransaction().commit();
            return banks;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Bank findBank(String id) {
        try {
            entityManager.getTransaction().begin();
            Bank bank = entityManager.find(Bank.class, id);
            entityManager.getTransaction().commit();
            return bank;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Bank saveBank(Bank bank) {
        try {
            entityManager.getTransaction().begin();
            bank = entityManager.merge(bank);
            entityManager.getTransaction().commit();
            System.out.println("Bank wurde gespeichert");
            return bank;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.out.println("Bank konnte nicht gespeichert werden");
            return null;
        }
    }

    public void deleteBank(Bank bank) {
        try {
            entityManager.getTransaction().begin();
            System.out.println("vor dem Löschen: " + bank);
            entityManager.remove(bank);
            System.out.println("repo: bank wurde gelöscht");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public Transfers findTransfers(String transferId){
        try {
            entityManager.getTransaction().begin();
            Transfers transfer = entityManager.find(Transfers.class, transferId);
            entityManager.getTransaction().commit();
            return transfer;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Accounts findAccounts(String accountId){
        try {
            entityManager.getTransaction().begin();
            Accounts account = entityManager.find(Accounts.class, accountId);
            entityManager.getTransaction().commit();
            return account;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public List<Accounts> allAccounts(){
        try {
            entityManager.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<Accounts> accountsList = entityManager.createQuery("select a from Accounts a").getResultList();
            entityManager.getTransaction().commit();
            return accountsList;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public void transferFromBankToAccount(Accounts account, int amount){
        try {
            entityManager.getTransaction().begin();
            if(amount > 0){
            account.addSaldo(amount);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public void transferToBankFromAccount(Accounts account, int amount){
        try {
            entityManager.getTransaction().begin();
            if(amount > 0){
                amount = amount * -1;
                account.addSaldo(amount);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public void transferFromAccountToAccount(Accounts from, Accounts to, int amount){
        try {
            entityManager.getTransaction().begin();
            if(from.getSaldo() < amount ){
                amount = from.getSaldo();
            }
            from.addSaldo(amount * -1);
            to.addSaldo(amount);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public Transaction findTransaction(String transactionId){
        try {
            entityManager.getTransaction().begin();
            Transaction transaction = entityManager.find(Transaction.class, transactionId);
            entityManager.getTransaction().commit();
            return transaction;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public void transactionBegin(){
        try{
            entityManager.getTransaction().begin();
            Transaction transaction = new Transaction();
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }


    public void deleteTransaction(Transaction transaction){
        try {
            entityManager.getTransaction().begin();
            System.out.println("vor dem Löschen: " + transaction);
            entityManager.remove(transaction);
            System.out.println("repo: bank wurde gelöscht");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

}
