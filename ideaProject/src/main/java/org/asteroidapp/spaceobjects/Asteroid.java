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
import org.asteroidapp.util.CallStackViewer;
import org.asteroidapp.util.ConsoleUI;

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
        CallStackViewer.getInstance().methodStartsLogCall("Asteroid constructor called");

        this.name = "";
        setName(name);

        this.core = new Core(initResource);
        this.layer = new Layer(layer);

        closeToSun = position.distanceFrom(AsteroidZone.getInstance().getSun().getPosition()) < AsteroidZone.defOfCloseToSun;

        log.log(Level.TRACE, "new Asteroid created");
        CallStackViewer.getInstance().methodReturns();
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
    public boolean drillLayer() {
        log.log(Level.INFO, "drillLayer called, before drill was the layer: {}", layer.getThickness());
        CallStackViewer.getInstance().methodStartsLogCall("drillLayer() called (Asteroid)");

        //thin layer
        var result = layer.getThickness() != layer.thinIt();

        var resource = core.popResource();
        core.pushResource(resource);

        //check explosion conditions
        if (layer.getThickness() == 0 && resource != null && closeToSun && resource.isRadioActive(position)) {
            //this calls explosion
            log.log(Level.INFO, "Asteroid has radioactive core _and_ it's close to Sun --> EXPLODE");
            notifyAboutDieEvent();
        } else if (layer.getThickness() == 0 && resource != null && resource.equals(new FrozenWater()) && closeToSun) {
            //when it is FrozenWater --> drop it, and set empty
            core.popResource();
            core.pushResource(new Empty());
        }

        CallStackViewer.getInstance().methodReturns();
        //return the actual thickness
        return result;
    }

    /**
     * Overridden method to get layerThickness
     *
     * @return actual layer thickness, or -1, if it's not interpretable
     */
    @Override
    public int getLayerThickness() {
        return layer.getThickness();
    }

    /**
     * can return with null! --- and it's okay, and good :)
     */
    @Override
    public Resource mineResource() {
        log.log(Level.INFO, "mineResource called");
        CallStackViewer.getInstance().methodStartsLogCall("mineResource() called (Asteroid)");

        Resource ret = null;
        //if no layer --> can be mined
        if (layer.getThickness() == 0) {
            CallStackViewer.getInstance().methodReturns();
            ret = core.popResource();
        } else {
            log.log(Level.INFO, "Resource cannot mined - layer is too big!");
            ret = null;
        }

        CallStackViewer.getInstance().methodReturns();
        return ret;
    }

    @Override
    public boolean addResourceToCore(Resource resource) {
        log.log(Level.INFO, "addResourceToCore called");
        CallStackViewer.getInstance().methodStartsLogCall("addResourceToCore() called (Asteroid)");

        boolean success = false;
        if (resource != null && layer.getThickness() == 0) {
            success = core.pushResource(resource);
        } else {
            //NOP
        }

        CallStackViewer.getInstance().methodReturns();
        return success;
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
        ConsoleUI.getInstance().sendMessageToConsole("name: " + name + ", layer: " + layer.getThickness() + ", core: " + core.getCoreInfo() + ", isCloseToSun: " + closeToSun +
                ", position: x=" + position.getX() + " y=" + position.getY());
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
     *
     * @note DO NOT handle the checkIn, checkOut here!
     */
    protected void explode() {
        log.log(Level.INFO, "explode called");
        CallStackViewer.getInstance().methodStartsLogCall("explode() called (Asteroid)");

        //let these resources go
        core = null;
        layer = null;

        var iter = entitiesOnMe.iterator();
        while (iter.hasNext()) {
            var entityItem = iter.next();
            iter.remove();
            entityItem.notifyAsteroidExplosion();
        }

        //remove from world
        AsteroidZone.getInstance().removeSpaceObject(this);

        CallStackViewer.getInstance().methodReturns();
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
        CallStackViewer.getInstance().methodStartsLogCall("notifyAboutDieEvent() called (Asteroid)");

        explode();

        CallStackViewer.getInstance().methodReturns();
    }
}