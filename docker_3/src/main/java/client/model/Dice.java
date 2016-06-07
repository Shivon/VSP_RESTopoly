package client.model;

//import de.haw.vs.escr.dice.util.Util;

/**
 * Created by Christian on 04.04.2016.
 */
public class Dice {
    private int number;

    public Dice() {
    }

    public int getNumber() {
        return number;
    }

    public void rollDice() {
        final int min = 1;
        final int max = 6;
//        this.number = Util.getRandomNumber(min, max);
    }
}
