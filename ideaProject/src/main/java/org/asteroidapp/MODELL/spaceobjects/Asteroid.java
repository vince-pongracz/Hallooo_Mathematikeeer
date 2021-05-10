package org.asteroidapp.MODELL.spaceobjects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.CONTROLLER.AsteroidZone;
import org.asteroidapp.MODELL.EventType;
import org.asteroidapp.MODELL.interfaces.Observable;
import org.asteroidapp.MODELL.resources.Empty;
import org.asteroidapp.MODELL.resources.FrozenWater;
import org.asteroidapp.MODELL.resources.Resource;
import org.asteroidapp.MODELL.spaceobjects.asteroid.Core;
import org.asteroidapp.MODELL.spaceobjects.asteroid.Layer;
import org.asteroidapp.VIEW.drawables.AsteroidGraphic;
import org.asteroidapp.util.CallStackViewer;
import org.asteroidapp.util.ConsoleUI;

/**
 * class for Asteroid
 */
public class Asteroid extends SteppableSpaceObject implements Observable {

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

        this.core = new Core(initResource);
        this.layer = new Layer(layer);

        closeToSun = position.distanceFrom(AsteroidZone.getInstance().getSun().getPosition()) < AsteroidZone.defOfCloseToSun;

        log.log(Level.TRACE, "new Asteroid created");
        this.name = "";
        setName(name);
        this.checkIn(new AsteroidGraphic(this));
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
            this.signalizeUpdate(EventType.REFRESH);
        } else {
            log.log(Level.FATAL, "name is null - name cannot set");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Method for drill
     *
     * @return actual core thickness, or -1, if it's not interpretable
     */
    public boolean drillLayer() {
        log.log(Level.INFO, "drillLayer called, before drill was the layer: {}", layer.getThickness());
        CallStackViewer.getInstance().methodStartsLogCall("drillLayer() called (Asteroid)");

        //thin layer
        var canWeDrillAgain = (layer.getThickness() != layer.thinIt());

        var resource = core.popResource();
        core.pushResource(resource);

        //check explosion conditions
        if (layer.getThickness() == 0 && resource != null && closeToSun && resource.isRadioActive(position)) {
            //this calls explosion, which notifies the observers
            signalizeUpdate(EventType.EXPLOSION);
            log.log(Level.INFO, "Asteroid has radioactive core _and_ it's close to Sun --> EXPLODE");
        } else if (layer.getThickness() == 0 && resource != null && resource.equals(new FrozenWater()) && closeToSun) {
            //when it is FrozenWater --> drop it, and set empty
            core.popResource();
            core.pushResource(new Empty());
        }

        signalizeUpdate(EventType.REFRESH);
        CallStackViewer.getInstance().methodReturns();
        //return the actual thickness

        return canWeDrillAgain;
    }

    /**
     * Method to get layerThickness
     *
     * @return actual layer thickness, or -1, if it's not interpretable
     */
    public int getLayerThickness() {
        return layer.getThickness();
    }

    /**
     * Mining a resource
     * <p>
     * can return with null! --- and it's okay, and good :)
     *
     * @return resource, which is mined
     */
    public Resource mineResource() {
        log.log(Level.INFO, "mineResource called");
        CallStackViewer.getInstance().methodStartsLogCall("mineResource() called (Asteroid)");

        Resource ret = null;
        //if no layer --> can be mined
        if (layer.getThickness() == 0) {
            ret = core.popResource();
        } else {
            log.log(Level.INFO, "Resource cannot mined - layer is too big!");
            ret = null;
        }

        CallStackViewer.getInstance().methodReturns();
        this.signalizeUpdate(EventType.REFRESH);
        return ret;
    }

    /**
     * Add a resource to the core
     *
     * @param resource to be added
     * @return true if successfully added
     * false if can't add
     */
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
        this.signalizeUpdate(EventType.REFRESH);
        return success;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public String getInfo() {
        StringBuilder infoBuilder = new StringBuilder();

        infoBuilder.append(getName()).append("\n");
        infoBuilder.append("Layer: ").append(getLayerThickness());

        if (getLayerThickness() == 0) {
            infoBuilder.append(this.core.getCoreInfo());
        }

        infoBuilder.append("\ncloseToSun: ").append(closeToSun);
        infoBuilder.append("\nCore: ").append(core.getCoreInfo());
        ConsoleUI.getInstance().sendMessageToConsole(infoBuilder.toString());
        return infoBuilder.toString();
    }

    @Override
    public Asteroid getTarget() {
        return this;
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

        var iter = observers.iterator();
        while (iter.hasNext()) {
            var entityItem = iter.next();
            iter.remove();
            entityItem.notify(EventType.EXPLOSION);
        }

        //remove from world
        AsteroidZone.getInstance().removeSpaceObject(this);
        this.signalizeUpdate(EventType.DELETE);

        CallStackViewer.getInstance().methodReturns();
        //gc eats this object sooner or later
    }

    /**
     * Notify observers, they have to die or handle this event
     */
    @Override
    public void signalizeUpdate(EventType event) {
        switch (event) {
            //explode sent appropriate notifications for observers
            case EXPLOSION -> explode();
            case DELETE, REFRESH -> {
                for (var obs : observers) {
                    obs.notify(event);
                }
            }
        }
    }
}