package org.asteroidapp.entities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AsteroidZone;
import org.asteroidapp.GameController;
import org.asteroidapp.spaceobjects.SteppableSpaceObject;

import java.util.*;

/**
 *
 */
public class AIRobot extends Entity {

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
        if (creationPlace != null && name != null) {
            onSpaceObject = creationPlace;
        } else {
            log.log(Level.FATAL, "null parameters in constructor!");
        }
    }


    @Override
    public boolean drill() {
        log.log(Level.INFO, "drill called (AIRobot's drill)");

        if (onSpaceObject.drillLayer() > 0) {
            log.log(Level.INFO, "drill success!");
            return true;
        } else {
            decisionCounterStratOne += 3;
            log.log(Level.INFO, "drill is not successful!");
            return false;
        }
    }

    @Override
    protected void die() {
        log.log(Level.INFO, "die called (AIRobot's die)");

        //delete robot's reference from collections (every collections)
        //checkout form onSpaceObject
        onSpaceObject.checkOut(this);

        //elv ennek így jónak kellene lennie, nem szabadna később se exceptiont dobnia...
        onSpaceObject = null;

        //checkout form sun
        AsteroidZone.getInstance().getSun().checkOut(this);
        //remove form game
        GameController.getInstance().removeBot(this);

    }

    @Override
    protected SteppableSpaceObject chooseNeighbour(Set<SteppableSpaceObject> neighbours) {
        //TODO (OPT): don't choose a spaceObject, which is empty
        //TODO (OPT): don't choose an asteroid, where robot came from

        log.log(Level.INFO, "chooseNaighbour called");
        //nullcheck
        if (neighbours != null) {
            //convert set to a list to shuffle
            var neighbourListToShuffle = new ArrayList<SteppableSpaceObject>(neighbours);
            Collections.shuffle(neighbourListToShuffle);
            //generate random to decision
            var randomNumber = new Random(neighbourListToShuffle.size()).nextInt();
            return neighbourListToShuffle.get(randomNumber);
        } else {
            log.log(Level.FATAL, "Given collection in parameter is null!");
            return null;
        }
    }

    @Override
    public void notifyFlairEvent() {
        log.log(Level.INFO, "notifyFlairEvent called");
        log.log(Level.TRACE, "in a flair, robot has to die");
        die();
    }

    @Override
    public void notifyFlairDanger() {
        //TODO AI in Robots... (opt)
        log.log(Level.INFO, "notifyFlairDanger called");
        log.log(Level.INFO, "Hey bot, you should hide!");
        //NOP for Robots...
        //or some logic required to make a hole and hide
    }

    @Override
    public void notifyAsteroidExplosion() {
        log.log(Level.INFO, "notifyAsteroidExplosion called");

        //robot has to move (she/he is protected at the explosion)
        move();
    }

    /**
     * counter for decision
     */
    private int decisionCounterStratOne = 0;

    @Override
    public void doAction() {
        log.log(Level.INFO, "doAction called");
        //TODO implement better action choose, and decisionmaking
        stratOne();
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