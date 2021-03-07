package org.asteroidapp.entities;

import org.asteroidapp.interfaces.Observer;
import org.asteroidapp.spaceobjects.SteppableSpaceObject;

import java.util.*;

/**
 *
 */
public abstract class Entity implements Observer {

    /**
     * Default constructor
     */
    public Entity(String name, SteppableSpaceObject droppingPlace) {
        this.name = name;
        onSpaceObject = droppingPlace;
    }

    /**
     *
     */
    private String name;

    public String getName(){
        return name;
    }

    /**
     *
     */
    protected SteppableSpaceObject onSpaceObject;

    /**
     *
     */
    public void move(){
        var neighbours = listMyNeighbours();
        var nextSpaceObject = chooseNeighbour(neighbours);
        onSpaceObject.checkOut(this);
        setMySpaceObject(nextSpaceObject);
        nextSpaceObject.checkIn(this);
    }

    /**
     *
     */
    public abstract void drill();

    /**
     *
     */
    public abstract void die();

    /**
     * @return
     */
    public Set<SteppableSpaceObject> listMyNeighbours() {
        // TODO implement here
        return null;
    }

    /**
     * @param newOnPlace
     */
    protected void setMySpaceObject(SteppableSpaceObject newOnPlace) {
        if (newOnPlace != null) {
            onSpaceObject = newOnPlace;
        } else {
            //NOP
        }
    }

    /**
     * @return
     */
    public SteppableSpaceObject getMySpaceObject() {
        return onSpaceObject;
    }

    /**
     * @param neighbours
     * @return
     */
    protected abstract SteppableSpaceObject chooseNeighbour(Set<SteppableSpaceObject> neighbours);

    /**
     *
     */
    public abstract void notifyFlairEvent();

    /**
     *
     */
    public abstract void notifyFlairDanger();

    /**
     *
     */
    public abstract void notifyAsteroidExplosion();

}