package de.haw.vs.escr.broker.adapter.banks.rest;

/**
 * Created by Christian on 21.06.2016.
 */
public class BankRestModel {
    private final String BANKS = "/banks";
    private final String TRANSFER = "/transfer";
    private final String FROM = "/from";
    private final String TO = "/to";
    private final String AUTHORITY;
    private final String PROTOCOL = "http://";

    public BankRestModel(String AUTHORITY) {
        this.AUTHORITY = AUTHORITY;
    }

    public String getAddress() {
        return AUTHORITY;
    }

    public String getAddress(String query) {
        return this.getAddress() + query;
    }

    public String getFromRoute(int bankId, String from, int amount) {
        return this.getAddress() + this.TRANSFER + this.FROM + "/" + from + "/" + amount;
    }

    public String getFromToAmountRoute(int bankId, String owner, String player, int amount) {
        return this.getAddress() + this.TRANSFER + this.FROM + "/" + player + this.TO + "/" + owner + "/" + amount;
    }
}
