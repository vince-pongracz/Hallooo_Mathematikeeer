package org.asteroidapp.MODELL.entities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.CONTROLLER.AsteroidZone;
import org.asteroidapp.CONTROLLER.GameController;
import org.asteroidapp.MODELL.EventType;
import org.asteroidapp.MODELL.interfaces.*;
import org.asteroidapp.MODELL.spaceobjects.SteppableSpaceObject;
import org.asteroidapp.VIEW.drawables.AIRobotGraphic;
import org.asteroidapp.util.CallStackViewer;

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
            onAsteroid = creationPlace.getTarget();
            log.log(Level.TRACE, "AIRobot created on {}", creationPlace.getName());
        } else {
            log.log(Level.FATAL, "null parameters in constructor!");
        }

        this.checkIn(new AIRobotGraphic(this));
        CallStackViewer.getInstance().methodReturns();
    }


    @Override
    public boolean drill() {
        log.log(Level.INFO, "drill called (AIRobot's drill)");

        CallStackViewer.getInstance().methodStartsLogCall("drill() called (AIRobot's drill)");

        boolean ret = false;
        if (onAsteroid.drillLayer()) {
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
        onAsteroid.checkOut(this);

        //elv ennek így jónak kellene lennie, nem szabadna később se exceptiont dobnia...
        onAsteroid = null;

        //checkout form sun
        AsteroidZone.getInstance().getSun().checkOut(this);
        //remove form game
        GameController.getInstance().removeAutoEntity(this);

        this.signalizeUpdate(EventType.DELETE);

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

        //TODO implement better action choose, and decisionmaking
        stratOne();
        this.signalizeUpdate(EventType.REFRESH);

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
                move(chooseNeighbour(listMyNeighbours()));
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

    @Override
    public void notify(EventType eventType) {
        switch (eventType){
            case FLAIREVENT -> die();
            case EXPLOSION -> move(this.chooseNeighbour(listMyNeighbours()));
            case FLAIRWARN -> log.log(Level.INFO, "Hey bot, you should hide!");
        }
    }
}