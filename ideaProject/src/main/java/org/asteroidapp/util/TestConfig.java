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
import org.asteroidapp.spaceobjects.Sun;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.asteroidapp.entities.Settler.*;

//TODO logs everywhere

public class TestConfig {

    private static final Logger log = LogManager.getLogger(TestConfig.class.getSimpleName());

    private String testname = "defaultTestName";
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
    private transient boolean enableCompare = true;
    private String[] expectedOut = {};

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
        Sun.sunFlairInEveryXRound = Math.abs(sunFlairInEveryXRound);
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

    public void eval() {
        ConsoleUI.getInstance().sendMessageToConsole("");
        ConsoleUI.getInstance().sendMessageToConsole("EVAL TEST : " + testname);
        ConsoleUI.getInstance().sendMessageToConsole("---------------------------------------");

        enableCompare = (expectedOut.length > 0) ? true : false;
        boolean checkExpectedV = checkExpected();

        if (enableCompare && checkExpectedV) {
            ConsoleUI.getInstance().sendMessageToConsole("---------------------------------------");
            ConsoleUI.getInstance().sendMessageToConsole(testname + " : PASSED");
            ConsoleUI.getInstance().sendMessageToConsole("---------------------------------------");
        } else if (enableCompare) {
            ConsoleUI.getInstance().sendMessageToConsole("---------------------------------------");
            ConsoleUI.getInstance().sendMessageToConsole(testname + " FAILED");
            ConsoleUI.getInstance().sendMessageToConsole("---------------------------------------");
        } else {
            ConsoleUI.getInstance().sendMessageToConsole("---------------------------------------");
            ConsoleUI.getInstance().sendMessageToConsole(testname + " : no eval for this test");
            ConsoleUI.getInstance().sendMessageToConsole("---------------------------------------");
        }
    }

    private boolean checkExpected() {
        try {
            String dirName = "logs" + File.separator;
            File directory = new File(dirName);
            File[] files = directory.listFiles(File::isFile);
            long lastModifiedTime = Long.MIN_VALUE;
            File latestLog = null;

            if (files != null) {
                for (var file : files) {
                    if (file.lastModified() > lastModifiedTime) {
                        latestLog = file;
                        lastModifiedTime = file.lastModified();
                    }
                }
            }

            List<String> lines = Files.readAllLines(Paths.get(dirName + latestLog.getName()), StandardCharsets.UTF_8);

            int howManyFound = 0;
            for (var line : lines) {
                for (int i = howManyFound; i < expectedOut.length; i++) {
                    Pattern pattern = Pattern.compile(expectedOut[i], Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        ConsoleUI.getInstance().sendMessageToConsole(expectedOut[i] + " : pattern found");
                        howManyFound++;
                    } else {
                        //NOP
                    }
                }
            }

            return (howManyFound == expectedOut.length);

        } catch (IOException e) {
            System.err.println("File error");
        }
        return false;
    }
}
