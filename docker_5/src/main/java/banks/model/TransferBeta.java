package banks.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "TransferBeta")
public class TransferBeta {
    @Id
    @Column(name = "id")
    // @Expose on a field you're telling Gson to include that property into your JSON String
    @Expose(serialize = false)
    private UUID id;

    @Column(name = "from")
    @Expose
    private UUID from;

    @Column(name = "to")
    @Expose
    private UUID to;

    @Column(name = "amount")
    @Expose
    private int amount;

    @Column(name = "reason")
    @Expose
    private String reason;

    // leerer Konstruktor notwendig, weil gson das braucht, sonst Probleme mit ID
    public TransferBeta(){
    }

    public TransferBeta(UUID from, UUID to, int amount, String reason) {
        this.id = UUID.randomUUID();
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.reason = reason;
    }

    public UUID getId() {
        return id;
    }

    public UUID getFrom() {
        return from;
    }

    public void setFrom(UUID from) {
        this.from = from;
    }

    public UUID getTo() {
        return to;
    }

    public void setTo(UUID to) {
        this.to = to;
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

        TransferBeta transfers = (TransferBeta) o;

        if (amount != transfers.amount) return false;
        if (id != null ? !id.equals(transfers.id) : transfers.id != null) return false;
        if (from != null ? !from.equals(transfers.from) : transfers.from != null) return false;
        if (to != null ? !to.equals(transfers.to) : transfers.to != null) return false;
        return reason.equals(transfers.reason);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + amount;
        result = 31 * result + reason.hashCode();
        return result;
    }
}
