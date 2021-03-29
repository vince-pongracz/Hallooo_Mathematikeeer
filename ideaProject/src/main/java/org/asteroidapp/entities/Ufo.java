package org.asteroidapp.entities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AsteroidZone;
import org.asteroidapp.GameController;
import org.asteroidapp.resources.Empty;
import org.asteroidapp.resources.Resource;
import org.asteroidapp.resources.ResourceStorage;
import org.asteroidapp.spaceobjects.SteppableSpaceObject;
import org.asteroidapp.util.CallStackViewer;

import java.util.*;

/**
 *
 */
public class Ufo extends Entity {


    private ResourceStorage resources = null;
    public static int UfoCapacity = 30;

    /**
     * logger for Ufo
     */
    private static final Logger log = LogManager.getLogger(Ufo.class.getSimpleName());

    /**
     * Default constructor
     */
    public Ufo(String name, SteppableSpaceObject creationPlace) {
        super(name, creationPlace);

        log.log(Level.INFO, "Ufo constructor called");

        CallStackViewer.getInstance().methodStartsLogCall("AIRobot constructor called");

        if (creationPlace != null && name != null) {
            onSpaceObject = creationPlace;
        } else {
            log.log(Level.FATAL, "null parameters in constructor!");
        }

        CallStackViewer.getInstance().methodReturns();
    }


    @Override
    public boolean drill() {
        return false;
    }

    @Override
    protected void die() {

    }

    public boolean mine() {
        log.log(Level.INFO, "Mine called");
        CallStackViewer.getInstance().methodStartsLogCall("mine() called (Ufo)");

        Resource res = onSpaceObject.mineResource();
        boolean mineSuccess = false;

        //mining is successful
        if (res != null) {
            if (!res.equals(new Empty())) {
                addResource(res);
                log.log(Level.INFO, "Ufo mined a(n) {}", res.getName());
                mineSuccess = true;
            } else {
                //but can't mine "Empty", so it will be denied
                log.log(Level.INFO, "Ufo could not mine a resource because it is empty");
                mineSuccess = false;
            }
        } else {
            //unsuccessful mining
            log.log(Level.INFO, "Ufo could not mine a resource because the layer is not drilled trough");
            mineSuccess = false;
        }

        CallStackViewer.getInstance().methodReturns();
        return mineSuccess;
    }


    /**
     * It ads a resource to the players resources
     *
     * @param resource the resource that will be added
     */
    private void addResource(Resource resource) {
        log.log(Level.INFO, "addResource called");
        CallStackViewer.getInstance().methodStartsLogCall("addResource called");
        //TODO this can return null.. :/
        if (resource != null) {
            resources.pushResource(resource);
            log.log(Level.INFO, "{} added to Ufo successfully", resource.getName());
        } else {
            log.log(Level.INFO, "Nothing can be added");
        }
        CallStackViewer.getInstance().methodReturns();
    }

    @Override
    protected SteppableSpaceObject chooseNeighbour(Set<SteppableSpaceObject> neighbours) {
        //TODO (OPT): don't choose a spaceObject, which is empty
        //TODO (OPT): don't choose an asteroid, where robot came from

        log.log(Level.INFO, "chooseNeighbour called");
        CallStackViewer.getInstance().methodStartsLogCall("chooseNeighbour() called (AIRobot)");

        //nullcheck
        if (neighbours != null) {
            //convert set to a list to shuffle
            var neighbourListToShuffle = new ArrayList<SteppableSpaceObject>(neighbours);
            Collections.shuffle(neighbourListToShuffle);
            //generate random to decision
            var randomNumber = new Random().nextInt(neighbourListToShuffle.size());

            CallStackViewer.getInstance().methodReturns();

            return neighbourListToShuffle.get(randomNumber);
        } else {
            log.log(Level.FATAL, "Given collection in parameter is null!");
            CallStackViewer.getInstance().methodReturns();

            return null;
        }
    }

    @Override
    public void notifyFlairEvent() {
        log.log(Level.INFO, "notifyFlairEvent called");
        CallStackViewer.getInstance().methodStartsLogCall("notifyFlairEvent() called (AIRobot)");

        die();

        CallStackViewer.getInstance().methodReturns();
    }

    @Override
    public void notifyFlairDanger() {
        //TODO AI in Robots... (opt)
        log.log(Level.INFO, "notifyFlairDanger called");
        CallStackViewer.getInstance().methodStartsLogCall("notifyFlairDanger() called (AIRobot)");

        log.log(Level.INFO, "Hey bot, you should hide!");

        //NOP for Robots...
        //or some logic required to make a hole and hide
        CallStackViewer.getInstance().methodReturns();
    }

    @Override
    public void notifyAsteroidExplosion() {
        log.log(Level.INFO, "notifyAsteroidExplosion called");
        CallStackViewer.getInstance().methodStartsLogCall("notifyAsteroidExplosion() called (AIRobot)");

        //robot has to move (she/he is protected at the explosion)
        move();

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * counter for decision
     */
    private int decisionCounterStratOne = 0;

    @Override
    public void doAction() {
        log.log(Level.INFO, "doAction called");
        CallStackViewer.getInstance().methodStartsLogCall("doAction() called (AIRobot)");

        //TODO implement better action choose, and decisioning
        stratOne();

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Strategy 1
     * 1 move, then 3 drill --- repeat it (robots)
     * 1 move, then raw material search, if found mined
     */
    private void stratOne() {
        //TODO (OPT) create more strategies
        log.log(Level.INFO, "strat_1 called: AIRobot on strat1");

        int mod = 2;
        //1 move, than 3 drill
        log.log(Level.TRACE, "make decision");
        if (decisionCounterStratOne % mod == 0) {
            move();
        } else{
            mine();
        }

        decisionCounterStratOne++;

        //not to increment this variable, give a bound to it - ring structure
        if (decisionCounterStratOne == mod) {
            decisionCounterStratOne = 0;
        }
    }
}