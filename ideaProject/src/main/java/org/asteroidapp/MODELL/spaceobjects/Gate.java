package org.asteroidapp.MODELL.spaceobjects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.MODELL.EventType;
import org.asteroidapp.MODELL.interfaces.MoveableObserver;
import org.asteroidapp.CONTROLLER.AsteroidZone;
import org.asteroidapp.MODELL.interfaces.Observer;
import org.asteroidapp.util.CallStackViewer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for teleport gates.
 */
public class Gate extends SteppableSpaceObject implements MoveableObserver {
    /**
     * logger for Gate
     */
    private static final Logger log = LogManager.getLogger(Gate.class.getSimpleName());

    /**
     * Another gate connected to this one, allowing travel between the two.
     */
    private Gate gatePair = null;

    /**
     * The Asteroid at which the gate is placed
     */
    private Asteroid currentAsteroid = null;

    /**
     * Stores if the gate has been hit by a sunflare
     */
    private boolean affectedByFlair = false;

    /**
     * Default constructor
     */
    public Gate() {
        super();

        CallStackViewer.getInstance().methodStartsLogCall("Gate constructor called");

        log.log(Level.TRACE, "Gate constructor called. New gate created.");

        CallStackViewer.getInstance().methodReturns();
    }

    @Override
    public String getName() {
        log.log(Level.TRACE, "Gate's getName called: returns gate");
        if (this.getTarget() != null) {
            return "Gate to " + this.getTarget().getName() + " from " + this.currentAsteroid.getName();
        } else {
            return "Gate on " + currentAsteroid.getName();
        }
    }

    /**
     * Sets a new position for object
     *
     * @param asteroid to be set
     */
    public void setMyAsteroid(Asteroid asteroid) {
        log.log(Level.TRACE, "Gate's setMyAsteroid called");
        this.currentAsteroid = asteroid;
        this.position = new Position(40 + asteroid.position.getX(), 40 + asteroid.position.getY());
        this.signalizeUpdate(EventType.REFRESH);
    }

    @Override
    public boolean isActive() {
        log.log(Level.TRACE, "Gate's isActive called");
        CallStackViewer.getInstance().methodStartsLogCall("isActive() called");

        boolean ret = false;
        if (this.getCurrentAsteroid() != null && gatePair.getCurrentAsteroid() != null) {
            log.log(Level.INFO, "This gate is active, you can teleport");
            ret = true;
        } else {
            log.log(Level.INFO, "This gate is inactive, its pair is yet to be placed");
            ret = false;
        }
        CallStackViewer.getInstance().methodReturns();
        return ret;
    }


    /**
     * @param pairGate
     * @return
     */
    public boolean setPair(Gate pairGate) {
        log.log(Level.TRACE, "Gate's setPair called");
        CallStackViewer.getInstance().methodStartsLogCall("setPair() called (Gate)");

        boolean ret = false;
        if (pairGate != null) {
            this.gatePair = pairGate;
            ret = true;
        } else {
            ret = false;
        }

        CallStackViewer.getInstance().methodReturns();
        return ret;
    }

    @Override
    public String getInfo() {
        log.log(Level.TRACE, "Gate's getInfo called");
        this.signalizeUpdate(EventType.REFRESH);
        return "Some example info";
    }

    @Override
    public Asteroid getTarget() {
        if (isActive() && this.gatePair != null && this.gatePair.isActive()) {
            return this.gatePair.getCurrentAsteroid();
        } else {
            return this.getCurrentAsteroid();
        }
    }

    @Override
    public void move(SteppableSpaceObject nextSpaceObject) {
        var neighbourIter = AsteroidZone.getInstance().getIterOnSpaceObjects();
        var allZoneObject = new ArrayList<SteppableSpaceObject>();
        while (neighbourIter.hasNext()) {
            allZoneObject.add(neighbourIter.next());
        }
        Collections.shuffle(allZoneObject);
        int i = 0;
        SteppableSpaceObject target = this;
        do {
            target = allZoneObject.remove(i);
            i++;
        }
        while (target.getTarget() == this.getCurrentAsteroid());

        currentAsteroid.checkOut(this);
        setMyAsteroid(target.getTarget());
        currentAsteroid.checkIn(this);
        this.signalizeUpdate(EventType.REFRESH);
    }

    public Asteroid getCurrentAsteroid() {
        return currentAsteroid;
    }

    private void notifyFlairEvent() {
        if (currentAsteroid != null) {
            if (currentAsteroid.getPosition().distanceFrom(AsteroidZone.getInstance().getSun().getPosition()) >= AsteroidZone.defOfCloseToSun) {
                move(null);
            }
        }
    }

    private void notifyAsteroidExplosion() {
        //destroy gate
        //or move gate
        //move is easier
        move(null);
    }

    @Override
    public void checkOut(Observer leavingObserver) {
        this.observers.remove(leavingObserver);
    }

    @Override
    public void checkIn(Observer newObserver) {
        this.observers.add(newObserver);
    }

    @Override
    public void signalizeUpdate(EventType event) {
        for (var obs : observers) {
            obs.notify(event);
        }
    }

    @Override
    public void notify(EventType eventType) {
        switch (eventType) {
            case EXPLOSION -> notifyAsteroidExplosion();
            case FLAIREVENT -> notifyFlairEvent();
        }
    }
}