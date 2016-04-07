package de.haw.vs.escr.dice.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Christian on 04.04.2016.
 */
public class Util {
    public static int getRandomNumber(int min, int max) {
        int number = min + (int)(Math.random() * max);
        return number;
    }

    public static URI uriFromString(String uriString) throws URISyntaxException {
        return new URI(uriString);
    }

    public static boolean validateURI(String uri) {
        try {
            new URL(uri);
        } catch (Exception e1) {
            return false;
        }

        return true;
    }
}
