package org.asteroidapp.entities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.GameController;
import org.asteroidapp.resources.*;
import org.asteroidapp.spaceobjects.Gate;
import org.asteroidapp.Player;
import org.asteroidapp.spaceobjects.SteppableSpaceObject;

import java.time.format.ResolverStyle;
import java.util.*;

/**
 *
 */
public class Settler extends Entity {

    private static final Logger log = LogManager.getLogger(Settler.class.toString());

    /**
     *
     */

    private Map<Resource, Integer> resources = new HashMap<>();

    /**
     * Default constructor
     */
    public Settler(String name, SteppableSpaceObject initPlace) {
        super(name, initPlace);
        resources.put(new Coal(), 0);
        resources.put(new Empty(), 0);
        resources.put(new FrozenWater(), 0);
        resources.put(new Iron(), 0);
        resources.put(new Uran(), 0);
        log.log(Level.TRACE, "Settler created with an empty resource list");
    }

    @Override
    public void drill() {
        log.log(Level.INFO, "Settler tried to drill an object");
        onSpaceObject.drillLayer();
    }

    @Override
    public void die() {

    }

    @Override
    protected SteppableSpaceObject chooseNeighbour(Set<SteppableSpaceObject> neighbours) {
        return null;
    }

    @Override
    public void notifyFlairEvent() {

    }

    @Override
    public void notifyFlairDanger() {

    }

    @Override
    public void notifyAsteroidExplosion() {

    }


    /**
     *
     */
    private Gate createdGates = null;

    /**
     *
     */
    private Player owner;

    /**
     *
     */
    public void createBot() {

        if (resources.get(new Coal()) >= 1 && resources.get(new Iron()) >= 1 && resources.get(new Uran()) >= 1) {

            int numOfResource = resources.get(new Coal());
            resources.put(new Coal(), numOfResource - 1);
            numOfResource = resources.get(new Iron());
            resources.put(new Iron(), numOfResource - 1);
            numOfResource = resources.get(new Uran());
            resources.put(new Uran(), numOfResource - 1);

            AIRobot bot = new AIRobot("Robot", onSpaceObject);
            GameController.getInstance().addBot(bot);
            log.log(Level.INFO, "Bot created at {} asteroid", onSpaceObject.getName());
        } else {
            log.log(Level.INFO, "Robot can not be created, not enough resources");
        }
    }

    /**
     *
     */
    public void mine() {
        // TODO implement here
    }

    /**
     *
     */
    public void createGate() {
        // TODO implement here
    }

    /**
     *
     */
    public void deployResource() {
        // TODO implement here
    }

    /**
     *
     */
    public void buildGate() {
        // TODO implement here
    }

    /**
     *
     */
    public List<Resource> listResources() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Resource chooseResource() {
        // TODO implement here
        return null;
    }

    /**
     * @param resource
     */
    private void addResource(Resource resource) {
        // TODO implement here
    }

}