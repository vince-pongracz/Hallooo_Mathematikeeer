package org.asteroidapp.CONTROLLER;

import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.MODELL.entities.AIRobot;
import org.asteroidapp.MODELL.entities.Ufo;
import org.asteroidapp.MODELL.entities.Settler;
import org.asteroidapp.MODELL.EventType;
import org.asteroidapp.MODELL.resources.*;
import org.asteroidapp.MODELL.spaceobjects.Sun;
import org.asteroidapp.util.ActionResponse;
import org.asteroidapp.util.CallStackViewer;
import org.asteroidapp.util.InitMessage;

import java.util.*;

import static org.asteroidapp.MODELL.EventType.DELETE;
import static org.asteroidapp.MODELL.EventType.REFRESH;

/**
 *
 */
public class GameController {

    /**
     * Logger for GameController
     */
    private static final Logger log = LogManager.getLogger(GameController.class.getSimpleName());

    public static ActionResponse response = null;

    /**
     * Default constructor
     */
    private GameController() {
        log.log(Level.INFO, "GameController constructor called");
        CallStackViewer.getInstance().methodStartsLogCall("GameController constructor called");

        //default config?
        //later set in setup
        currentRound = 1;
        playersNum = 0;
        settlerNum = 1;
        ufosNum = 1;

        GsonBuilder gsonBuilder = new GsonBuilder();
        log.log(Level.TRACE, "jsonBuilder created");
        log.log(Level.INFO, "\nDefault config set:\n{}", gsonBuilder.setPrettyPrinting().create().toJson(this));

        robots = new HashSet<>();
        players = new ArrayList<>();
        ufos = new HashSet<>();

        log.log(Level.TRACE, "robots has their collection");
        log.log(Level.TRACE, "players has their collection");
        log.log(Level.TRACE, "ufos has their collection");

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
     * roundcounter
     */
    private int currentRound;

    /**
     * Number of players in current game
     */
    private int playersNum = 1;

    /**
     * Initial number of settlers/player in current game
     */
    private int settlerNum = 1;


    /**
     * Number of players in current game
     */
    private int ufosNum = 1;

    /**
     * collection for robots
     */
    private transient Set<AIRobot> robots;

    /**
     * collection for players
     */
    private transient List<Player> players;

    /**
     * collection for ufos
     */
    private transient Set<Ufo> ufos;

    private void createUfos() {
        for (int i = 0; i < ufosNum; i++) {
            var ufo = new Ufo("Ufo_" + i, AsteroidZone.getInstance().findHome());
            ufos.add(ufo);
            ufo.signalizeUpdate(EventType.REFRESH);
        }
    }

    /**
     * Create and place settlers to the HomeAsteroid
     * this method creates just settlers
     */
    private void dropSettlers() {
        log.log(Level.INFO, "dropSettlers called");
        CallStackViewer.getInstance().methodStartsLogCall("dropSettlers() called");
        log.log(Level.TRACE, "iterate on player(s) to create their settler(s)");

        var startPlace = AsteroidZone.getInstance().findHome();
        //for every player
        for (var playerItem : players) {
            var playerName = playerItem.getName();

            log.log(Level.INFO, "create {}'s settlers", playerName);

            for (int i = 0; i < settlerNum; i++) {
                //settlerName to set
                String name = playerName + "'s settler_" + i;

                //create new Settler (set name, startPlace, and owner), and bind to his/her owner
                var newSettler = new Settler(name, startPlace, playerItem);

                //bind settler to his/her asteroid (to get the notifications from his/her SteppableSpaceObject)
                startPlace.checkIn(newSettler);

                //bind settler to the sun (to get the notifications from sun)
                //TODO ez nem csak akkor kellene, ha napközel van?
                AsteroidZone.getInstance().getSun().checkIn(newSettler);

                //bind player to settler
                playerItem.addSettler(newSettler);

                newSettler.signalizeUpdate(EventType.REFRESH);

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
    private void createAndNamePlayers(String[] names) {
        log.log(Level.INFO, "createAndNamePlayers called");
        CallStackViewer.getInstance().methodStartsLogCall("createAndNamePlayers() called");

        //create so many players, which was given in config
        for (int i = 0; i < playersNum; ) {
            String name = names[i];

            //check on input
            if (name != null && !name.equals("")) {
                //add player, if does not exist (collection is a set!)
                players.add(new Player(name));

                log.log(Level.INFO, "New player({}) added as: {}", i, name);
                i++;
            } else {
                log.log(Level.WARN, "Wrong name try: {}", name);
            }
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * sets up the current game
     */
    public void setupGame(InitMessage initConfig) {

        log.log(Level.INFO, "setupGame called");
        CallStackViewer.getInstance().methodStartsLogCall("setupGame() called");

        //get number and name of players
        log.log(Level.TRACE, "Setup...");
        playersNum = initConfig.getPlayerNum();
        this.createAndNamePlayers(initConfig.getNames());

        settlerNum = initConfig.getSettlerNum();
        ufosNum = initConfig.getUfoNum();
        log.log(Level.TRACE, "Initialize...");

        //creating zone
        AsteroidZone.numOfAsteroids = initConfig.getAsteroidNum();
        AsteroidZone.defOfCloseToSun = initConfig.getDefOfCloseToSun();
        AsteroidZone.getInstance().createZone();

        //create and place settlers and ufos on the Zone
        GameController.getInstance().dropSettlers();
        GameController.getInstance().createUfos();

        GsonBuilder gsonBuilder = new GsonBuilder();
        log.log(Level.INFO, "\nCurrent config:\n{}", gsonBuilder.setPrettyPrinting().create().toJson(this));

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
    private void removePlayer(String name) {
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

            removePlayer(playerLeaving.getName());
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
        return currentRound;
    }

    /**
     * Check win or lose conditions
     */
    public void evaluateRound() {
        log.log(Level.INFO, "evaluateRound called");
        CallStackViewer.getInstance().methodStartsLogCall("evaluateRound() called");

        //eval flair
        this.evaluateFlair();

        log.log(Level.INFO, "check win and lose conditions");

        //check is there any settler alive
        if (players == null || players.size() == 0) {
            log.log(Level.TRACE, "Game lost");
            //TODO response here
        }
        //check win conditions
        else {
            ResourceStorage aggregStorage = new ResourceStorage();
            aggregStorage.setAllCapacity(10000);

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
                                aggregStorage.pushResource(item);
                            }
                        }
                        //if no resource, send a log message... (not so important)
                        else {
                            log.log(Level.TRACE, "No resource at settler: {}", settler.getName());
                        }
                    }
                    //if settler isn't at HomeAsteroid, his/her resource doesn't matter in this check
                    //because he/she isn't at HomeAsteroid
                    else {
                        log.log(Level.TRACE, "Settler: {} isn't at HomeAsteroid", settler.getName());
                        //NOP
                    }
                }
            }
            //add resources to check from HomeAsteroid
            var resourcesInHome = AsteroidZone.getInstance().findHome().getResourcesWithoutTakeThem();
            for (var res:resourcesInHome) {
                aggregStorage.pushResource(res);
            }

            //check win:
            //if all resource in resources are more than 3 --> WIN
            //otherwise not
            boolean win = true;

            //It's not a map anymore therefore it doesn't know all the possible resources so
            //if it has zero resources it returns true or if it has just 3 irons it would also return true
            List<Resource> allResources = new ArrayList<>();
            allResources.add(new Coal());
            allResources.add(new Iron());
            allResources.add(new FrozenWater());
            allResources.add(new Uran());

            for (var item : allResources) {
                if (aggregStorage.countOf(item) < 3) {
                    win = false;
                    break;
                }
            }

            if (win) {
                log.log(Level.INFO, "Players win");
                //TODO send response;
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
        CallStackViewer.getInstance().methodStartsLogCall("evaluateFlair() called");

        if (getRound() % Sun.sunFlairInEveryXRound == 0) {
            log.log(Level.TRACE, "flair event will be launched");

            AsteroidZone.getInstance().getSun().signalizeUpdate(EventType.FLAIREVENT);
        } else if (getRound() % Sun.sunFlairInEveryXRound == (Sun.sunFlairInEveryXRound - 2)) {
            log.log(Level.TRACE, "flair is coming in the future!");

            AsteroidZone.getInstance().getSun().signalizeUpdate(EventType.FLAIRWARN);
        } else {
            log.log(Level.TRACE, "no flair event in this round");
            //NOP
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * @param newPlayer
     */
    //TODO is it really useful?
    //I don't think so
    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
    }

    public static int maxRound = 22;

    /**
     * Remove AIRobot from game
     *
     * @param bot bot to remove
     * @return boolean was the operation successful
     */
    public boolean removeAutoEntity(AIRobot bot) {
        log.log(Level.TRACE, "removeBot called");

        Boolean retValue = false;
        if (bot != null) {
            retValue = robots.remove(bot);
            bot.signalizeUpdate(DELETE);
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
            bot.signalizeUpdate(REFRESH);
            log.log(Level.TRACE, "bot added to the game");
        } else {
            log.log(Level.TRACE, "no bot added to the game");
        }
    }

    private void execAutoEntityActions() {
        log.log(Level.TRACE, "check on robots");
        if (robots != null && robots.size() != 0) {
            log.log(Level.INFO, "Iterate on robots, they do things:");

            for (var bot : robots) {
                bot.doAction();
            }
        }

        log.log(Level.TRACE, "check on ufos");
        if (ufos != null && ufos.size() != 0) {
            log.log(Level.INFO, "Iterate on ufos, they do things:");

            for (var ufo : ufos) {
                ufo.doAction();
            }
        }
    }

    private int actual = 0;

    public void nextPlayer() {
        actual++;
        if (actual == players.size()) {
            actual = 0;
            execAutoEntityActions();
            evaluateRound();
            currentRound++;

            if (getRound() > maxRound) {
                endGame();
            }
        }
    }

    public Player getActualPlayer() {
        return players.get(actual);
    }

    //TODO endgame, add message to response...
    private void endGame() {

    }
}