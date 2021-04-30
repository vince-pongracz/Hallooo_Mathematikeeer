package org.asteroidapp.MODELL.entities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.MODELL.interfaces.AutoEntity;
import org.asteroidapp.MODELL.interfaces.EventType;
import org.asteroidapp.MODELL.interfaces.Mine;
import org.asteroidapp.MODELL.resources.Empty;
import org.asteroidapp.MODELL.resources.Resource;
import org.asteroidapp.MODELL.resources.ResourceStorage;
import org.asteroidapp.MODELL.spaceobjects.SteppableSpaceObject;
import org.asteroidapp.VIEW.drawables.UfoGraphic;
import org.asteroidapp.util.CallStackViewer;

/**
 *
 */
public class Ufo extends Entity implements Mine, AutoEntity {


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

        CallStackViewer.getInstance().methodStartsLogCall("Ufo constructor called");

        resources = new ResourceStorage();
        resources.setAllCapacity(UfoCapacity);

        this.checkIn(new UfoGraphic(this));

        CallStackViewer.getInstance().methodReturns();
    }

    @Override
    protected void die() {
        //NOP
    }

    @Override
    public boolean mine() {
        log.log(Level.INFO, "Mine called");
        CallStackViewer.getInstance().methodStartsLogCall("mine() called (Ufo)");

        Resource res = onAsteroid.mineResource();
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

    /**
     * counter for decision
     */
    private int decisionCounterStratOne = 0;

    @Override
    public void doAction() {
        log.log(Level.INFO, "doAction called");
        CallStackViewer.getInstance().methodStartsLogCall("doAction() called (Ufo)");

        stratOne();
        this.signalizeUpdate(EventType.REFRESH);

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Strategy 1
     * <p>
     * 1 move, then raw material search, if found mined
     */
    private void stratOne() {

        log.log(Level.INFO, "strat_1 called: Ufo on strat1");

        log.log(Level.TRACE, "make decision");
        if (decisionCounterStratOne % 2 == 0) {
            move(chooseNeighbour(listMyNeighbours()));
        } else {
            mine();
        }

        decisionCounterStratOne++;

        if (decisionCounterStratOne == 2) {
            decisionCounterStratOne = 0;
        }
    }

    @Override
    public void notify(EventType eventType) {
        switch (eventType){
            case FLAIRWARN -> log.log(Level.INFO, "Hey ufo, you should hide!");
            case EXPLOSION -> move(chooseNeighbour(listMyNeighbours()));
        }
    }
}