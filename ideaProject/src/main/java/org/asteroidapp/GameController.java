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
        log.log(Level.INFO, "GameController constructor called");

        //default config?
        //later set in setup
        gameIsRunning = false;
        currentRound = 1;
        playersNum = 1;

        settlerNum = 1;

        //hopefully this works as expected
        //TODO form it normally
        GsonBuilder gsonBuilder = new GsonBuilder();
        log.log(Level.INFO, "\nDefault config set:\n{}", gsonBuilder.setPrettyPrinting().create().toJson(this));

        robots = new HashSet<>();
        players = new HashSet<>();

        log.log(Level.TRACE, "robots has their collection");
        log.log(Level.TRACE, "players has their collection");

    }

    private static GameController instance = null;

    public static GameController getInstance() {
        log.log(Level.INFO, "getInstance called");

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
        log.log(Level.INFO, "dropSettlers called");
        log.log(Level.TRACE, "iterate on player(s) to create their settler(s)");

        for (var playerItem : players) {
            String playerName = playerItem.getName();

            log.log(Level.INFO, "create {}'s settlers", playerName);

            for (int i = 0; i < settlerNum; i++) {
                String name = playerName + "'s settler" + i;
                var newSettler = new Settler(name, AsteroidZone.getInstance().findHome());
                playerItem.addSettler(newSettler);

                log.log(Level.TRACE, "{} created for player: {}", newSettler.getName(), playerName);
            }

            log.log(Level.INFO, "Player {}'s settler(s): done", playerName);
        }
    }

    /**
     * initialize players
     */
    private void createAndNamePlayers() {
        log.log(Level.INFO, "createAndNamePlayers called");

        for (int i = 0; i < playersNum; ) {
            ConsoleUI.getInstance().sendMessageToConsole("Name of player " + (i + 1));
            String name = ConsoleUI.getInstance().readLineFromConsole();

            //refactor!!!
            if (name != null && !name.equals("")) {
                players.add(new Player(name));

                log.log(Level.INFO, "New player({}) added as: {}", i, name);

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

        log.log(Level.INFO, "setupGame called");

        ConsoleUI.getInstance().sendMessageToConsole("Setup...");
        ConsoleUI.getInstance().sendMessageToConsole("#players");

        playersNum = ConsoleUI.getInstance().readIntFromConsole();
        createAndNamePlayers();

        ConsoleUI.getInstance().sendMessageToConsole("#settlers/player");
        settlerNum = ConsoleUI.getInstance().readIntFromConsole();

        ConsoleUI.getInstance().sendMessageToConsole("Initialize...");

        AsteroidZone.getInstance().createZone();
        GameController.getInstance().dropSettlers();

        GsonBuilder gsonBuilder = new GsonBuilder();
        log.log(Level.INFO, "\nCurrent config:\n{}", gsonBuilder.setPrettyPrinting().create().toJson(this));

        ConsoleUI.getInstance().sendMessageToConsole("");
        ConsoleUI.getInstance().sendMessageToConsole("Setup ended");
        log.log(Level.INFO, "setup ended");
    }

    /**
     * Get iterator on players
     *
     * @return an iterator to the players
     */
    public Iterator<Player> getIterOnPlayers() {
        log.log(Level.INFO, "getIterOnPlayers called");
        log.log(Level.TRACE, "return iterator on players collection");

        return players.iterator();
    }

    /**
     * Remove a player from the game.
     *
     * @param name player's ame, which will be removed, if it's find in players collection
     */
    //TODO refactor (what's the goal of this method..?)
    public void removePlayer(String name) {
        log.log(Level.INFO, "removePlayer called");

        log.log(Level.TRACE, "check param");

        if (name != null && !name.equals("")) {
            log.log(Level.INFO, "Search player with name: {}", name);

            for (var player : players) {
                if (player.getName().equals(name)) {

                    log.log(Level.TRACE, "Player found");
                    log.log(Level.INFO, "Kill player...");
                    player.killPlayer();

                    log.log(Level.INFO, "Remove player from players collection");
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
        log.log(Level.INFO, "leaveGame called");

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

        //eval flair
        evaluateFlair();

        log.log(Level.TRACE, "check win and lose conditions");

        //check is there any settler alive
        if (players == null || players.size() == 0) {
            log.log(Level.TRACE, "Game lost");
            ConsoleUI.getInstance().sendMessageToConsole("Game lost");
            gameIsRunning = false;
        }
        //check win conditions
        else {
            //TODO implement correctly... it's not easy to find out the way..

            //collect the collected resources in a map
            //String - name of the resource
            //Integer - piece of the resource
            Map<String, Integer> resources = new HashMap<>();

            //init map
            //TODO refactor: add a private collection to this class, and if a reource created,
            //add this resource to this collection --> easier to extend
            resources.put("Coal", 0);
            resources.put("FrozenWater", 0);
            resources.put("Iron", 0);
            resources.put("Uran", 0);

            //iterate on players for their resources
            for (var player : players) {
                var iter = player.getIterOnMySettlers();

                //iterate on settlers for their resources
                while (iter.hasNext()) {
                    var settler = iter.next();

                    //if settler is at Home, his/her resource counts, otherwise not
                    //(because resources should be placed at one asteroid)
                    if (settler.getMySpaceObject() == AsteroidZone.getInstance().findHome()) {
                        var tempList = settler.listResources();
                        //is there any collected resource?
                        if (tempList != null) {
                            //if yes, add them to the resources collection, what will be checked
                            for (var item : tempList) {
                                if (resources.containsKey(item.getName())) {
                                    resources.put(item.getName(), resources.get(item.getName()) + 1);
                                } else {
                                    resources.put(item.getName(), 1);
                                }
                            }
                        }
                        //if no resource, send a log message... (not so important)
                        else {
                            log.log(Level.TRACE, "No resource at settler: {}", settler.getName());
                            //NOP
                        }
                    }
                    //if settler isn't at HomeAsteroid, his/her resources don't matter in this check
                    //because he/she isn't at HomeAsteroid
                    else {
                        log.log(Level.TRACE, "Settler: {} isn't at HomeAsteroid", settler.getName());
                        //NOP
                    }
                }
            }
            //TODO add resources from HomeAsteroid

            //check win:
            //if all resource in resources are more than 3 --> WIN
            //otherwise not
            boolean win = true;
            for (var item : resources.values()) {
                if (item.intValue() < 3) {
                    win = false;
                }
            }

            if (win) {
                log.log(Level.INFO, "Players win");
                gameIsRunning = false;
            }
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

        while (gameIsRunning && getRound() < 12) {
            log.log(Level.TRACE, "new round started: {}", getRound());
            round();
            evaluateRound();
        }
    }

    /**
     * Implements a round in the game
     */
    private void round() {
        log.log(Level.TRACE, "round called: {} th round of the game", currentRound);
        currentRound++;

        //TODO implement
        log.log(Level.TRACE, "Iterate on players");
        for (var player : players) {
            log.log(Level.TRACE, "player: _{}_ is on move", player.getName());
            var settlerIter = player.getIterOnMySettlers();
            //choose specified settler (with mouse or at the console with numbers), or iterate on the settlers random?

        }

        if (robots.size() != 0) {
            log.log(Level.TRACE, "Iterate on robots, they do things:");

            for (var bot : robots) {
                //TODO what should do a bot in a round?
            }
        }
    }

    /**
     * Remove AIRobot from game
     *
     * @param bot bot to remove
     * @return boolean was the operation successful
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
     * Add an AIRobot to the game
     *
     * @param bot robot to add
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