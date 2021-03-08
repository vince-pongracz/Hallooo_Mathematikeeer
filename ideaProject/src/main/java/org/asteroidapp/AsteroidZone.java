package org.asteroidapp;

import org.asteroidapp.entities.AIRobot;
import org.asteroidapp.resources.*;
import org.asteroidapp.spaceobjects.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * It stores the asteroids, the home asteroid and the sun
 * and it also can create the whole zone
 */
public class AsteroidZone {

    private int asteroidSize = 80;

    /**
     * logger for AsteroidZone
     */
    private static final Logger log = LogManager.getLogger(AsteroidZone.class.getSimpleName());

    /**
     * Default constructor
     */
    private AsteroidZone() {
        spaceObjects = new HashSet<>();
    }

    private static AsteroidZone instance = null;

    public static AsteroidZone getInstance() {
        if (instance == null) {
            instance = new AsteroidZone();
        }
        return instance;
    }

    /**
     * The definition of what is too close to the sun (in pixels)
     */
    //TODO setter, or preset
    public static double defOfCloseToSun = 200;

    /**
     * It stores the objects e.g. asteroid, home asteroid, portals
     */
    private Set<SteppableSpaceObject> spaceObjects;

    /**
     * store the sun
     */
    private Sun sun = null;

    public Sun getSun() {
        return sun;
    }

    private SteppableSpaceObject homeAsteroid = null;

    /**
     * It creates the whole zone with a bunch of asteroids (amount can be set)
     * the home asteroid and the sun.
     */
    public void createZone() {
        //add home
        int range = 1000;
        int numOfAsteroids = 8;
        Random rand = new Random();
        Resource resource;
        int layer;

        homeAsteroid = new HomeAsteroid(new Position(0, 0, 10), new Empty());
        spaceObjects.add(homeAsteroid);

        //For now the Sun is in the top-left Corner and has a size of 100 x 100
        sun = new Sun(new Position(0, 100, 50));
        log.log(Level.TRACE, "Sun has been created in the top-left corner");

        int i = 0;
        while (i < numOfAsteroids) {
            //TODO add minimal, maximal distance logic, namefaker for spacenames
            Position randomPosition = generateRandomPosition(range);
            if (checkDistanceAtCreate(randomPosition)) {
                randomPosition.setRadius(30);
                resource = generateRandomResource();
                layer = rand.nextInt(5) + 3;
                //Layer is between 3 and 8
                spaceObjects.add(new Asteroid("temp" + i, randomPosition, resource, layer));
                log.log(Level.TRACE, "Asteroid created at x={} y={} with a core of {} with layer={}",
                        (int) randomPosition.getX(), (int) randomPosition.getY(), resource.getName(), layer);
                i++;
            }
        }

    }

    /**
     * It adds an Object to the zone
     *
     * @param spaceObj The object that is going to added to the list of spaceObjects
     */
    public void addSpaceObject(SteppableSpaceObject spaceObj) {
        log.log(Level.INFO, "addSpaceObject called");
        if (spaceObj != null) {
            spaceObjects.add(spaceObj);
        } else {
            log.log(Level.WARN, "spaceObj is null");
        }
    }

    /**
     * It removes an object from the zone
     *
     * @param removedSpaceObject The object that is going to removed to the list of spaceObjects
     */
    public void removeSpaceObject(SteppableSpaceObject removedSpaceObject) {
        log.log(Level.INFO, "removeSpaceObject called");
        if (removedSpaceObject != null) {
            spaceObjects.remove(removedSpaceObject);
        } else {
            log.log(Level.WARN, "removedSpaceObject is null");
        }
    }

    /**
     * It returns with an iterator on the steppableSpaceObject
     *
     * @return iterator on the steppableSpaceObject
     */
    public Iterator<SteppableSpaceObject> getIterOnSpaceObjects() {
        return spaceObjects.iterator();
    }

    /**
     * It generates a random Position
     *
     * @return A random position that an object owns on the screen
     */
    public Position generateRandomPosition(int range) {
        Random random = new Random();
        return new Position(random.nextInt(range - asteroidSize), random.nextInt(range - asteroidSize) + asteroidSize);
    }

    /**
     * It generates a randomly selected resource
     *
     * @return A randomly selected resource
     */
    public Resource generateRandomResource() {
        //TODO solve: 5 of each Resources should not be near to the sun
        Random random = new Random();
        int randNum = random.nextInt(5);
        Resource result;

        switch (randNum) {
            case (0):
                result = new Coal();
                break;
            case (1):
                result = new Uran();
                break;
            case (2):
                result = new FrozenWater();
                break;
            case (3):
                result = new Iron();
                break;
            default:
                result = new Empty();
        }
        return result;
    }

    /**
     * It decides whether on that position can be placed on Asteroid or not
     *
     * @param toCheckPosition
     * @return true if an Asteroid can be placed there otherwise false
     */
    private boolean checkDistanceAtCreate(Position toCheckPosition) {
        boolean canBePlaced = true;
        int x, y;
        for (SteppableSpaceObject elem : spaceObjects) {
            x = (int) elem.getPosition().getX();
            y = (int) elem.getPosition().getY();
            int r = (int) elem.getPosition().getRadius();
            boolean xIsWrong = toCheckPosition.getX() > x - r && toCheckPosition.getX() < x + asteroidSize + r;
            boolean yIsWrong = toCheckPosition.getY() > y - r && toCheckPosition.getY() < y + asteroidSize + r;

            if (xIsWrong || yIsWrong) {
                canBePlaced = false;
            }
        }
        return canBePlaced;
    }

    /**
     * It returns with the home asteroid
     *
     * @return the home asteroid
     */
    public SteppableSpaceObject findHome() {
        return homeAsteroid;
    }
}