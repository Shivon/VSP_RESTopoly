package de.haw.vs.escr.games.businesslogic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.response.Response;
import de.haw.vs.escr.games.dtos.*;
import de.haw.vs.escr.games.models.*;
import de.haw.vs.escr.games.repos.GameRepo;
import de.haw.vs.escr.games.repos.PlayerRepo;
import de.haw.vs.escr.games.restmodel.BankRestModel;
import de.haw.vs.escr.games.restmodel.BoardRESTModel;
import de.haw.vs.escr.games.util.URLBuilder.URLBuilder;
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
        savedPlayer.setUri("/games/" + gameId + "/players/" + savedPlayer.getUser().toLowerCase());
        Player updatedPlayer = this.playerRepo.savePlayer(savedPlayer);
        return updatedPlayer;
    }

    public Player createPlayerAndAddToGame(int gameId, Player player) {
        Game g = this.findGame(gameId);
        Player savedPlayer = this.createPlayer(gameId, player);

        // Set pawn
        BoardRESTModel brm = new BoardRESTModel(g.getComponents().getBoard(), g.getServices().getBoard(), g.getComponents().getBoard());
        BoardPawnDTO bPawn = this.postPawnToBoardService(brm, savedPlayer.getUri());
        savedPlayer.setPawn(bPawn.getId());

        // Set bank account
        BankRestModel bankRest = new BankRestModel(g.getComponents().getBank(), g.getServices().getBank());
        BankAccountDTO bad = this.postAccountToBankService(bankRest, savedPlayer.getUri());

        g.addPlayer(savedPlayer);
        this.updateGame(g);
        return savedPlayer;
    }

    private BankAccountDTO postAccountToBankService(BankRestModel bankRest, String playerURI) {
        BankAccountDTO bad = new BankAccountDTO();
        bad.setPlayer(playerURI);
        bad.setSaldo(4000);
        logger.info("POST to Board Service with Route '" + bankRest.getAccountServiceRoute() + "': " + this.gson.toJson(bad).toString());
        Response res = given().body(this.gson.toJson(bad)).post(bankRest.getAccountServiceRoute());
        logger.info("Response was: "+ res.body().prettyPrint());
        bad = this.gson.fromJson(res.body().asString(), BankAccountDTO.class);
        return bad;
    }

    private BoardPawnDTO postPawnToBoardService(BoardRESTModel brm, String playerURI) {
        BoardPlayerDTO bpd = new BoardPlayerDTO(playerURI, brm.getInitialPlace(), brm.getInitialPosition());
        logger.info("POST to Board Service with Route '" + brm.getPawnsServiceRoute() + "': " + this.gson.toJson(bpd).toString());
        Response res = given().body(bpd).post(brm.getPawnsServiceRoute());
        logger.info("Response was: "+ res.body().prettyPrint());
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
        if (services.getBoard() != null) components.setBoard(this.initializeBoardService(services.getBoard(), game));
        if (services.getEvents() != null) components.setEvents(services.getEvents());
        game.setComponents(components);
        return game;
    }

    private String initializeBoardService(String board, Game game) {
        return this.initalizeService(board, game);
    }

    private String initializeDecksService(String decks, Game game) {
        return this.initalizeService(decks, game);
    }

    private String initializeBrokerService(String broker, Game game) {
        return this.initalizeService(broker, game);
    }

    private String initializeBankService(String bank, Game game) {
        return initalizeService(bank, game);
    }

    private String initalizeService(String uri, Game game) {
        GameUriDTO gud = new GameUriDTO(game.getUri());

        //POST Service
        logger.info("POST to '" + uri + "': " + this.gson.toJson(gud).toString());
        Response res = given().body(this.gson.toJson(gud)).post(uri);
        logger.info("Response was: " + res.body().prettyPrint());

        PathUriDTO pud = this.gson.fromJson(res.body().asString(), PathUriDTO.class);

        URLBuilder ub = new URLBuilder(uri);

        return ub.getProtocolAndAuthority() + pud.getId();
    }

    public void checkGameStatus(int gameId) {
        Game g = this.findGame(gameId);
        int maxPlayerCount = 6;
        if (g.getPlayers().size() == maxPlayerCount) {
            g.setStatus(GameStatus.running);
            for (Player p : g.getPlayers()) {
                p.setReady(new Ready(true));
            }
            this.updateGame(g);
        }
    }
}
