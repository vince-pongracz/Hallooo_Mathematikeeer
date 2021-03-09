package org.asteroidapp.spaceobjects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AsteroidZone;
import org.asteroidapp.interfaces.EventObservable;
import org.asteroidapp.resources.Empty;
import org.asteroidapp.resources.FrozenWater;
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

        log.log(Level.TRACE, "new Asteroid created");
    }

    /**
     * Setter on name
     *
     * @param name Asteroid's name
     */
    private void setName(String name) {
        log.log(Level.TRACE, "setName called");
        //TODO check name at AsteroidZone, exception, etc;

        //check on name
        if (name != null && !name.equals("")) {
            this.name = name;
        } else {
            log.log(Level.FATAL, "name is null - name cannot set");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int drillLayer() {
        log.log(Level.INFO, "drillLayer called");

        //thin layer
        var result = layer.thinIt();

        //like pop
        var resource = core.popResource();

        //like push
        //because of stack behaviour (later come)
        core.pushResource(resource);

        //TODO closeToSun part
        //check explosion conditions
        if (result == 0 && resource != null && resource.isRadioActive() && closeToSun) {
            //this calls explosion
            log.log(Level.INFO, "Asteroid has radioactive core _and_ it's close to Sun --> EXPLODE");
            notifyAboutDieEvent();
        } else if (result == 0 && resource != null && resource.equals(new FrozenWater()) && closeToSun) {
            //TODO hopefully this condition is enough to sublimate FrozenWater when it's close to sun

            //when it is FrozenWater --> drop it, and set empty
            core.popResource();
            core.pushResource(new Empty());
        }
        //return the actual thickness
        return result;
    }

    /**
     * can return with null! --- and it's okay, and good :)
     */
    @Override
    public Resource mineResource() {
        log.log(Level.INFO, "mineResource called");

        //if no layer --> can be mined
        if (layer.getThickness() == 0) {
            return core.popResource();
        } else {
            log.log(Level.INFO, "Resource cannot mined - layer is too big!");
            return null;
        }
    }

    @Override
    public boolean addResourceToCore(Resource resource) {
        log.log(Level.INFO, "addResourceToCore called");

        if (resource != null) {
            if (layer.getThickness() == 0 && core.popResource().equals(new Empty())) {
                core.pushResource(resource);
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
        log.log(Level.TRACE, "No pair for asteroid - null returned");
        return null;
    }

    @Override
    public String getInfo() {
        //TODO write some valuable information here
        return "exampleInfo";
    }

    /**
     * name of the asteroid
     */
    private String name = "";

    /**
     * definition of close to sun
     */
    private boolean closeToSun = false;

    /**
     * core of the asteroid
     */
    protected Core core = null;
    /**
     * layer of the asteroid
     */
    protected Layer layer = null;

    /**
     * explode asteroid (and notify observers, what they have to do!)
     */
    protected void explode() {
        log.log(Level.INFO, "explode called");

        //let these resources go
        core = null;
        layer = null;

        //DO NOT handle the checkIn, checkOut here!
        //this is the entity's responsibility
        for (var item : playersOnMe) {
            item.notifyAsteroidExplosion();
        }

        //remove from world
        AsteroidZone.getInstance().removeSpaceObject(this);

        //gc eats this object sooner or later
    }

    /**
     * Notify observers about danger
     * Asteroid don't send notification about its explosion (before it happens)
     */
    public void notifyAboutDanger() {
        //NOP for asteroid
    }

    /**
     * Notify observers, they have to die or handle this event
     */
    public void notifyAboutDieEvent() {
        explode();
    }
}