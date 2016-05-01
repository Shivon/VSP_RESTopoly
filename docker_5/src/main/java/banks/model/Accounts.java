package banks.model;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URI;
import java.util.UUID;

/**
 * Created by Jana Mareike on 01.05.2016.
 */
@Entity
@Table(name = "Accounts")
public class Accounts {

    @Id
    @Column(name = "id")
    // @Expose on a field you're telling Gson to include that property into your JSON String
    @Expose(serialize = false)
    private String id;

    @Column(name = "player")
    @Expose
    private URI player;

    @Column(name = "saldo")
    @Expose
    private int saldo;


    // leerer Konstruktor notwendig, weil gson das braucht, sonst Probleme mit ID
    public Accounts(){
    }

    public Accounts(URI player, int saldo) {
        this.id = UUID.randomUUID().toString();
        this.player = player;
        this.saldo = saldo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URI getPlayer() {
        return player;
    }

    public void addSaldo(int saldo) {
        this.saldo += saldo;
    }

    public void setPlayer(URI player) {
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

        Accounts accounts = (Accounts) o;

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