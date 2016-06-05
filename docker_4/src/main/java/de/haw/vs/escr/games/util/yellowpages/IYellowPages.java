package de.haw.vs.escr.games.util.yellowpages;

import de.haw.vs.escr.games.util.yellowpages.model.Service;

/**
 * Created by Christian on 26.05.2016.
 */
public interface IYellowPages {
    String getUri();
    String getURL();
    Service registrateService(String uri, String name, String service, String description);
    Service findServiceByName(String name);
    String findURIByServiceName(String name);
}
