package org.asteroidapp.MODELL.spaceobjects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.MODELL.EventType;
import org.asteroidapp.MODELL.interfaces.Observable;
import org.asteroidapp.MODELL.interfaces.Observer;
import org.asteroidapp.util.CallStackViewer;

import java.util.*;

/**
 * Abstract class for SteppableSpaceObjects like Asteroid and Gate
 */
public abstract class SteppableSpaceObject implements Observable {

    /**
     * Logger for this class
     */
    private static final Logger log = LogManager.getLogger(SteppableSpaceObject.class.getSimpleName());

    /**
     * Default constructor
     */
    public SteppableSpaceObject() {
        log.log(Level.INFO, "SteppableSpaceObject constructor called");

        this.position = null;

        log.log(Level.TRACE, "SteppableSpaceObject created");
    }

    /**
     * Default constructor
     */
    public SteppableSpaceObject(Position position) {
        log.log(Level.INFO, "SteppableSpaceObject constructor called");

        if (position != null) {
            this.position = position;
        } else {
            log.log(Level.FATAL, "Wrong position! (position is null)");
        }

        log.log(Level.TRACE, "SteppableSpaceObject created");
    }

    /**
     * set of entities
     */
    protected Set<Observer> observers = new HashSet<>();;

    /**
     * SpaceObject's position
     */
    protected Position position = null;

    /**
     * Getter on position
     *
     * @return position my position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Abstract getter method on name
     *
     * @return name or null/empty string, when SteppableSpaceObject has no name (because he/she is Gate)
     */
    public abstract String getName();

    /**
     * State of a SteppableSpaceObject
     *
     * @return true if SpaceObject is active (can be used)
     * false if it's not
     */
    public abstract boolean isActive();

    /**
     * Checking out from a SteppableSpaceObject
     *
     * @param leavingObserver Entity, which leaves the object
     */
    public void checkOut(Observer leavingObserver) {
        log.log(Level.INFO, "checkOut called");
        CallStackViewer.getInstance().methodStartsLogCall("checkOut() called");

        if (leavingObserver != null) {
            Boolean temp = observers.remove(leavingObserver);
            log.log(Level.TRACE, "Entity removed: {}", temp.toString());
        } else {
            //NOP
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Check in to a SpaceObject
     *
     * @param newObserver to be added to SpaceObject
     */
    public void checkIn(Observer newObserver) {
        log.log(Level.INFO, "checkIn called");
        CallStackViewer.getInstance().methodStartsLogCall("checkIn() called");

        if (newObserver != null) {
            Boolean temp = observers.add(newObserver);
            log.log(Level.TRACE, "Entity added: {}", temp.toString());
            newObserver.notify(EventType.REFRESH);
        } else {
            //NOP
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Info about SpaceObject
     *
     * @return information
     */
    public abstract String getInfo();

    /**
     * Returns the target spcaeObject, which will be set as new onSpaceObject of an Entity/MoveableObserver
     *
     * @return the SpaceObject behind the SpaceObject
     */
    public abstract Asteroid getTarget();
}