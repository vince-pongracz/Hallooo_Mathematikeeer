package org.asteroidapp;

import org.asteroidapp.resources.Coal;
import org.asteroidapp.resources.Empty;
import org.asteroidapp.spaceobjects.*;

import java.util.*;

/**
 *
 */
public class AsteroidZone {

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
     *
     */
    //TODO setter, or preset
    public static double defOfCloseToSun = 30;

    /**
     *
     */
    private Set<SteppableSpaceObject> spaceObjects;

    /**
     *
     */
    private Sun sun;

    public Sun getSun() {
        return sun;
    }

    /**
     *
     */
    public void createZone() {
        //add home
        int range = 800;
        int numOfAsteroids = 45;

        HomeAsteroid asteroid = new HomeAsteroid(new Position(0, 0, 10), new Empty());
        spaceObjects.add(asteroid);


        //TODO solve this in non-infinite time
        boolean sunPositionIsOK = false;
        while (!sunPositionIsOK) {
            Position temp = generateRandomPosition(range);
            if (temp.distanceFrom(asteroid.getPosition()) >= defOfCloseToSun) {
                sunPositionIsOK = true;
                sun = new Sun(temp);
            }
        }


        int i = 0;
        while (i < numOfAsteroids) {
            //TODO layer and resource distribution
            //TODO add minimal, maximal distance logic
            Position randomPosition = generateRandomPosition(range);
            if (checkDistanceAtCreate(randomPosition)) {
                //TODO to radius is it good to have a setter?
                randomPosition.setRadius(10);
                spaceObjects.add(new Asteroid("temp" + i, randomPosition, new Coal(), 12));
                i++;
            }
        }

    }

    /**
     * @param spaceObj
     */
    public void addSpaceObject(SteppableSpaceObject spaceObj) {
        if (spaceObj != null) {
            spaceObjects.add(spaceObj);
        } else {
            //NOP
        }
    }

    /**
     * @param removedSpaceObject
     */
    public void removeSpaceObject(SteppableSpaceObject removedSpaceObject) {
        //TODO nullcheck
        spaceObjects.remove(removedSpaceObject);
    }

    /**
     * @return
     */
    public Iterator<SteppableSpaceObject> getIterOnSpaceObjects() {
        return spaceObjects.iterator();
    }

    /**
     * @return
     */
    public Position generateRandomPosition(int range) {
        // TODO implement, and range of generation?
        Random random = new Random(range);
        double substituate = range / 2;
        return new Position(random.nextDouble() - substituate, random.nextDouble() - substituate);
    }

    /**
     * @param toCheckPosition
     * @return
     */
    private boolean checkDistanceAtCreate(Position toCheckPosition) {
        // TODO implement here
        return false;
    }

}