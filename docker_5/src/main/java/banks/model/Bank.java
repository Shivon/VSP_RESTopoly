package banks.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.net.URI;
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
    private String id;

    // required params

    @Column(name = "accounts")
    @Expose
    private URI accounts;

    @Column(name = "transfers")
    @Expose
    private URI transfers;


    // leerer Konstruktor notwendig, weil gson das braucht, sonst Probleme mit ID
    public Bank(){
    }

    public Bank(URI accounts, URI transfers) {
        this.id = UUID.randomUUID().toString();
        this.accounts = accounts;
        this.transfers = transfers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URI getAccounts() {
        return accounts;
    }

    public void setAccounts(URI accounts) {
        this.accounts = accounts;
    }

    public URI getTransfers() {
        return transfers;
    }

    public void setTransfers(URI transfers) {
        this.transfers = transfers;
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
