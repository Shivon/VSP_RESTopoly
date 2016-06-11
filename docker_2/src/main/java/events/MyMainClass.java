package events;

import events.service.EventService;
import events.util.yellowpages.IYellowPages;
import events.util.yellowpages.YellowPagesService;


public class MyMainClass {
    public static void main(String[] args) {
        new EventService();
        IYellowPages yp = new YellowPagesService();
        yp.registrateService("/events", "event", "event", "A Fancy Event Service");
    }
}