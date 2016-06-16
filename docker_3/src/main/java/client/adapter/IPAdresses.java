package client.adapter;

import client.util.yellowpages.YellowPagesService;

/**
 * Created by Jana Mareike on 24.05.2016.
 */
public class IPAdresses {

    private YellowPagesService yp;

    public IPAdresses(){
        yp = new YellowPagesService();
    }

    public String gamesIP() { return yp.findURIByServiceName("fancy_games");}

    public String diceIP() { return yp.findURIByServiceName("fancy_dice");}

    public String usersIP() { return yp.findURIByServiceName("fancy_users");}

    public String eventsIP() { return yp.findURIByServiceName("fancy_events");}

    public String banksIP() { return  yp.findURIByServiceName("fancy_banks");}

    public String boardsIP() { return yp.findURIByServiceName("fancy_boards");}

    public String clientIP() { return "192.168.255.38:4567"; }
}
