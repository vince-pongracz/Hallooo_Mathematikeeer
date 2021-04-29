package org.asteroidapp.MODELL.spaceobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.MODELL.resources.Empty;
import org.asteroidapp.MODELL.resources.Resource;
import org.asteroidapp.MODELL.spaceobjects.asteroid.Core;
import org.asteroidapp.MODELL.spaceobjects.asteroid.Layer;
import org.asteroidapp.util.CallStackViewer;

import java.util.ArrayList;
import java.util.List;

public class HomeAsteroid extends Asteroid {

    /**
     * logger for HomeAsteroid
     */
    private static final Logger log = LogManager.getLogger(HomeAsteroid.class.getSimpleName());

    public static int homeCapacity = 5;

    /**
     * Default constructor
     *
     * @param position
     * @param initResource
     */
    public HomeAsteroid(Position position, Resource initResource) {
        super("Home", position, initResource, 0);

        CallStackViewer.getInstance().methodStartsLogCall("HomeAsteroid constructor called");

        //to override Asteroid core, layer constructions
        core = null;
        core = new Core(homeCapacity, new Empty());
        layer = null;
        layer = new Layer(0);

        CallStackViewer.getInstance().methodReturns();
    }

    @Override
    public boolean drillLayer() {
        CallStackViewer.getInstance().methodStartsLogCall("drillLayer() called (HomeAsteroid)");
        CallStackViewer.getInstance().methodReturns();
        return false;
    }

    @Override
    public Resource mineResource() {
        CallStackViewer.getInstance().methodStartsLogCall("mineResource() called (HomeAsteroid)");

        Resource temp = core.popResource();

        CallStackViewer.getInstance().methodReturns();
        return temp;
    }

    @Override
    protected void explode() {
        //NOP
    }

    public List<Resource> getResourcesWithoutTakeThem() {
        List<Resource> tmpRes = new ArrayList<>();
        for (int i = 0; i < homeCapacity; i++) {
            var tmp = core.popResource();
            core.pushResource(tmp);
            if (!tmp.equals(new Empty())){
                tmpRes.add(tmp);
            }
        }
        return tmpRes;
    }
}
