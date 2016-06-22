package banks.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by Jana Mareike on 01.05.2016.
 */
@Entity
@Table(name = "Account")
public class Account {

    @Id
    @Column(name = "id")
    // @Expose on a field you're telling Gson to include that property into your JSON String
    @Expose(serialize = false)
    private UUID id;

    @Column(name = "player")
    @Expose
    private String player;

    @Column(name = "saldo")
    @Expose
    private int saldo;


    // leerer Konstruktor notwendig, weil gson das braucht, sonst Probleme mit ID
    public Account(){
    }

    public Account(String playerUri, int saldo) {
        this.id = UUID.randomUUID();
        this.player = playerUri;
        this.saldo = saldo;
    }

    public UUID getId() {
        return id;
    }

    public String getPlayer() {
        return player;
    }

    public void addSaldo(int saldo) {
        this.saldo += saldo;
    }
    public void subtractSaldo(int saldo) { this.saldo -= saldo; }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account accounts = (Account) o;

        if (saldo != accounts.saldo) return false;
        if (id != null ? !id.equals(accounts.id) : accounts.id != null) return false;
        return player.equals(accounts.player);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + player.hashCode();
        result = 31 * result + saldo;
        return result;
    }
}