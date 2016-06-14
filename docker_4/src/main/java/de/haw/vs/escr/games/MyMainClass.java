package de.haw.vs.escr.games;

import de.haw.vs.escr.games.service.GameService;
import de.haw.vs.escr.games.util.URLBuilder.URLBuilder;
import de.haw.vs.escr.games.util.yellowpages.IYellowPages;
import de.haw.vs.escr.games.util.yellowpages.YellowPagesService;

/**
 * Created by Christian on 29.04.2016.
 */
public class MyMainClass {
    public static void main(String[] args) {
        IYellowPages yp = new YellowPagesService();
        yp.registrateService("/games", "fancy_games", "fancy_games", "A fancy games service");
        new GameService();
    }
}
