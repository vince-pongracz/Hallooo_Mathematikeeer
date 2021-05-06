package org.asteroidapp.MODELL.spaceobjects.asteroid;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.MODELL.resources.*;
import org.asteroidapp.util.CallStackViewer;


/**
 * Its job is to store the raw materials inside the asteroid
 */
public class Core {

    /**
     * Logger for Core
     */
    private static Logger log = LogManager.getLogger(Core.class.getSimpleName());

    /**
     * Default constructor
     */
    public Core(Resource initResource) {
        resources.setAllCapacity(1);
        resources.pushResource(initResource);
    }

    /**
     * Create Core with not default capacity and an initial resource
     *
     * @param capacity
     * @param initResource
     */
    public Core(int capacity, Resource initResource) {
        resources.setAllCapacity(Math.abs(capacity));
        resources.pushResource(initResource);
    }

    /**
     * Resource storage of core
     */
    private ResourceStorage resources = new ResourceStorage();

    /**
     * It returns with the raw material of the seed.
     *
     * @return returnList
     */
    public Resource popResource() {
        log.log(Level.TRACE, "popResource called.");
        CallStackViewer.getInstance().methodStartsLogCall("popResource() called (Core)");
        CallStackViewer.getInstance().methodReturns();
        return resources.popRandomResource();
    }

    /**
     * Sets the basic raw material for a specific resource.
     *
     * @param newResource
     */
    //TODO: esetek lefedése
    public boolean pushResource(Resource newResource) {
        log.log(Level.TRACE, "pushResource called.");
        CallStackViewer.getInstance().methodStartsLogCall("pushResource() called (Core)");

        boolean success = resources.pushResource(newResource);
        CallStackViewer.getInstance().methodReturns();
        return success;
    }

    public String getCoreInfo() {
        var irons = this.resources.countOf(new Iron());
        var coals = this.resources.countOf(new Coal());
        var urans = this.resources.countOf(new Uran());
        var frozens = this.resources.countOf(new FrozenWater());

        StringBuilder coreInfoBuilder = new StringBuilder();

        if (irons > 0) {
            coreInfoBuilder.append("Iron: ").append(irons);
        }
        if (coals > 0) {
            coreInfoBuilder.append("\n").append("Coal: ").append(coals);
        }
        if (urans > 0) {
            coreInfoBuilder.append("\n").append("Uran: ").append(urans);
        }
        if (frozens > 0) {
            coreInfoBuilder.append("\n").append("FrozenW: ").append(frozens);
        }
        if (frozens == 0 && urans == 0 && coals == 0 && irons == 0) {
            coreInfoBuilder.append("\nEmpty core");
        }

        return coreInfoBuilder.toString();
    }

}