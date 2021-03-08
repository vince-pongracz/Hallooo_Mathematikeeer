package org.asteroidapp.spaceobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.entities.AIRobot;
import org.asteroidapp.entities.Entity;
import org.asteroidapp.resources.Resource;

import java.util.*;

/**
 *
 */
public class Gate extends SteppableSpaceObject {

    /**
     * logger for Gate
     */
    private static final Logger log = LogManager.getLogger(Gate.class.getSimpleName());

    /**
     * Default constructor
     */
    public Gate(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int drillLayer() {
        return -1;
    }

    @Override
    public Resource mineResource() {
        return null;
    }

    @Override
    public boolean addResourceToCore(Resource resource) {
        return false;
    }

    @Override
    public void setMyPosition(Position newPosition) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean setPair(Gate pairGate) {
        return false;
    }

    @Override
    public SteppableSpaceObject getPair() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }

    /**
     *
     */
    private Set<Entity> playersOnMe;

    /**
     *
     */
    private Gate gatePair;

}