package org.asteroidapp.util;

import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AsteroidZone;
import org.asteroidapp.GameController;
import org.asteroidapp.entities.Settler;
import org.asteroidapp.spaceobjects.HomeAsteroid;
import org.asteroidapp.spaceobjects.Position;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class TestConfig {

    private static final Logger log = LogManager.getLogger(TestConfig.class.getSimpleName());

    private final String testname = "defaultTestName";
    private int playerNumber = 1;
    private String[] playerNames = {"testName"};
    private int settlerNumber = 1;
    private int asteroidNumber = 23;
    private int range = 1000;
    private int maxRound = 22;
    private int sunFlairInEveryXRound = 10;
    private int homeCapacity = 5;
    private int settlerCapacity = 10;
    private int defOfCloseToSun = 500;
    private int maxNeighbourDistance = 500;
    /**
     * commands after game init
     */
    private String[] realCommandQueue = {};

    public boolean checkConfig() {
        if (testname != null &&
                playerNumber >= 1 &&
                playerNames.length == playerNumber &&
                settlerNumber >= 1 &&
                asteroidNumber <= 23 &&
                asteroidNumber >= 1 &&
                range == 1000 &&
                maxRound >= 1 &&
                homeCapacity >= 1 &&
                settlerCapacity >= 1 &&
                defOfCloseToSun == 500 &&
                maxNeighbourDistance == 500 &&
                realCommandQueue != null
        ) {
            log.log(Level.INFO, "All params checked: OK");
            ConsoleUI.getInstance().sendMessageToConsole(new GsonBuilder().setPrettyPrinting().create().toJson(this));

            return true;
        } else {
            log.log(Level.INFO, "Wrong testconfig");
            ConsoleUI.getInstance().sendMessageToConsole(new GsonBuilder().setPrettyPrinting().create().toJson(this));
            return false;
        }
    }

    public Queue<String> setConfigIntoComponents() {
        log.log(Level.INFO, "{}", testname);
        Queue<String> commands = new ArrayDeque<>();

        AsteroidZone.numOfAsteroids = Math.abs(asteroidNumber);
        AsteroidZone.range = Math.abs(range);
        GameController.maxRound = Math.abs(maxRound);
        GameController.sunFlairInEveryXRound = Math.abs(sunFlairInEveryXRound);
        HomeAsteroid.homeCapacity = Math.abs(homeCapacity);
        Settler.settlerCapacity = Math.abs(settlerCapacity);
        AsteroidZone.defOfCloseToSun = Math.abs(defOfCloseToSun);
        Position.setMaximalNeighbourDistance(maxNeighbourDistance);

        commands.add(Integer.toString(playerNumber));
        commands.addAll(Arrays.asList(playerNames));
        commands.add(Integer.toString(settlerNumber));
        commands.addAll(Arrays.asList(realCommandQueue));

        return commands;
    }

}
