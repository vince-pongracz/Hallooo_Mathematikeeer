package org.asteroidapp.spaceobjects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.interfaces.EventObservable;
import org.asteroidapp.resources.Empty;
import org.asteroidapp.resources.Resource;
import org.asteroidapp.spaceobject.asteroid.Core;
import org.asteroidapp.spaceobject.asteroid.Layer;

/**
 * class for Asteroid
 */
public class Asteroid extends SteppableSpaceObject implements EventObservable {

    /**
     * Logger for Asteroid class
     */
    private static final Logger log = LogManager.getLogger(Asteroid.class.getSimpleName());

    /**
     * Default constructor
     */
    public Asteroid(String name, Position position, Resource initResource, int layer) {
        super(position);
        log.log(Level.INFO, "Asteroid constructor called");
        this.name = "";
        setName(name);

        this.core = new Core(initResource);
        this.layer = new Layer(layer);
    }

    /**
     * Setter on name
     *
     * @param name Asteroid's name
     */
    private void setName(String name) {
        //TODO check name at AsteroidZone, exception, etc;
        if (name != null && !name.equals("")) {
            this.name = name;
        } else {
            log.log(Level.FATAL, "name is null");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean drillLayer() {
        int result = layer.thinIt();

        var resource = core.getResource();
        if (result == 0 && resource != null && resource.isRadioActive()) {
            notifyAboutDieEvent();
        }

        //because
        return true;
    }

    @Override
    public Resource mineResource() {
        log.log(Level.INFO, "mineResource called");
        if (layer.getThickness() == 0) {
            return core.getResource();
        } else {
            log.log(Level.INFO, "Resource cannot mined - layer is too big!");
            return null;
        }
    }

    @Override
    public boolean addResourceToCore(Resource resource) {
        log.log(Level.INFO, "addResourceToCore called");

        if (resource != null) {
            if (layer.getThickness() == 0 && core.getResource().equals(new Empty())) {
                core.setResource(resource);
                return true;
            } else {
                log.log(Level.INFO, "Resource cannot mined - layer is too big!");
            }
        }
        return false;
    }

    @Override
    public void setMyPosition(Position newPosition) {
        //NOP for asteroid
    }


    @Override
    public boolean isActive() {
        return true;
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
    private String name;

    /**
     *
     */
    private boolean closeToSun;


    protected Core core;
    protected Layer layer;

    /**
     *
     */
    protected void explode() {
        // TODO implement here
    }

    /**
     *
     */
    public void notifyAboutDanger() {
        // TODO implement here
    }

    /**
     *
     */
    public void notifyAboutDieEvent() {
        // TODO implement here
    }

}