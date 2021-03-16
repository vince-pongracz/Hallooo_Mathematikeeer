package org.asteroidapp.spaceobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.resources.Empty;
import org.asteroidapp.resources.Resource;
import org.asteroidapp.spaceobject.asteroid.Core;
import org.asteroidapp.spaceobject.asteroid.Layer;
import org.asteroidapp.util.CallStackViewer;

public class HomeAsteroid extends Asteroid {

    /**
     * logger for HomeAsteroid
     */
    private static final Logger log = LogManager.getLogger(HomeAsteroid.class.getSimpleName());

    /**
     * Default constructor
     *
     * @param position
     * @param initResource
     */
    public HomeAsteroid(Position position, Resource initResource) {
        super("Home", position, initResource, 0);

        CallStackViewer.getInstance().logCall("HomeAsteroid constructor called");

        //to override Asteroid core, layer constructions
        core = null;
        core = new Core(5, new Empty());
        layer = null;
        layer = new Layer(0);

        CallStackViewer.getInstance().methodReturns();
    }

    @Override
    public int drillLayer() {
        CallStackViewer.getInstance().logCall("drillLayer() called (HomeAsteroid)");
        CallStackViewer.getInstance().methodReturns();
        return 0;
    }

    @Override
    public Resource mineResource() {
        CallStackViewer.getInstance().logCall("mineResource() called (HomeAsteroid)");

        Resource temp = core.popResource();

        CallStackViewer.getInstance().methodReturns();
        return temp;
    }

    @Override
    protected void explode() {
        //NOP
    }
}
