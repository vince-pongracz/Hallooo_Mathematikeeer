package org.asteroidapp.spaceobject.asteroid;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AppController;
import org.asteroidapp.resources.Resource;
import org.asteroidapp.resources.ResourceStorage;
import org.asteroidapp.util.CallStackViewer;

import java.lang.invoke.CallSite;
import java.util.*;


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

        resource = new ResourceStorage();
        resource.setAllCapacity(1);
        resource.pushResource(initResource);
    }

    /**
     * Create Core with not default capacity and an initial resource
     *
     * @param capacity
     * @param initResource
     */
    public Core(int capacity, Resource initResource) {
        resource = new ResourceStorage();
        resource.setAllCapacity(Math.abs(capacity));
        resource.pushResource(initResource);
    }

    /**
     * Resource storage of core
     */
    ResourceStorage resource = null;

    /**
     * It returns with the raw material of the seed.
     *
     * @return returnList
     */
    public Resource popResource() {
        log.log(Level.TRACE, "popResource called.");
        CallStackViewer.getInstance().logCall("popResource() called (Core)");
        CallStackViewer.getInstance().methodReturns();
        return resource.popRandomResource();
    }

    /**
     * Sets the basic raw material for a specific resource.
     *
     * @param newResource
     */
    //TODO: esetek lefedése
    public void pushResource(Resource newResource) {
        CallStackViewer.getInstance().logCall("pushResource() called (Core)");

        resource.pushResource(newResource);
        log.log(Level.TRACE, "pushResource called. The ressource was set to: {}", newResource.getName());

        CallStackViewer.getInstance().methodReturns();
    }

}