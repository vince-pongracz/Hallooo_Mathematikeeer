package org.asteroidapp.entities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AsteroidZone;
import org.asteroidapp.GameController;
import org.asteroidapp.resources.*;
import org.asteroidapp.spaceobjects.Gate;
import org.asteroidapp.Player;
import org.asteroidapp.spaceobjects.SteppableSpaceObject;
import org.asteroidapp.util.ConsoleUI;

import java.util.*;

/**
 *
 */
public class Settler extends Entity {

    private static final Logger log = LogManager.getLogger(Settler.class.getSimpleName());

    /**
     *
     */
    private Map<Resource, Integer> resources = new HashMap<>();

    /**
     * Default constructor
     */
    public Settler(String name, SteppableSpaceObject initPlace, Player owner) {
        super(name, initPlace);
        resources.put(new Coal(), 0);
        resources.put(new Empty(), 0);
        resources.put(new FrozenWater(), 0);
        resources.put(new Iron(), 0);
        resources.put(new Uran(), 0);

        if (owner != null) {
            this.owner = owner;
        } else {
            log.log(Level.FATAL, "owner is null!");
        }
        log.log(Level.TRACE, "Settler created with an empty resource list");
    }

    @Override
    public boolean drill() {
        log.log(Level.INFO, "Settler tried to drill an object");
        return onSpaceObject.drillLayer();
    }

    @Override
    protected void die() {
        log.log(Level.INFO, "Die method of player {}'s settler called", this.owner.getName());
        AsteroidZone.getInstance().getSun().checkOut(this);
        onSpaceObject.checkOut(this);
        onSpaceObject = null;
        owner.removeSettler(this);
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

    @Override
    public void doAction() {
        log.log(Level.INFO, "doAction called");
        //TODO decisionmaking, and communication with user

        List<String> options = new ArrayList<>();
        options.add("move");
        options.add("drill");
        options.add("mine");
        options.add("create gate");
        options.add("build gate");
        options.add("deploy resource");
        options.add("list neighbours");
        ConsoleUI.getInstance().sendOptionListToConsole(options);
        var answer = ConsoleUI.getInstance().readIntFromConsole();

        boolean actionOK = false;
        while (!actionOK) {
            switch (answer) {
                case 0:
                    move();
                    actionOK = true;
                    break;
                case 1:
                    actionOK = drill();
                    break;
                case 2:
                    //TODO refactor, return with boolean
                    mine();
                    break;
                case 3:
                    //TODO refactor, return with boolean
                    createGate();
                    break;
                case 4:
                    //TODO refactor, return with boolean
                    buildGate();
                    break;
                case 5:
                    //TODO refactor, return with boolean
                    deployResource();
                    break;
                case 6:
                    listMyNeighbours();
                    actionOK = true;
                    break;
                default:
                    break;
            }
        }
    }


    /**
     *
     */
    private List<Gate> createdGates = null;

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