package org.asteroidapp;

import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.entities.AIRobot;
import org.asteroidapp.entities.Settler;
import org.asteroidapp.resources.*;
import org.asteroidapp.util.CallStackViewer;
import org.asteroidapp.util.ConsoleUI;

import java.util.*;

/**
 *
 */
public class GameController {

    /**
     * Logger for GameController
     */
    private static final Logger log = LogManager.getLogger(GameController.class.getSimpleName());
    private static Logger callStack = LogManager.getLogger("callStack");

    /**
     * Default constructor
     */
    private GameController() {
        log.log(Level.INFO, "GameController constructor called");
        callStack.log(Level.TRACE, "{}GameController constructor called", CallStackViewer.getInstance().printIntend());

        //default config?
        //later set in setup
        gameIsRunning = false;
        currentRound = 1;
        playersNum = 0;
        settlerNum = 1;


        GsonBuilder gsonBuilder = new GsonBuilder();
        log.log(Level.TRACE, "jsonBuilder created");
        log.log(Level.INFO, "\nDefault config set:\n{}", gsonBuilder.setPrettyPrinting().create().toJson(this));

        robots = new HashSet<>();
        players = new HashSet<>();

        log.log(Level.TRACE, "robots has their collection");
        log.log(Level.TRACE, "players has their collection");

        CallStackViewer.getInstance().methodReturns();
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
     * roundcounter
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
     * this method creates just settlers
     */
    private void dropSettlers() {
        log.log(Level.INFO, "dropSettlers called");
        callStack.log(Level.TRACE, "{}dropSettlers called", CallStackViewer.getInstance().printIntend());
        log.log(Level.TRACE, "iterate on player(s) to create their settler(s)");

        //for every player
        for (var playerItem : players) {
            String playerName = playerItem.getName();

            log.log(Level.INFO, "create {}'s settlers", playerName);

            for (int i = 0; i < settlerNum; i++) {
                //settlerName to set
                String name = playerName + "'s settler" + i;

                //create new Settler (set name, startPlace, and owner), and bind to his/her owner
                var startPlace = AsteroidZone.getInstance().findHome();
                var newSettler = new Settler(name, startPlace, playerItem);

                //bind settler to his/her asteroid (to get the notifications from his/her SteppableSpaceObject)
                startPlace.checkIn(newSettler);
                //bind settler to the sun (to get the notifications from sun)
                AsteroidZone.getInstance().getSun().checkIn(newSettler);

                //bind player to settler
                playerItem.addSettler(newSettler);

                log.log(Level.TRACE, "{} created for player: {}", newSettler.getName(), playerName);
            }

            log.log(Level.INFO, "Player {}'s settler(s): done", playerName);
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * initialize players
     * create players (but not settlers!)
     */
    private void createAndNamePlayers() {
        log.log(Level.INFO, "createAndNamePlayers called");
        callStack.log(Level.TRACE, "{}createAndNamePlayers called", CallStackViewer.getInstance().printIntend());

        //create so many players, which was given in config
        for (int i = 0; i < playersNum; ) {
            //Interaction with user(s)
            ConsoleUI.getInstance().sendMessageToConsole("Name of player " + (i + 1));
            //read the given playerName
            String name = ConsoleUI.getInstance().readLineFromConsole();

            //check on input
            if (name != null && !name.equals("")) {
                //add player, if does not exist (collection is a set!)
                players.add(new Player(name));

                log.log(Level.INFO, "New player({}) added as: {}", i, name);
                i++;

            } else {
                log.log(Level.WARN, "Wrong name try: {}", name);
                ConsoleUI.getInstance().sendMessageToConsole("Wrong name");
            }
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * sets up the current game
     */
    public void setupGame() {

        log.log(Level.INFO, "setupGame called");
        callStack.log(Level.TRACE, "{}setupGame called", CallStackViewer.getInstance().printIntend());

        //get number and name of players
        log.log(Level.TRACE,"Setup...");
        ConsoleUI.getInstance().sendMessageToConsole("Type the number of desired players");

        playersNum = ConsoleUI.getInstance().readIntFromConsole();
        createAndNamePlayers();

        ConsoleUI.getInstance().sendMessageToConsole("Type the number of desired settlers for each player");
        settlerNum = ConsoleUI.getInstance().readIntFromConsole();

        log.log(Level.TRACE,"Initialize...");

        //creating zone
        AsteroidZone.getInstance().createZone();
        //create and place settlers on the Zone
        GameController.getInstance().dropSettlers();

        log.log(Level.TRACE, "jsonBuilder created");
        GsonBuilder gsonBuilder = new GsonBuilder();
        log.log(Level.INFO, "\nCurrent config:\n{}", gsonBuilder.setPrettyPrinting().create().toJson(this));

        ConsoleUI.getInstance().sendMessageToConsole("");
        ConsoleUI.getInstance().sendMessageToConsole("Setup ended");
        log.log(Level.INFO, "setup ended");

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Get iterator on players
     *
     * @return an iterator to the players
     */
    public Iterator<Player> getIterOnPlayers() {
        log.log(Level.INFO, "getIterOnPlayers called");
        log.log(Level.TRACE, "return iterator on players collection");

        if (players == null || players.size() == 0) {
            return Collections.emptyIterator();
        } else {
            return players.iterator();
        }
    }

    /**
     * Remove a player from the game.
     *
     * @param name player's ame, which will be removed, if it's find in players collection
     */
    //TODO refactor (what's the goal of this method..?)
    public void removePlayer(String name) {
        log.log(Level.INFO, "removePlayer called");

        if (name != null && !name.equals("")) {
            log.log(Level.INFO, "Search player with name: {}", name);

            for (var player : players) {
                if (player.getName().equals(name)) {
                    log.log(Level.INFO, "Remove player from players collection");
                    players.remove(player);
                    return;
                }
            }
        } else {
            log.log(Level.INFO, "No player found with name: {}", name);
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
        log.log(Level.TRACE, "getRound called - current round: {}", currentRound);
        return currentRound;
    }

    /**
     * Check win or lose conditions
     */
    public void evaluateRound() {
        log.log(Level.INFO, "evaluateRound called");
        callStack.log(Level.TRACE, "{}evaluateRound called", CallStackViewer.getInstance().printIntend());

        //eval flair
        evaluateFlair();

        log.log(Level.INFO, "check win and lose conditions");

        //check is there any settler alive
        if (players == null || players.size() == 0) {
            log.log(Level.TRACE, "Game lost");
            ConsoleUI.getInstance().sendMessageToConsole("Game lost");
            gameIsRunning = false;
        }
        //check win conditions
        else {
            //TODO implement correctly... it's not easy to find out the way..

            ResourceStorage resources = new ResourceStorage();

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
                            for (Resource item : tempList) {
                                resources.pushResource(item);
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
            //TODO ha csak pl szen és uranbol van 3-3 akkor is valszeg igazzal ter vissza mivel nem Map
            boolean win = true;

            //It's not a map anymore therefore it doesn't know all the possible resources so
            //if it has zero resources it returns true or if it has just 3 irons it would also return true
            List<Resource> allResources = new ArrayList<>();
            allResources.add(new Coal());
            allResources.add(new Iron());
            allResources.add(new FrozenWater());
            allResources.add(new Uran());

            for (var item : allResources) {
                if (resources.countOf(item) < 3) {
                    win = false;
                }
            }

            if (win) {
                log.log(Level.INFO, "Players win");
                gameIsRunning = false;
            }
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Call and schedule flair events
     */
    private void evaluateFlair() {
        //TODO logic to flair scheduling - long task
        log.log(Level.INFO, "evaluateFlair called");

        if (getRound() % 10 == 0) {
            log.log(Level.TRACE, "flair event will be launched");

            AsteroidZone.getInstance().getSun().notifyAboutDieEvent();
        } else if (getRound() % 10 == 7) {
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
        log.log(Level.INFO, "inGame called - this is the game loop");
        gameIsRunning = true;
        log.log(Level.TRACE, "game is running: {}", ((Boolean) gameIsRunning).toString());

        while (gameIsRunning && getRound() < 22) {
            log.log(Level.TRACE, "new round started: {}", getRound());
            round();
            evaluateRound();
        }
    }

    /**
     * Implements a round in the game
     */
    private void round() {
        log.log(Level.INFO, "round called: {} th round of the game", currentRound);
        currentRound++;

        log.log(Level.TRACE, "check on players");
        if (players != null && players.size() != 0) {
            log.log(Level.INFO, "Iterate on players");
            for (var player : players) {

                var tempName = player.getName();
                log.log(Level.INFO, "player: _{}_ is on move", tempName);
                log.log(Level.TRACE, "iterate on {}'s settlers", tempName);

                var settlerIter = player.getIterOnMySettlers();
                while (settlerIter.hasNext()) {
                    settlerIter.next().doAction();
                }
            }
        }

        log.log(Level.TRACE, "check on robots");
        if (robots != null && robots.size() != 0) {
            log.log(Level.INFO, "Iterate on robots, they do things:");

            for (var bot : robots) {
                bot.doAction();
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