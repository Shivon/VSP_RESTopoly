package de.haw.vs.escr.games.restmodel;

/**
 * Created by Christian on 13.06.2016.
 */
public class BankRestModel {
    private final String component;
    private final String service;
    private final String ACCOUNTS = "accounts";

    public BankRestModel(String component, String service) {
        this.component = component;
        this.service = service;
    }

    public String getAccountServiceRoute() {
        return String.format("%s/%s", this.service, this.ACCOUNTS);
    }

    public String getAccountComponentRoute() {
        return String.format("%s/%s", this.component, this.ACCOUNTS);
    }
}
