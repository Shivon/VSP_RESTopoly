package banks.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "Transfer")
public class Transfer {
    @Id
    @Column(name = "id")
    // @Expose on a field you're telling Gson to include that property into your JSON String
    @Expose(serialize = false)
    private UUID id;

    @Column(name = "fromAccount")
    @Expose
    private String fromAccount;

    @Column(name = "toAccount")
    @Expose
    private String toAccount;

    @Column(name = "amount")
    @Expose
    private int amount;

    @Column(name = "reason")
    @Expose
    private String reason;

    // leerer Konstruktor notwendig, weil gson das braucht, sonst Probleme mit ID
    public Transfer(){
    }

    public Transfer(String fromAccount, String toAccount, int amount, String reason) {
        this.id = UUID.randomUUID();
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.reason = reason;
    }

    public UUID getId() {
        return id;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String from) {
        this.fromAccount = from;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String to) {
        this.toAccount = to;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transfer transfers = (Transfer) o;

        if (amount != transfers.amount) return false;
        if (id != null ? !id.equals(transfers.id) : transfers.id != null) return false;
        if (fromAccount != null ? !fromAccount.equals(transfers.fromAccount) : transfers.fromAccount != null) return false;
        if (toAccount != null ? !toAccount.equals(transfers.toAccount) : transfers.toAccount != null) return false;
        return reason.equals(transfers.reason);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fromAccount != null ? fromAccount.hashCode() : 0);
        result = 31 * result + (toAccount != null ? toAccount.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + reason.hashCode();
        return result;
    }
}
