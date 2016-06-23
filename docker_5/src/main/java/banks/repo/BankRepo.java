package banks.repo;

import banks.model.*;

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
            System.out.println(e);
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Bank findBank(String id) {
        try {
            entityManager.getTransaction().begin();
            UUID uuid = UUID.fromString(id);
            Bank bank = entityManager.find(Bank.class, uuid);
            entityManager.getTransaction().commit();
            return bank;
        } catch (Exception e) {
            System.out.println(e);
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Bank saveBank(Bank bank) {
        try {
            entityManager.getTransaction().begin();
            bank = entityManager.merge(bank);
            System.out.println("BankID nach Merge in saveBank" + bank.getId().toString());
            entityManager.getTransaction().commit();
            System.out.println("Bank wurde gespeichert");
            return bank;
        } catch (Exception e) {
            System.out.println(e);
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
            System.out.println(e);
            entityManager.getTransaction().rollback();
        }
    }

    public Transfer findTransfer(String transferId) {
        try {
            entityManager.getTransaction().begin();
            UUID uuid = UUID.fromString(transferId);
            Transfer transfer = entityManager.find(Transfer.class, uuid);
            entityManager.getTransaction().commit();
            return transfer;
        } catch (Exception e) {
            System.out.println(e);
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Account findAccounts(String accountId){
        try {
            entityManager.getTransaction().begin();
            UUID uuid = UUID.fromString(accountId);
            Account account = entityManager.find(Account.class, uuid);
            entityManager.getTransaction().commit();
            return account;
        } catch (Exception e) {
            System.out.println(e);
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public List<Account> allAccounts(){
        try {
            entityManager.getTransaction().begin();
            @SuppressWarnings("unchecked")
            List<Account> accountsList = entityManager.createQuery("select a from Account a").getResultList();
            entityManager.getTransaction().commit();
            return accountsList;
        } catch (Exception e) {
            System.out.println(e);
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Transfer transferFromBankToAccount(Bank bank, Account account, int amount, String reason){
        try {
            entityManager.getTransaction().begin();
            if (amount > 0) {
                account.addSaldo(amount);
            }
            Transfer transfer = new Transfer(bank.getDummyAccount().getId().toString(), account.getId().toString(), amount, reason);
            bank.addTransfer(transfer);
            entityManager.getTransaction().commit();
            return transfer;
        } catch (Exception e) {
            System.out.println(e);
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Transfer transferFromAccountToBank(Bank bank, Account account, int amount, String reason){
        try {
            entityManager.getTransaction().begin();
            if (account.getSaldo() < amount ) {
                amount = account.getSaldo();
            }
            if (amount > 0) {
                account.subtractSaldo(amount);
            }
            Transfer transfer = new Transfer(account.getId().toString(), bank.getDummyAccount().getId().toString(), amount, reason);
            bank.addTransfer(transfer);
            entityManager.getTransaction().commit();
            return transfer;
        } catch (Exception e) {
            System.out.println(e);
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Transfer transferFromAccountToAccount(Bank bank, Account from, Account to, int amount, String reason){
        try {
            entityManager.getTransaction().begin();
            if (from.getSaldo() < amount ) {
                amount = from.getSaldo();
            }
            from.subtractSaldo(amount);
            to.addSaldo(amount);
            Transfer transfer = new Transfer(from.getId().toString(), to.getId().toString(), amount, reason);
            bank.addTransfer(transfer);
            entityManager.getTransaction().commit();
            return transfer;
        } catch (Exception e) {
            System.out.println(e);
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Transaction findTransaction(String transactionId){
        try {
            entityManager.getTransaction().begin();
            Transaction transaction = entityManager.find(Transaction.class, transactionId);
            entityManager.getTransaction().commit();
            return transaction;
        } catch (Exception e) {
            System.out.println(e);
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
            System.out.println(e);
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
            System.out.println(e);
            entityManager.getTransaction().rollback();
        }
    }

}
