package client.adapter;

/**
 * Created by Jana Mareike on 24.05.2016.
 */
public class IPAdresses {

    public IPAdresses(){}

    public String gamesIP(){
        return "http://172.18.0.41:4567/games";
    }

    public String diceIP(){
        return "http://172.18.0.83:4567/dice";
    }

    public String usersIP(){
        return "172.18.0.11:4567";
    }

    public String eventsIP(){
        return null;
    }

    public String banksIP(){
        return null;
    }

    public String boardsIP(){
        return null;
    }
}
