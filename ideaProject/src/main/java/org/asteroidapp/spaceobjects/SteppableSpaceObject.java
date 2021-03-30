package org.asteroidapp.spaceobjects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.interfaces.Observable;
import org.asteroidapp.entities.Entity;
import org.asteroidapp.resources.Resource;
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
    public SteppableSpaceObject(Position position) {
        log.log(Level.INFO, "SteppableSpaceObject constructor called");

        entitiesOnMe = new HashSet<>();

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
    protected Set<Entity> entitiesOnMe = null;

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
     * Abstract method for drill
     *
     * @return actual core thickness, or -1, if it's not interpretable
     */
    public abstract int drillLayer();

    /**
     * Abstract method to get layerThickness
     *
     * @return actual layer thickness, or -1, if it's not interpretable
     */
    public abstract int getLayerThickness();

    /**
     * Mining a resource
     *
     * @return resource, which is mined
     */
    public abstract Resource mineResource();

    /**
     * Add a resource to the core
     *
     * @param resource to be added
     * @return true if successfully added
     * false if can't add
     */
    public abstract boolean addResourceToCore(Resource resource);

    /**
     * Sets a new position for object
     *
     * @param newPosition to be set
     */
    public abstract void setMyPosition(Position newPosition);

    /**
     * State of a SteppableSpaceObject
     *
     * @return true if SpaceObject is active (can be used)
     * false if it's not
     */
    public abstract boolean isActive();

    /**
     * @param pairGate
     * @return
     */
    public abstract boolean setPair(Gate pairGate);

    /**
     * @return
     */
    public abstract SteppableSpaceObject getPair();

    /**
     * Checking out from a SteppableSpaceObject
     *
     * @param leavingEntity Entity, which leaves the object
     */
    public void checkOut(Entity leavingEntity) {
        log.log(Level.INFO, "checkOut called");
        CallStackViewer.getInstance().methodStartsLogCall("checkOut() called");

        if (leavingEntity != null) {
            Boolean temp = entitiesOnMe.remove(leavingEntity);
            log.log(Level.TRACE, "Entity removed: {}", temp.toString());
        } else {
            //NOP
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Check in to a SpaceObject
     *
     * @param newEntity to be added to SpaceObject
     */
    //TODO check
    public void checkIn(Entity newEntity) {
        log.log(Level.INFO, "checkIn called");
        CallStackViewer.getInstance().methodStartsLogCall("checkIn() called");

        if (newEntity != null) {
            Boolean temp = entitiesOnMe.add(newEntity);
            log.log(Level.TRACE, "Entity added: {}", temp.toString());
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

}