package org.asteroidapp;

import org.asteroidapp.resources.*;
import org.asteroidapp.spaceobjects.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.util.CallStackViewer;

import java.util.*;

/**
 * It stores the asteroids, the home asteroid and the sun
 * and it also can create the whole zone
 */
public class AsteroidZone {

    private final int asteroidSize = 80;

    //only one random per class should exist otherwise it would give the same result (Position, Resource) for every asteroid
    private final Random random = new Random(999); //seed: 999 is good for testing uran explosion


    /**
     * logger for AsteroidZone
     */
    private static final Logger log = LogManager.getLogger(AsteroidZone.class.getSimpleName());

    /**
     * Default constructor
     */
    private AsteroidZone() {
        spaceObjects = Collections.synchronizedSet(new HashSet<>());
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
    public static double defOfCloseToSun = 500;

    /**
     * It stores the objects e.g. asteroid, home asteroid, portals
     */
    private Set<SteppableSpaceObject> spaceObjects = null;

    /**
     * store the sun
     */
    private Sun sun = null;

    public Sun getSun() {
        return sun;
    }

    private SteppableSpaceObject homeAsteroid = null;

    public static int numOfAsteroids = 23;

    public static int range = 1000;
    /**
     * It creates the whole zone with a bunch of asteroids (amount can be set)
     * the home asteroid and the sun.
     */
    public void createZone() {
        CallStackViewer.getInstance().methodStartsLogCall("createZone() called");

        Resource resource = new Empty();
        int layer = 0;

        //For now the Sun is in the top-left Corner and has a size of 100 x 100
        sun = new Sun(new Position(150, 150, 50));
        log.log(Level.TRACE, "Sun has been created in the top-left corner");

        homeAsteroid = new HomeAsteroid(new Position(400, 500, 5), new Empty());
        spaceObjects.add(homeAsteroid);

        int numOfPlacedAsteroids = 0;
        while (numOfPlacedAsteroids < numOfAsteroids) {
            //TODO add minimal, maximal distance logic, namefaker for spacenames
            Position randomPosition = generateRandomPosition(range);

            if (checkDistanceAtCreate(randomPosition)) {
                randomPosition.setRadius(5);
                resource = generateRandomResource();

                //Layer is between 3 and 6
                layer = random.nextInt(3) + 3;

                spaceObjects.add(new Asteroid("temp" + numOfPlacedAsteroids, randomPosition, resource, layer));
                log.log(Level.TRACE, "Asteroid created at x={} y={} with a core of {} with layer={}",
                        (int) randomPosition.getX(), (int) randomPosition.getY(), resource.getName(), layer);
                numOfPlacedAsteroids++;
            }
        }

        //TODO Slipping is not working
/*
        if(numOfAsteroids > 20){
            Position slippedPosition = null;

            while(numOfPlacedAsteroids < numOfAsteroids){
                for(SteppableSpaceObject elem: spaceObjects){
                    slippedPosition = generateSlippedPosition(elem.getPosition(), range);

                    if(slippedPosition != null){
                        resource = generateRandomResource();
                        layer = random.nextInt(3) + 3;
                        slippedPosition.setRadius(5);
                        spaceObjects.add(new Asteroid("temp" + numOfPlacedAsteroids, slippedPosition, resource, layer));
                        log.log(Level.TRACE, "Asteroid created at x={} y={} with a core of {} with layer={}", (int) slippedPosition.getX(), (int) slippedPosition.getY(), resource.getName(), layer);
                        numOfPlacedAsteroids++;
                    }

                    if(numOfPlacedAsteroids == numOfAsteroids)
                        break;
                }
                break;
            }
        }*/

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * It adds an Object to the zone
     *
     * @param spaceObj The object that is going to added to the list of spaceObjects
     */
    public void addSpaceObject(SteppableSpaceObject spaceObj) {
        log.log(Level.INFO, "addSpaceObject called");
        CallStackViewer.getInstance().methodStartsLogCall("addSpaceObject() called");

        if (spaceObj != null) {
            spaceObjects.add(spaceObj);
        } else {
            log.log(Level.WARN, "spaceObj is null");
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * It removes an object from the zone
     *
     * @param removedSpaceObject The object that is going to removed to the list of spaceObjects
     */
    public void removeSpaceObject(SteppableSpaceObject removedSpaceObject) {
        log.log(Level.INFO, "removeSpaceObject called");
        CallStackViewer.getInstance().methodStartsLogCall("removeSpaceObject() called");

        if (removedSpaceObject != null) {
            spaceObjects.remove(removedSpaceObject);
        } else {
            log.log(Level.WARN, "removedSpaceObject is null");
        }

        CallStackViewer.getInstance().methodReturns();
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
        //Round to the nearest number that can be divided by 5 for less possible Positions
        int x = random.nextInt(range - asteroidSize);
        int y = random.nextInt(range - asteroidSize) + asteroidSize;
        return new Position(x, y);
    }

    //TODO Slipping the asteroids is not working :(
/*
    public Position generateSlippedPosition(Position currentPos, int range){
        Position slipped = null;
        boolean isNotCorrect = true;
        int x = 0;

        while(x < range - asteroidSize && isNotCorrect){
            Position temp = new Position(x, currentPos.getY());
            if(checkDistanceAtCreate(temp)) {
                slipped = temp;
                isNotCorrect = true;
            }
            x += 1;
        }

        int y = asteroidSize;
        if(slipped == null){
            while(y < range && isNotCorrect){
                Position temp = new Position(currentPos.getX(), y);
                if(checkDistanceAtCreate(temp)) {
                    slipped = temp;
                    isNotCorrect = true;
                }
                y += 1;
            }
        }
        return slipped;
    }
*/
    /**
     * It generates a randomly selected resource
     *
     * @return A randomly selected resource
     */
    public Resource generateRandomResource() {
        //TODO solve: 5 of each Resources should not be near to the sun
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