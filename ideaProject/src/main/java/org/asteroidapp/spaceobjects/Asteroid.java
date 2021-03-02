package org.asteroidapp.spaceobjects;

import org.asteroidapp.EventObservable;
import org.asteroidapp.resources.Resource;
import org.asteroidapp.spaceobject.asteroid.Core;
import org.asteroidapp.spaceobject.asteroid.Layer;

/**
 *
 */
public class Asteroid extends SteppableSpaceObject implements EventObservable {

    /**
     * Default constructor
     */
    public Asteroid(String name, Position position, Resource initResource, int layer) {
        super(position);
        this.name = "";
        setName(name);

        this.core = new Core(initResource);
        this.layer = new Layer(layer);
    }

    private void setName(String name) {
        //TODO check name at AsteroidZone, exception, etc;
        if (name != null && !name.equals("")) {
            this.name = name;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean drillLayer() {
        int result = layer.thinIt();

        if (result == 0 && core.getResource() != null) {
            var res = core.getResource();
            if (res.get(0).isRadioActive()) {
                explode();
            } else {
//TODO make it clear
            }

        }

        return false;
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

    /**
     *
     */
    private String name;

    /**
     *
     */
    private boolean closeToSun;


    /**
     *
     */
    private Position position;

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