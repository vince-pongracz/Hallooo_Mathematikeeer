package org.asteroidapp;

import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.entities.AIRobot;
import org.asteroidapp.entities.Settler;
import org.asteroidapp.util.ConsoleUI;

import java.util.*;

/**
 *
 */
public class GameController {

    private static final Logger log = LogManager.getLogger(GameController.class.toString());

    /**
     * Default constructor
     */
    private GameController() {
        log.log(Level.TRACE, "GameController constructor called");

        //default config?
        //later set in setup
        gameIsRunning = false;
        currentRound = 1;
        playersNum = 1;

        settlerNum = 1;

        //hopefully this works as expected
        //TODO form it normally
        GsonBuilder gsonBuilder = new GsonBuilder();
        log.log(Level.TRACE, "\nDefault config set:\n{}", gsonBuilder.setPrettyPrinting().create().toJson(this));

        robots = new HashSet<>();
        players = new HashSet<>();

        log.log(Level.TRACE, "robots has their collection");
        log.log(Level.TRACE, "players has their collection");

    }

    private static GameController instance = null;

    public static GameController getInstance() {
        log.log(Level.TRACE, "getInstance called");

        if (instance == null) {
            log.log(Level.TRACE, "Create GameController instance");

            instance = new GameController();
        }

        log.log(Level.TRACE, "return GameController instance");

        return instance;
    }

    /**
     * State of the game
     */
    private boolean gameIsRunning;

    /**
     * count rounds
     */
    private int currentRound;

    /**
     * Number of players in current game
     */
    private int playersNum;

    /**
     * Initial number of settlers/player in current game
     */
    private int settlerNum;

    /**
     * collection for robots
     */
    private transient Set<AIRobot> robots;

    /**
     * collection for players
     */
    private transient Set<Player> players;

    /**
     * Create and place settlers to the HomeAsteroid
     */
    private void dropSettlers() {
        log.log(Level.TRACE, "dropSettlers called");
        log.log(Level.TRACE, "iterate on player(s) to create their settler(s)");

        for (var playerItem : players) {
            String playerName = playerItem.getName();

            log.log(Level.TRACE, "create {}'s settlers", playerName);

            for (int i = 0; i < settlerNum; i++) {
                String name = playerName + "'s settler" + i;
                var newSettler = new Settler(name, AsteroidZone.getInstance().findHome());
                playerItem.addSettler(newSettler);

                log.log(Level.TRACE, "{} created for player: {}", newSettler.getName(), playerName);
            }

            log.log(Level.TRACE, "Player {}'s settler(s): done", playerName);
        }
    }

    /**
     * initialize players
     */
    private void createAndNamePlayers() {
        log.log(Level.TRACE, "createAndNamePlayers called");

        for (int i = 0; i < playersNum; ) {
            ConsoleUI.getInstance().sendMessageToConsole("Name of player " + (i + 1));
            String name = ConsoleUI.getInstance().readLineFromConsole();

            //refactor!!!
            if (name != null && !name.equals("")) {
                players.add(new Player(name));

                log.log(Level.TRACE, "New player({}) added as: {}", i, name);

                i++;
            } else {
                log.log(Level.WARN, "Wrong name try: {}", name);

                ConsoleUI.getInstance().sendMessageToConsole("Wrong name");
            }
        }
    }

    /**
     * sets up the current game
     */
    public void setupGame() {

        ConsoleUI.getInstance().sendMessageToConsole("Setup...");
        ConsoleUI.getInstance().sendMessageToConsole("#players");

        playersNum = ConsoleUI.getInstance().readIntFromConsole();
        createAndNamePlayers();

        ConsoleUI.getInstance().sendMessageToConsole("#settlers/player");
        settlerNum = ConsoleUI.getInstance().readIntFromConsole();

        ConsoleUI.getInstance().sendMessageToConsole("Initialize...");

        AsteroidZone.getInstance().createZone();
        GameController.getInstance().dropSettlers();

        ConsoleUI.getInstance().sendMessageToConsole("");
        ConsoleUI.getInstance().sendMessageToConsole("Setup ends");
    }

    /**
     * Get iterator on players
     *
     * @return an iterator to the players
     */
    public Iterator<Player> getIterOnPlayers() {
        log.log(Level.TRACE, "getIterOnPlayers called - return iterator on players collection");

        return players.iterator();
    }

    /**
     * Remove a player from the game.
     *
     * @param name player's ame, which will be removed, if it's find in players collection
     */
    //TODO refactor (what's the goal of this method..?)
    public void removePlayer(String name) {
        log.log(Level.TRACE, "removePlayer called");
        log.log(Level.TRACE, "check param");

        if (name != null && !name.equals("")) {
            log.log(Level.TRACE, "Search player with name: {}", name);

            for (var player : players) {
                if (player.getName().equals(name)) {

                    log.log(Level.TRACE, "Player found");
                    log.log(Level.TRACE, "Kill player...");
                    player.killPlayer();

                    log.log(Level.TRACE, "Remove player from players collection");
                    players.remove(player);
                    return;
                }
            }
        }
    }

    /**
     * "Kick" a player from game
     *
     * @param playerLeaving player, which will be kicked
     */
    //TODO refactor (what's the goal of this method..?)
    public void leaveGame(Player playerLeaving) {
        log.log(Level.TRACE, "leaveGame called");

        if (playerLeaving != null) {
            log.log(Level.TRACE, "kill and remove player: {}", playerLeaving.getName());

            playerLeaving.killPlayer();
            players.remove(playerLeaving);

        } else {
            //NOP
        }
    }

    /**
     * Getter on round
     *
     * @return
     */
    public int getRound() {
        log.log(Level.TRACE, "getRound called");
        log.log(Level.INFO, "current round: {}", currentRound);
        return currentRound;
    }

    /**
     * Check win or lose conditions
     */
    public void evaluateRound() {
        log.log(Level.TRACE, "evaluateRound called");

        evaluateFlair();

        log.log(Level.TRACE, "check win and lose conditions");
        if (players == null || players.size() == 0) {
            log.log(Level.TRACE, "Game lost");
            ConsoleUI.getInstance().sendMessageToConsole("Game lost");
            gameIsRunning = false;
        } else {
            //TODO implement correctly... it's not easy to find out the way..
            Map<String, Integer> resources = new HashMap<>();
            for (var player : players) {
                var iter = player.getIterOnMySettlers();
                while (iter.hasNext()) {
                    var settler = iter.next();
                    var tempList = settler.listResources();
                    for (var item : tempList) {
                        Integer count = resources.get(item.getName());
                        if (count != null) {
                            ++count;
                            resources.put(item.getName(), count);
                        } else {
                            resources.put(item.getName(), 0);
                        }

                    }
                }
            }

            //not always!
            //TODO
            gameIsRunning = false;

        }
    }

    /**
     * Call and schedule flair events
     */
    private void evaluateFlair() {
        //TODO logic to flair scheduling
        log.log(Level.TRACE, "evaluateFlair called");

        if (getRound() % 5 == 0) {
            log.log(Level.TRACE, "flair event will be launched");

            AsteroidZone.getInstance().getSun().doSunFlair();
        } else if (getRound() % 5 == 2) {
            log.log(Level.TRACE, "flair is coming in the future!");

            AsteroidZone.getInstance().getSun().notifyAboutDanger();
        } else {
            log.log(Level.TRACE, "no flair event in this round");
            //NOP
        }
    }

    /**
     * @param newPlayer
     */
    //TODO is it really useful?
    //I don't think so
    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
    }

    /**
     * Game loop
     */
    public void inGame() {
        log.log(Level.TRACE, "inGame called - this is the game loop");
        gameIsRunning = true;
        log.log(Level.TRACE, "game is running: {}", ((Boolean) gameIsRunning).toString());

        while (gameIsRunning || getRound() > 50) {
            log.log(Level.TRACE, "new round started: {}", getRound());
            round();
            evaluateRound();
        }
    }

    /**
     * Implements a round in the game
     */
    private void round() {
        currentRound++;
        //TODO implement
    }

    /**
     * @param bot
     * @return
     */
    public boolean removeBot(AIRobot bot) {
        log.log(Level.TRACE, "removeBot called");

        Boolean retValue = false;
        if (bot != null) {
            retValue = robots.remove(bot);
        } else {
            retValue = false;
        }

        log.log(Level.TRACE, "bot removed: {}", retValue.toString());

        return retValue;
    }

    /**
     * @param bot
     */
    public void addBot(AIRobot bot) {

        log.log(Level.TRACE, "addBot called");

        if (bot != null) {
            robots.add(bot);
            log.log(Level.TRACE, "bot added to the game");
        } else {
            log.log(Level.TRACE, "no bot added to the game");
        }
    }
}