package org.asteroidapp.spaceobjects;

import org.asteroidapp.resources.Empty;
import org.asteroidapp.resources.Resource;
import org.asteroidapp.spaceobject.asteroid.Core;
import org.asteroidapp.spaceobject.asteroid.Layer;

public class HomeAsteroid extends Asteroid {
    /**
     * Default constructor
     *
     * @param position
     * @param initResource
     */
    public HomeAsteroid(Position position, Resource initResource) {
        super("Home", position, initResource, 0);

        //to override Asteroid core, layer constructions
        core = null;
        core = new Core(5, new Empty());
        layer = null;
        layer = new Layer(0);
    }

    @Override
    public boolean drillLayer() {
        return true;
    }

    @Override
    public Resource mineResource() {
        return core.getResource().get(0);
    }

    @Override
    protected void explode() {
        //NOP
    }
}
