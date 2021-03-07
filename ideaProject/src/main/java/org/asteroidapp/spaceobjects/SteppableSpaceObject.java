package org.asteroidapp.spaceobjects;

import org.asteroidapp.interfaces.Observable;
import org.asteroidapp.entities.Entity;
import org.asteroidapp.resources.Resource;

import java.util.*;

/**
 *
 */
public abstract class SteppableSpaceObject implements Observable {

    /**
     * Default constructor
     */
    public SteppableSpaceObject(Position position) {
        playersOnMe = new HashSet<>();
        this.position = position;
    }

    /**
     *
     */
    protected Set<Entity> playersOnMe;

    /**
     *
     */
    protected Position position;

    /**
     * @return
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @return
     */
    public abstract String getName();

    /**
     * @return
     */
    public abstract boolean drillLayer();

    /**
     * @return
     */
    public abstract Resource mineResource();

    /**
     * @param resource
     * @return
     */
    public abstract boolean addResourceToCore(Resource resource);

    /**
     * @param newPosition
     */
    public abstract void setMyPosition(Position newPosition);

    /**
     * @return
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
     * @param leavingEntity
     */
    //TODO check
    public void checkOut(Entity leavingEntity) {
        playersOnMe.remove(leavingEntity);
    }

    /**
     * @param newEntity
     */
    //TODO check
    public void checkIn(Entity newEntity) {
        playersOnMe.add(newEntity);
    }

    public abstract String getInfo();

}