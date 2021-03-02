package org.asteroidapp;

import org.asteroidapp.entities.AIRobot;
import org.asteroidapp.util.ConsoleUI;

import java.util.*;

/**
 *
 */
public class GameController {

    /**
     * Default constructor
     */
    private GameController() {

        //default config?
        //later set in setup
        gameIsRunning = false;
        currentRound = 1;
        playersNum = 1;
        settlerNum = 1;

        robots = new HashSet<>();
        players = new HashSet<>();
    }

    private static GameController instance = null;

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    /**
     *
     */
    private boolean gameIsRunning;

    /**
     *
     */
    private int currentRound;

    /**
     *
     */
    private int playersNum;

    /**
     *
     */
    private int settlerNum;

    /**
     *
     */
    private Set<AIRobot> robots;

    /**
     *
     */
    private Set<Player> players;

    /**
     *
     */
    private void dropSettlers() {
        // TODO implement here
    }

    /**
     *
     */
    public void setupGame() {

        ConsoleUI.getInstance().sendMessageToConsole("Setup:");
        ConsoleUI.getInstance().sendMessageToConsole("#players");
        playersNum = ConsoleUI.getInstance().readIntFromConsole();
        ConsoleUI.getInstance().sendMessageToConsole("#settlers/player");
        settlerNum = ConsoleUI.getInstance().readIntFromConsole();

        ConsoleUI.getInstance().sendMessageToConsole("Setup end");

        ConsoleUI.getInstance().sendMessageToConsole("Initialize...");

        AsteroidZone.getInstance().createZone();
        GameController.getInstance().dropSettlers();

        ConsoleUI.getInstance().sendMessageToConsole("END init phase");

        ConsoleUI.getInstance().sendMessageToConsole("");
    }

    /**
     * @return
     */
    public Iterator<Player> getIterOnPlayers() {
        return players.iterator();
    }

    /**
     * @param name
     */
    //TODO implement
    public void removePlayer(String name) {
    }

    /**
     * @param palyerLeaving
     */
    public void leaveGame(Player palyerLeaving) {
        // TODO implement here
    }

    /**
     * @return
     */
    public int getRound() {
        return currentRound;
    }

    /**
     *
     */
    public void evaluateRound() {
        // TODO implement here
    }

    /**
     *
     */
    private void evaluateFlair() {
        //TODO logic to flair scheduling
        if (getRound() % 5 == 0) {
            AsteroidZone.getInstance().getSun().doSunFlair();
        } else {
            //NOP
        }
    }

    /**
     * @param newPlayer
     */
    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
    }

    /**
     *
     */
    public void inGame() {
        boolean quitCondition = false;
        while (!quitCondition) {
            round();
        }
    }

    /**
     *
     */
    private void round() {
        currentRound++;

    }

    /**
     * @param bot
     * @return
     */
    public boolean removeBot(AIRobot bot) {
        return robots.remove(bot);
    }

    /**
     * @param bot
     */
    public void addBot(AIRobot bot) {
        robots.add(bot);
    }

}