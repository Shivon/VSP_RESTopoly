package client.adapter;

/**
 * Created by Jana Mareike on 24.05.2016.
 */
public class IPAdresses {

    public IPAdresses(){}

    public String gamesIP(){ return "http://172.18.0.26:4567"; }

    public String diceIP(){
        return "http://172.18.0.35:4567";
    }

    public String usersIP(){
        return "172.18.0.25:4567";
    }

    public String eventsIP(){
        return null;
    }

    public String banksIP(){
        return "172.18.0.25:4567";
    }

    public String boardsIP(){
        return "172.18.0.38:4567";
    }

    public String clientIP() { return "192.168.255.38:4567"; }
}
