package de.haw.vs.escr.games.businesslogic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.response.Response;
import de.haw.vs.escr.games.adapter.events.dto.EventDTO;
import de.haw.vs.escr.games.adapter.users.dto.UserDTO;
import de.haw.vs.escr.games.dtos.*;
import de.haw.vs.escr.games.models.*;
import de.haw.vs.escr.games.repos.GameRepo;
import de.haw.vs.escr.games.repos.PlayerRepo;
import de.haw.vs.escr.games.restmodel.BankRestModel;
import de.haw.vs.escr.games.restmodel.BoardRESTModel;
import de.haw.vs.escr.games.util.URLBuilder.URLBuilder;
import de.haw.vs.escr.games.util.yellowpages.IYellowPages;
import de.haw.vs.escr.games.util.yellowpages.YellowPagesService;
import de.haw.vs.escr.games.util.yellowpages.model.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by Christian on 30.04.2016.
 */
public class GameBusinessLogic {
    private static final Logger logger = LoggerFactory.getLogger(GameBusinessLogic.class);
    private GameRepo gameRepo = null;
    private PlayerRepo playerRepo = null;
    private Gson gson;

    public GameBusinessLogic(GameRepo gameRepo, PlayerRepo playerRepo) {
        this.gameRepo = gameRepo;
        this.playerRepo = playerRepo;
        GsonBuilder gb = new GsonBuilder();
        gb.excludeFieldsWithoutExposeAnnotation();
        this.gson = gb.create();
    }

    public Game createGame(Game game) {
        Game g1 = this.gameRepo.saveGame(game);
        g1.setUri("/games/" + g1.getGameId());
        Game g2 = this.gameRepo.saveGame(g1);
        return g2;
    }

    public Game findGame(int gameId) {
        Game g = this.gameRepo.findGame(gameId);
        return g;
    }

    public Game updateGame(Game game) {
        if (game.getGameId() < 0) return null;
        Game g = this.gameRepo.saveGame(game);
        return g;
    }

    public List<Game> findAllGames() {
        List<Game> games = this.gameRepo.findAllGames();
        return games;
    }

    public Paths updateServices(int gameId, Paths services) {
        Game g = this.gameRepo.findGame(gameId);
        g.setServices(services);
        Game g2 = this.gameRepo.saveGame(g);
        return g2.getServices();
    }

    public Paths updateComponents(int gameId, Paths components) {
        Game g = this.gameRepo.findGame(gameId);
        g.setComponents(components);
        Game g2 = this.gameRepo.saveGame(g);
        return g2.getComponents();
    }

    public StatusDTO findStatusForGameId(int gameId) {
        Game g = this.findGame(gameId);
        return g.toStatus();
    }

    public StatusDTO updateStatusForGameId(int gameId, StatusDTO statusDTO) {
        Game g = this.findGame(gameId);
        g.setStatus(statusDTO.getStatus());
        Game updatedGame = this.updateGame(g);
        return updatedGame.toStatus();
    }

    public Player createPlayer(int gameId, Player player) {
        Player savedPlayer = this.playerRepo.savePlayer(player);
        savedPlayer.setUri("/games/" + gameId + "/players/" + savedPlayer.getPlayerId());
        Player updatedPlayer = this.playerRepo.savePlayer(savedPlayer);
        return updatedPlayer;
    }

    public Player createPlayerAndAddToGame(int gameId, Player player) {
        Game g = this.findGame(gameId);
        Player savedPlayer = this.createPlayer(gameId, player);

        // Set pawn
        if (g.getComponents() != null) {
            if (g.getComponents().getBoard() != null) {
                BoardRESTModel brm = new BoardRESTModel(g.getComponents().getBoard(), g.getServices().getBoard());
                BoardPawnDTO bPawn = this.postPawnToBoardService(brm, savedPlayer.getUri());
                savedPlayer.setPawn(bPawn.getId());
            }

            if (g.getComponents().getBank() != null) {
                // Set bank account
                BankRestModel bankRest = new BankRestModel(g.getComponents().getBank(), g.getServices().getBank());
                BankAccountIdDTO bad = this.postAccountToBankService(bankRest, savedPlayer.getUri());
                if (bad != null) savedPlayer.setAccount(bad.getId());
            }
        }

        g.addPlayer(savedPlayer);
        this.updateGame(g);
        return savedPlayer;
    }

    private BankAccountIdDTO postAccountToBankService(BankRestModel bankRest, String playerURI) {
        BankAccountDTO bad = new BankAccountDTO();
        bad.setPlayer(playerURI);
        bad.setSaldo(4000);
        logger.info("POST to Board Service with Route '" + bankRest.getAccountComponentRoute() + "': " + this.gson.toJson(bad).toString());
        Response res = given().queryParam("player", bad.getPlayer()).queryParam("saldo", bad.getSaldo()).post(bankRest.getAccountComponentRoute());
        logger.info("Response was: "+ res.body().asString());
        logger.info("Status was: " + res.statusCode());
        try {

            BankAccountIdDTO baid = this.gson.fromJson(res.body().asString(), BankAccountIdDTO.class);
            return baid;
        }
        catch (Exception e) {
            logger.info("Something went wrong with bank account");
        }
        return null;
    }

    private BoardPawnDTO postPawnToBoardService(BoardRESTModel brm, String playerURI) {
        BoardPlayerDTO bpd = new BoardPlayerDTO(playerURI, brm.getInitialPlace(), brm.getInitialPosition());
        logger.info("POST to Board Service with Route '" + brm.getPawnsRoute() + "': " + this.gson.toJson(bpd).toString());
        Response res = given().body(bpd).post(brm.getPawnsRoute());
        logger.info("Response was: "+ res.body().asString());
        BoardPawnDTO bPawn = this.gson.fromJson(res.body().asString(), BoardPawnDTO.class);
        return bPawn;
    }

    public Player updatePlayer(Player p) {
        return this.playerRepo.savePlayer(p);
    }

    public Player compareAndUpdatePlayer(Player old, Player newP) {
        if (newP.getUri() != null) old.setUri(newP.getUri());
        if (newP.getUser() != null) old.setUser(newP.getUser());
        if (newP.getPawn() != null) old.setPawn(newP.getPawn());
        if (newP.getAccount() != null) old.setAccount(newP.getAccount());
        if (newP.getReady() != null) old.setReady(newP.getReady());
        return this.updatePlayer(old);
    }

    public void deletePlayer(int playerId) {
        Player p = this.findPlayer(playerId);
        this.deletePlayer(p);
    }

    public void deletePlayer(Player p) {
        this.playerRepo.deletePlayer(p);
    }

    private Player findPlayer(int playerId) {
        return this.playerRepo.findPlayer(playerId);
    }

    public Ready updateReady(Player p, Ready r) {
        if (r == null) return p.getReady();
        p.setReady(r);
        Player updatedPlayer = this.updatePlayer(p);
        return updatedPlayer.getReady();
    }

    public Player findPlayerHoldingTurn(List<Player> players) {
        for (Player p : players) {
            if (p.isTurn()) return p;
        }
        return null;
    }

    public PlayerDetailDTO tryAchieveTurn(List<Player> players, String uri) {
        Player holding = this.findPlayerHoldingTurn(players);
        if (holding != null && holding.getUri() == uri) {
            for (Player p : players) {
                if (p.getUri() == uri) {
                    PlayerDetailDTO pd = new PlayerDetailDTO(p, true, true);
                    pd.getPlayer().setTurn(true);
                    pd.getPlayer().setReady(new Ready(false));
                    pd.setPlayer(this.updatePlayer(pd.getPlayer()));
                    return pd;
                }
            }
        }
        if (holding == null) {
            for (Player p : players) {
                if (p.getUri() == uri) {
                    PlayerDetailDTO pd = new PlayerDetailDTO(p, false, true);
                    pd.getPlayer().setTurn(true);
                    pd.getPlayer().setReady(new Ready(false));
                    pd.setPlayer(this.updatePlayer(pd.getPlayer()));
                    return pd;
                }
            }
        }
        for (Player p : players) {
            if (p.getUri() == uri) {
                PlayerDetailDTO pd = new PlayerDetailDTO(p, false, false);
                pd.getPlayer().setTurn(false);
                pd.setPlayer(this.updatePlayer(pd.getPlayer()));
                return pd;
            }
        }
        return null;
    }

    public void releaseTurn(Game g) {
        for (Player p : g.getPlayers()) {
            p.setTurn(false);
            this.updatePlayer(p);
        }
    }

    public Game initializeAndCreateGame(Game game) {
        Game unroutedGame = this.createGame(game);
        Game routedGame = this.setGameRoutes(unroutedGame);
        return this.updateGame(routedGame);
    }

    private Game setGameRoutes(Game game) {
        Paths services = game.getServices();
        Paths components = new Paths();
        if (services.getDice() != null) components.setDice(services.getDice());
        if (services.getGame() != null) components.setGame(services.getGame() + "/" + game.getGameId());
        if (services.getBank() != null) components.setBank(this.initializeBankService(services.getBank(), game));
        if (services.getBroker() != null) components.setBroker(this.initializeBrokerService(services.getBroker(), game));
        if (services.getDecks() != null) components.setBroker(this.initializeDecksService(services.getDecks(), game));
        if (services.getEvents() != null) components.setEvents(services.getEvents());
        if (services.getBoard() != null) components.setBoard(this.initializeBoardService(services.getBoard(), game));
        game.setComponents(components);
        return game;
    }

    private String initializeBoardService(String board, Game game) {
        GameUriDTO gud = new GameUriDTO(game.getUri());

        //POST Service
        logger.info("POST to '" + board + "': " + this.gson.toJson(gud).toString());
        Response res = given().body(this.gson.toJson(gud)).post(board);
        logger.info("Response was: " + res.body().asString());

        BoardDTO bdto = this.gson.fromJson(res.body().asString(), BoardDTO.class);

        URLBuilder ub = new URLBuilder(board);

        return ub.getProtocolAndAuthority() + bdto.getId();
    }

    private String initializeDecksService(String decks, Game game) {
        GameUriDTO gud = new GameUriDTO(game.getUri());

        //POST Service
        logger.info("POST to '" + decks + "': " + this.gson.toJson(gud).toString());
        Response res = given().body(this.gson.toJson(gud)).post(decks);
        logger.info("Response was: " + res.body().asString());

        DeckDTO ddto = this.gson.fromJson(res.body().asString(), DeckDTO.class);

        URLBuilder ub = new URLBuilder(decks);

        return ub.getProtocolAndAuthority() + ddto.getId();
    }

    private String initializeBrokerService(String broker, Game game) {
        GameUriDTO gud = new GameUriDTO(game.getUri());

        //POST Service
        logger.info("POST to '" + broker + "': " + this.gson.toJson(gud).toString());
        Response res = given().body(this.gson.toJson(gud)).post(broker);
        logger.info("Response was: " + res.body().asString());

        BrokerDTO bdto = this.gson.fromJson(res.body().asString(), BrokerDTO.class);

        URLBuilder ub = new URLBuilder(broker);

        return ub.getProtocolAndAuthority() + bdto.getId();
    }

    private String initializeBankService(String bank, Game game) {
        GameUriDTO gud = new GameUriDTO(game.getUri());

        //POST Service
        logger.info("POST to '" + bank + "': " + this.gson.toJson(gud).toString());
        Response res = given().body(this.gson.toJson(gud)).post(bank);
        logger.info("Response was: " + res.body().asString());

        BankDTO bdto = this.gson.fromJson(res.body().asString(), BankDTO.class);

        URLBuilder ub = new URLBuilder(bank);

        return ub.getProtocolAndAuthority() + bdto.getId();
    }

    public void checkGameStatus(int gameId) {
        Game g = this.findGame(gameId);
        int maxPlayerCount = 2;
        if (g.getPlayers().size() == maxPlayerCount) {
            g.setStatus(GameStatus.running);
            for (Player p : g.getPlayers()) {
                p.setReady(new Ready(true));
                p.setTurn(false);
            }
            g.getPlayers().get(0).setTurn(true);
            g.getPlayers().get(0).setReady(new Ready(false));
            this.updateGame(g);

            //Send Event
            this.postStartEvent(g);

            //POST turn to client
            //Get user
            IYellowPages yp = new YellowPagesService();
            Service s = yp.findServiceByName("fancy_users");
            String uri = s.getUri();
            UserDTO uDto = this.findUser(g.getPlayers().get(0), uri);

            //POST Client
            this.postTurnToClient(uDto, g.getPlayers().get(0));
        }
    }

    private void postTurnToClient(UserDTO uDto, Player player) {
        String turn = "/turn";
        String uri = uDto.getUri().substring(0, uDto.getUri().lastIndexOf("/"));
        uri = uri + turn;
        logger.info("POST '" + uri + "': " + this.gson.toJson(player));
        Response res = given().body(player).post(uri);
        logger.info("Reponse was: " + res.body().asString());
    }

    private UserDTO findUser(Player p, String uri) {
        uri = uri + "/" + p.getUser();
        logger.info("GET " + uri);
        Response res = given().get(uri);
        logger.info("Response was: " + res.body().asString());
        try {
            return this.gson.fromJson(res.body().asString(), UserDTO.class);
        }
        catch (Exception e) {

        }
        return null;
    }

    private EventDTO postStartEvent(Game g) {
        EventDTO eDto = new EventDTO();
        eDto.setGame("/games/" + g.getGameId());
        eDto.setType("start");
        eDto.setName("Game started");
        eDto.setPlayer(g.getPlayers().get(0).getUri());
        eDto.setReason("Six Players added");
        eDto.setResource("/games/" + g.getGameId());
        //Make event
        EventDTO event = this.postEvent(eDto, g.getServices().getEvents());
        return event;
    }

    private EventDTO postEvent(EventDTO eDto, String eventPath) {
        logger.info("POST '" + eventPath + "': " + this.gson.toJson(eDto));
        Response res = given().body(this.gson.toJson(eDto)).post(eventPath);
        logger.info("Response was: " + res.body().asString());
        try {
            EventDTO e = this.gson.fromJson(res.body().asString(), EventDTO.class);
            return e;
        } catch (Exception e) {
            logger.info("Could not convert response to EventDTO");
        }
        return null;
    }

    public void setNextTurn(Game g) {
        Player player = null;
        boolean setted = false;
        for (Player p : g.getPlayers()) {
            boolean now = false;
            if (p.isTurn()) {
                now = true;
                setted = true;
                p.setTurn(false);
            }
            if (setted && !now) {
                p.setTurn(true);
                setted = false;
                player = p;
            }
        }
        if (setted) {
            g.getPlayers().get(0).setTurn(true);
            player = g.getPlayers().get(0);
        }

        //POST turn to client
        //Get user
        IYellowPages yp = new YellowPagesService();
        Service s = yp.findServiceByName("fancy_users");
        String uri = s.getUri();
        UserDTO uDto = this.findUser(player, uri);

        //POST Client
        this.postTurnToClient(uDto, player);
    }
}
