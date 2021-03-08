package org.asteroidapp.spaceobject.asteroid;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AppController;
import org.asteroidapp.resources.Resource;

import java.util.*;

//TODO stack -esre kéne megcsinálni

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
        resource = new ArrayList<>();
        resource.add(initResource);
    }

    public Core(int capacity, Resource initResource) {
        this.capacity = capacity;
        this.resource = new ArrayList<>();
        this.resource.add(initResource);
    }

    /**
     * Indicates how many raw materials can be in the core.
     */
    private int capacity = 1;

    /**
     *
     */
    private List<Resource> resource;


    /**
     * It returns with the raw material of the seed.
     *
     * @return returnList
     */
    public Resource getResource() {
        Resource returnRessource = resource.get(0);
        log.log(Level.TRACE, "getResource invited.  Return with the ressource: {}", returnRessource);
        return returnRessource;

    }

    /**
     * Sets the basic raw material for a specific resource.
     *
     * @param newResource
     */
    //TODO: esetek lefedése
    public void setResource(Resource newResource) {
        resource.add(newResource);
        log.log(Level.TRACE, "setResource invited. The ressource was set to: {}", newResource);
    }

}