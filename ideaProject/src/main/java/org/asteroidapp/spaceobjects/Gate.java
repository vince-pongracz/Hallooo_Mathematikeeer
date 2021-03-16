package org.asteroidapp.spaceobjects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.interfaces.Observable;
import org.asteroidapp.resources.Resource;

/**
 * Class for teleport gates.
 */
public class Gate extends SteppableSpaceObject implements Observable {

    /**
     * logger for Gate
     */
    private static final Logger log = LogManager.getLogger(Gate.class.getSimpleName());

    /**
     * Another gate connected to this one, allowing travel between the two.
     */
    private Gate gatePair = null;

    /**
     * Default constructor
     */
    public Gate(Position position) {
        super(position);
        log.log(Level.TRACE, "Gate constructor called.");
        log.log(Level.INFO, "New gate created.");
    }

    @Override
    public String getName() {
        log.log(Level.TRACE, "Gate's getName called: returns gate FOR NOW");
        return "gate";
    }

    @Override
    public int drillLayer() {
        log.log(Level.TRACE, "Gate's drillLayer called: uninterpretable, returns -1");
        return -1;
    }

    /**
     * Overridden method to get layerThickness
     *
     * @return actual layer thickness, or -1, if it's not interpretable, it is not an asteroid so it is -1
     */
    @Override
    public int getLayerThickness() {
        return -1;
    }

    @Override
    public Resource mineResource() {
        log.log(Level.TRACE, "Gate's mineResource called: no resource, returns null");
        return null;
    }

    @Override
    public boolean addResourceToCore(Resource resource) {
        log.log(Level.TRACE, "Gate's addResourceToCore called: can't hold resource, returns false");
        return false;
    }

    @Override
    public void setMyPosition(Position newPosition) {
        log.log(Level.TRACE, "Gate's setMyPosition called");
        this.position = newPosition;
    }

    @Override
    public boolean isActive() {
        log.log(Level.TRACE, "Gate's isActive called");
        if (this.position != null && gatePair.position != null) {
            log.log(Level.INFO, "This gate is active, you can teleport");
            return true;
        } else {
            log.log(Level.INFO, "This gate is inactive, its pair is yet to be placed");
            return false;
        }
    }

    @Override
    public boolean setPair(Gate pairGate) {
        log.log(Level.TRACE, "Gate's setPair called");
        if (pairGate != null) {
            this.gatePair = pairGate;
            return true;
        } else return false;
    }

    @Override
    public SteppableSpaceObject getPair() {
        log.log(Level.TRACE, "Gate's getPair called");
        return gatePair;
    }

    @Override
    public String getInfo() {
        log.log(Level.TRACE, "Gate's getInfo called");
        return "Some example info";
    }
}