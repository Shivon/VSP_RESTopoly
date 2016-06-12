package client.adapter;

/**
 * Created by Jana Mareike on 24.05.2016.
 */
public class IPAdresses {

    public IPAdresses(){}

    public String gamesIP(){ return "http://172.18.0.26:4567"; }
//     public String gamesIP() { String gamesUri = yp.findURIByServiceName("fancy_games");}

    public String diceIP(){
        return "http://172.18.0.35:4567";
    }
    //     public String diceIP() { String diceUri = yp.findURIByServiceName("fancy_dice");}

    public String usersIP(){
        return "172.18.0.25:4567";
    }
    //     public String usersIP() { String usersUri = yp.findURIByServiceName("fancy_users");}

    public String eventsIP(){
        return null;
    }
    //     public String eventsIP() { String eventsUri = yp.findURIByServiceName("fancy_events");}

    public String banksIP(){return "172.18.0.25:4567";}
    //     public String banksIP() { String banksUri = yp.findURIByServiceName("fancy_banks");}

    public String boardsIP(){
        return "172.18.0.38:4567";
    }
    //     public String boardsIP() { String boardsUri = yp.findURIByServiceName("fancy_boards");}

    public String clientIP() { return "192.168.255.38:4567"; }
}
