package events.repo;

import events.model.Event;

/**
 * Created by Jana Mareike on 11.04.2016.
 */
public class EventMatcher {

    public static boolean matchesType(Event event, String regex) {

        return event.getType().matches(regex) || regex == null;
    }

    public static boolean matchesGame(Event event, String regex) {

        return event.getGame().toString().matches(regex) || regex == null;
    }

    public static boolean matchesName(Event event, String regex) {

        return event.getName().toString().matches(regex) || regex == null;
    }

    public static boolean matchesReason(Event event, String regex) {
        return event.getReason().toString().matches(regex) || regex == null;
    }

    public static boolean matchesPlayerOrIsNull(Event event, String regex) {
        return event.getPlayer().toString().matches(regex) || regex == null;
    }

    public static boolean matchesResourceOrIsNull(Event event, String regex) {
        return event.getResource().toString().matches(regex) || regex == null;
    }
}
