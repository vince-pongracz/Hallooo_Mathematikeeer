package org.asteroidapp.CONTROLLER;

import org.asteroidapp.MODELL.EventType;
import org.asteroidapp.MODELL.resources.*;
import org.asteroidapp.MODELL.spaceobjects.*;
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
    private Set<SteppableSpaceObject> spaceObjects = Collections.synchronizedSet(new HashSet<>());

    /**
     * store the sun
     */
    private Sun sun = null;

    public Sun getSun() {
        return sun;
    }

    private HomeAsteroid homeAsteroid = null;

    public static int numOfAsteroids = 23;

    public static int range = 1000;

    /**
     * It creates the whole zone with a bunch of asteroids (amount can be set)
     * the home asteroid and the sun.
     */
    public void createZone() {
        CallStackViewer.getInstance().methodStartsLogCall("createZone() called");

        int layer = 0;

        //TODO The sun and the homeasteroid can't have that kind of fix position because they are going to collide with other asteroids
        sun = new Sun(new Position(150, 150, 50));
        log.log(Level.TRACE, "Sun has been created in the top-left corner");

        homeAsteroid = new HomeAsteroid(new Position(400, 500, 5), new Empty());
        spaceObjects.add(homeAsteroid);

        //numOfTiles must be rows * columns and the numOfAsteroids must be lower than the numOfTiles
        int columns = 7;
        int rows = 6;
        int count = 0;

        while (count < numOfAsteroids) {
            int i = count % columns;
            int j = count / columns;

            int x = i * (1500 / columns) + random.nextInt(1500 / columns - 80);
            int y = j * (900 / rows) + random.nextInt(900 / rows - 80);
            Position position = new Position(x, y);
            
            position.setRadius(5);
            var resource = generateRandomResource();
            //Layer is between 3 and 6
            layer = random.nextInt(3) + 3;

            this.addSpaceObject(new Asteroid("temp" + count, position, resource, layer));
            log.log(Level.TRACE, "Asteroid created at x={} y={} with a core of {} with layer={}", (int) position.getX(), (int) position.getY(), resource.getName(), layer);
            count++;

        }

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
            spaceObj.signalizeUpdate(EventType.REFRESH);
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
            removedSpaceObject.signalizeUpdate(EventType.DELETE);
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
     * It generates a randomly selected resource
     *
     * @return A randomly selected resource
     */
    public Resource generateRandomResource() {
        //TODO solve: 5 of each Resources should not be near to the sun
        int randNum = random.nextInt(5);
        Resource result = switch (randNum) {
            case (0) -> new Coal();
            case (1) -> new Uran();
            case (2) -> new FrozenWater();
            case (3) -> new Iron();
            default -> new Empty();
        };

        return result;
    }

    /**
     * It returns with the home asteroid
     *
     * @return the home asteroid
     */
    public HomeAsteroid findHome() {
        return homeAsteroid;
    }

    public SteppableSpaceObject getObjectByName(String name) {
        for (SteppableSpaceObject suspect : spaceObjects) {
            if (suspect.getName().equals(name)) {
                return suspect;
            }
        }
        return null;
    }

    public SteppableSpaceObject getNearestObject(Position position) {
        double minDistance = Double.MAX_VALUE;
        SteppableSpaceObject returnedObject = null;
        for (var objectItem : spaceObjects) {
            var actualDistance = position.distanceFrom(objectItem.getPosition());
            if (actualDistance < minDistance) {
                minDistance = actualDistance;
                returnedObject = objectItem;
            }
        }
        return returnedObject;
    }
}