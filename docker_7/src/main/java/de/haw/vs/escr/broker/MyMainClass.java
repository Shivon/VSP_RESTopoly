package de.haw.vs.escr.broker;

import de.haw.vs.escr.broker.service.BrokerService;
import de.haw.vs.escr.broker.util.yellowpages.IYellowPages;
import de.haw.vs.escr.broker.util.yellowpages.YellowPagesService;
import org.codehaus.groovy.runtime.powerassert.SourceText;

/**
 * Created by Christian on 13.06.2016.
 */
public class MyMainClass {
    public static void main(String[] args) {
        IYellowPages yp = new YellowPagesService();
        yp.registrateService("/broker", "fancy_brokers", "fancy_brokers", "A fancy broker service");
        new BrokerService();
    }
}
