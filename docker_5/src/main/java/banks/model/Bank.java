package banks.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by jana on 29.04.16.
 */
@Entity
@Table(name = "Bank")
public class Bank {
    @Id
    @Column(name = "id")
    // @Expose on a field you're telling Gson to include that property into your JSON String
    @Expose(serialize = false)
    private UUID id;

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "accounts")
    @Expose
    private Set<Account> accounts;

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "transfers")
    @Expose
    private Set<TransferBeta> transfers;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "dummyAccount")
    private Account dummyAccount;

    // leerer Konstruktor notwendig, weil gson das braucht, sonst Probleme mit ID
    public Bank(){
        this.id = UUID.randomUUID();
        this.accounts = new HashSet<>();
        this.transfers = new HashSet<>();
        this.dummyAccount = new Account();
    }

    public UUID getId() {
        return id;
    }

    public Account getDummyAccount() {
        return this.dummyAccount;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void deleteAccount(Account account) {
        this.accounts.remove(account);
    }

    public Set<TransferBeta> getTransfers() {
        return transfers;
    }

    public void setTransfers(Set transfers) {
        this.transfers = transfers;
    }

    public void addTransfer(TransferBeta transfer) {
        this.transfers.add(transfer);
    }

    public void deleteTransfer(TransferBeta transfer) {
        this.transfers.remove(transfer);
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        Bank bank = (Bank) object;

        if (id != null ? !id.equals(bank.id) : bank.id != null) return false;
        if (accounts != null ? !accounts.equals(bank.accounts) : bank.accounts != null) return false;
        if (transfers != null ? !transfers.equals(bank.transfers) : bank.transfers != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
        result = 31 * result + (transfers != null ? transfers.hashCode() : 0);
        return result;
    }
}
