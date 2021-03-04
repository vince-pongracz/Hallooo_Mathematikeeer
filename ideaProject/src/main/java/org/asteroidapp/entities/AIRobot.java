package org.asteroidapp.entities;

import org.asteroidapp.spaceobjects.SteppableSpaceObject;

import java.util.*;

/**
 *
 */
public class AIRobot extends Entity {

    /**
     * Default constructor
     */
    public AIRobot(String name, SteppableSpaceObject creationPlace) {
        super(name, creationPlace);
        if (creationPlace != null) {
            onSpaceObject = creationPlace;
        }
    }

    @Override
    public void drill() {
        //TODO if can't drill?
        onSpaceObject.drillLayer();
    }

    @Override
    public void die() {
        onSpaceObject.checkOut(this);
        onSpaceObject = null;
        //TODO checkout from Sun

    }

    @Override
    protected SteppableSpaceObject chooseNeighbour(Set<SteppableSpaceObject> neighbours) {

        var neighbourListToShuffle = new ArrayList<SteppableSpaceObject>(neighbours);
        Collections.shuffle(neighbourListToShuffle);
        var randomNumber = new Random(neighbourListToShuffle.size()).nextInt();
        return neighbourListToShuffle.get(randomNumber);
    }

    @Override
    public void notifyFlairEvent() {
        die();
    }

    @Override
    public void notifyFlairDanger() {
        //TODO AI in Robots... (opt)
        //NOP for Robots...
        //or some logic required for to make a hole and hide
    }

    @Override
    public void notifyAsteroidExplosion() {
        move();
    }
}