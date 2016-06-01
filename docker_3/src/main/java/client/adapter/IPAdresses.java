package client.adapter;

/**
 * Created by Jana Mareike on 24.05.2016.
 */
public class IPAdresses {

    public IPAdresses(){}

    public String gamesIP(){ return "http://172.18.0.77:4567"; }

    public String diceIP(){
        return "http://172.18.0.92:4567/dice";
    }

    public String usersIP(){
        return "172.18.0.29:4567";
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

    public String clientIP() { return null; }
}
