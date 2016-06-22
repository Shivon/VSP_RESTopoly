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

    public TransferBeta findTransferBeta(String transferId) {
        try {
            entityManager.getTransaction().begin();
            TransferBeta transfer = entityManager.find(TransferBeta.class, transferId);
            entityManager.getTransaction().commit();
            return transfer;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Transfer findTransfers(String transferId){
        try {
            entityManager.getTransaction().begin();
            Transfer transfer = entityManager.find(Transfer.class, transferId);
            entityManager.getTransaction().commit();
            return transfer;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public Account findAccounts(String accountId){
        try {
            entityManager.getTransaction().begin();
            Account account = entityManager.find(Account.class, accountId);
            entityManager.getTransaction().commit();
            return account;
        } catch (Exception e) {
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
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public TransferBeta transferFromBankToAccount(Bank bank, Account account, int amount, String reason){
        try {
            entityManager.getTransaction().begin();
            if (amount > 0) {
                account.addSaldo(amount);
            }
            TransferBeta transfer = new TransferBeta(bank.getDummyAccount().getId(), account.getId(), amount, reason);
            bank.addTransfer(transfer);
            entityManager.getTransaction().commit();
            return transfer;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public TransferBeta transferFromAccountToBank(Bank bank, Account account, int amount, String reason){
        try {
            entityManager.getTransaction().begin();
            if (account.getSaldo() < amount ) {
                amount = account.getSaldo();
            }
            if (amount > 0) {
                account.subtractSaldo(amount);
            }
            TransferBeta transfer = new TransferBeta(account.getId(), bank.getDummyAccount().getId(), amount, reason);
            bank.addTransfer(transfer);
            entityManager.getTransaction().commit();
            return transfer;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public TransferBeta transferFromAccountToAccount(Bank bank, Account from, Account to, int amount, String reason){
        try {
            entityManager.getTransaction().begin();
            if (from.getSaldo() < amount ) {
                amount = from.getSaldo();
            }
            from.subtractSaldo(amount);
            to.addSaldo(amount);
            TransferBeta transfer = new TransferBeta(from.getId(), to.getId(), amount, reason);
            bank.addTransfer(transfer);
            entityManager.getTransaction().commit();
            return transfer;
        } catch (Exception e) {
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
