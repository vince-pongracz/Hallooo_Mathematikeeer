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
        resource = new Stack<Resource>();
        resource.push(initResource);
    }

    public Core(int capacity, Resource initResource) {
        this.capacity = capacity;
        this.resource = new Stack<Resource>();
        this.resource.push(initResource);
    }

    /**
     * Indicates how many raw materials can be in the core.
     */
    private int capacity = 1;

    /**
     *
     */
    //private List<Resource> resource;
    Stack<Resource> resource;

    /**
     * It returns with the raw material of the seed.
     *
     * @return returnList
     */
    public Resource popResource() {
       // Resource returnRessource = resource.get(0);
        log.log(Level.TRACE, "popResource called.  Return with the ressource: {}", resource.get(resource.size()).getName());
        return resource.pop();

    }

    /**
     * Sets the basic raw material for a specific resource.
     *
     * @param newResource
     */
    //TODO: esetek lefedése
    public void pushResource(Resource newResource) {
        //resource.add(newResource);
        resource.push(newResource);
        log.log(Level.TRACE, "pushResource called. The ressource was set to: {}", newResource.getName());
    }

}