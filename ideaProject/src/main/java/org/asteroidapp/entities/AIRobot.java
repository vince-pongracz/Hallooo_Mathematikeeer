package org.asteroidapp.entities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AsteroidZone;
import org.asteroidapp.GameController;
import org.asteroidapp.interfaces.AutoEntity;
import org.asteroidapp.interfaces.Drill;
import org.asteroidapp.spaceobjects.SteppableSpaceObject;
import org.asteroidapp.util.CallStackViewer;

import java.util.*;

/**
 *
 */
public class AIRobot extends Entity implements Drill, AutoEntity {

    /**
     * logger for AIRobot
     */
    private static final Logger log = LogManager.getLogger(AIRobot.class.getSimpleName());

    /**
     * Default constructor
     */
    public AIRobot(String name, SteppableSpaceObject creationPlace) {
        super(name, creationPlace);

        log.log(Level.INFO, "AIRobot constructor called");

        CallStackViewer.getInstance().methodStartsLogCall("AIRobot constructor called");

        if (creationPlace != null && name != null) {
            onSpaceObject = creationPlace;
            log.log(Level.TRACE, "AIRobot created on {}", creationPlace.getName());
        } else {
            log.log(Level.FATAL, "null parameters in constructor!");
        }

        CallStackViewer.getInstance().methodReturns();
    }


    @Override
    public boolean drill() {
        log.log(Level.INFO, "drill called (AIRobot's drill)");

        CallStackViewer.getInstance().methodStartsLogCall("drill() called (AIRobot's drill)");

        boolean ret = false;
        if (onSpaceObject.drillLayer()) {
            log.log(Level.INFO, "drill success!");
            ret = true;
        } else {
            decisionCounterStratOne += 3;
            log.log(Level.INFO, "drill is not successful!");
            ret = false;
        }

        CallStackViewer.getInstance().methodReturns();
        return ret;
    }

    @Override
    protected void die() {
        log.log(Level.INFO, "die called (AIRobot's die)");
        CallStackViewer.getInstance().methodStartsLogCall("die() called (AIRobot)");

        //delete robot's reference from collections (every collections)
        //checkout form onSpaceObject
        onSpaceObject.checkOut(this);

        //elv ennek így jónak kellene lennie, nem szabadna később se exceptiont dobnia...
        onSpaceObject = null;

        //checkout form sun
        AsteroidZone.getInstance().getSun().checkOut(this);
        //remove form game
        GameController.getInstance().removeBot(this);

        CallStackViewer.getInstance().methodReturns();
    }

    @Override
    public SteppableSpaceObject chooseNeighbour(Set<SteppableSpaceObject> neighbours) {
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
     * 1 move, then 3 drill --- repeat it
     */
    private void stratOne() {
        //TODO (OPT) create more strategies
        log.log(Level.INFO, "strat_1 called: AIRobot on strat1");

        int mod = 4;
        //1 move, than 3 drill
        log.log(Level.TRACE, "make decision");
        switch (decisionCounterStratOne % mod) {
            case 0:
                move();
                break;
            case 1:
            case 2:
            case 3:
                drill();
                break;
            default:
                break;
        }

        decisionCounterStratOne++;

        //not to increment this variable, give a bound to it - ring structure
        if (decisionCounterStratOne == mod) {
            decisionCounterStratOne = 0;
        }
    }
}