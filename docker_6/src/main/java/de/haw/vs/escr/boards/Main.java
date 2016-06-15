package de.haw.vs.escr.boards;

import de.haw.vs.escr.boards.service.BoardService;
import de.haw.vs.escr.boards.util.yellowpages.IYellowPages;
import de.haw.vs.escr.boards.util.yellowpages.YellowPagesService;

/**
 * Created by Eric on 03.05.2016.
 */
public class Main {
    public static void main(String argv[]) {
        IYellowPages yp = new YellowPagesService();
        yp.registrateService("/boards", "fancy_boards", "fancy_boards", "A fancy boards service");
        new BoardService();
    }
}
