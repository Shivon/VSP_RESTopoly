package de.haw.vs.escr.dice;

import de.haw.vs.escr.dice.service.DiceService;
import de.haw.vs.escr.dice.util.yellowpages.IYellowPages;
import de.haw.vs.escr.dice.util.yellowpages.YellowPagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by Christian on 04.04.2016.
 */
public class MyMainClass {
    static final Logger log = LoggerFactory.getLogger(MyMainClass.class);

    public static void main(String[] args) {
        new DiceService();
        IYellowPages yp = new YellowPagesService();
        yp.registrateService("/dice", "fancy_dice", "fancy_dice", "A Fancy Dice Service");
        //String eventsUri = yp.findURIByServiceName("fancy_events");
        //MyMainClass.log.info(eventsUri);
    }
}
