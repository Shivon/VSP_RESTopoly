package de.haw.vs.escr.boards.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 20.06.2016.
 */
public class FieldNames {
    private List<String> germanNames;
    private List<Integer> nonBuyablePlaces;
    private List<Integer> notUpgradablePlaces;


    public FieldNames(){
        initGermanNames();
        initNonBuyablePlaces();
        initNotUpgradablePlaces();
    }

    private void initNotUpgradablePlaces() {
        notUpgradablePlaces = new ArrayList<>();
        notUpgradablePlaces.add(5);
        notUpgradablePlaces.add(12);
        notUpgradablePlaces.add(15);
        notUpgradablePlaces.add(25);
        notUpgradablePlaces.add(28);
        notUpgradablePlaces.add(35);
    }

    public List<Integer> getNonBuyablePlaces() {
        return nonBuyablePlaces;
    }

    public List<Integer> getNotUpgradablePlaces() {
        return notUpgradablePlaces;
    }

    private void initNonBuyablePlaces() {
        nonBuyablePlaces = new ArrayList<>();
        nonBuyablePlaces.add(0);
        nonBuyablePlaces.add(2);
        nonBuyablePlaces.add(4);
        nonBuyablePlaces.add(7);
        nonBuyablePlaces.add(10);
        nonBuyablePlaces.add(17);
        nonBuyablePlaces.add(20);
        nonBuyablePlaces.add(22);
        nonBuyablePlaces.add(30);
        nonBuyablePlaces.add(33);
        nonBuyablePlaces.add(36);
        nonBuyablePlaces.add(38);
    }

    private void initGermanNames() {
        germanNames = new ArrayList<>();
        germanNames.add("Los");
        germanNames.add("Badstraße");
        germanNames.add("Gemeinschaftsfeld");
        germanNames.add("Turmstraße");
        germanNames.add("Einkommensteuer");
        germanNames.add("Südbahnhof");
        germanNames.add("Chauseestraße");
        germanNames.add("Ereignisfeld");
        germanNames.add("Elisenstraße");
        germanNames.add("Poststraße");
        germanNames.add("Gefängnis");
        germanNames.add("Seestraße");
        germanNames.add("Elektrizitätswerk");
        germanNames.add("Hafenstraße");
        germanNames.add("Neue Straße");
        germanNames.add("Westbahnhof");
        germanNames.add("Münchener Straße");
        germanNames.add("Gemeinschaftsfeld");
        germanNames.add("Wiener Straße");
        germanNames.add("Berliner Straße");
        germanNames.add("Frei Parken");
        germanNames.add("Theaterstraße");
        germanNames.add("Ereignisfeld");
        germanNames.add("Museumsstraße");
        germanNames.add("Opernplatz");
        germanNames.add("Nordbahnhof");
        germanNames.add("Lessingstraße");
        germanNames.add("Schillerstraße");
        germanNames.add("Wasserwerk");
        germanNames.add("Goethestraße");
        germanNames.add("Gehe ins Gefängnis");
        germanNames.add("Rathausplatz");
        germanNames.add("Hauptstraße");
        germanNames.add("Gemeinschaftsfeld");
        germanNames.add("Bahnhofstraße");
        germanNames.add("Hauptbahnhof");
        germanNames.add("Ereignisfeld");
        germanNames.add("Parkstraße");
        germanNames.add("Zustatzsteuer");
        germanNames.add("Schlossallee");
    }

    public List<String> getGermanNames() {
        return germanNames;
    }
}
