package org.asteroidapp;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.entities.Entity;

import java.util.*;

/**
 *
 */
public class Player {

    private static final Logger log = LogManager.getLogger(Player.class.toString());

    /**
     * Default constructor
     */
    //TODO throw an exception, when name is incorrect
    public Player(String name) {
        log.log(Level.TRACE, "Create Player");

        this.name = "";
        this.setName(name);

        log.log(Level.TRACE, "Player created with name: {}.", this.name);

        this.mySettlers = new ArrayList<Entity>();
        log.log(Level.TRACE, "List created for settlers");

    }

    /**
     *
     */
    private String name;

    private void setName(String name) {

        //TODO name check by Controller
        if (name != null && !name.equals("")) {
            this.name = name;
        }
        //TODO throw exception again
    }

    /**
     *
     */
    private List<Entity> mySettlers;

    /**
     * @return
     */
    public Iterator<Entity> getIterOnMyEntities() {
        log.log(Level.TRACE, "getIterOnMyEntities called");
        return mySettlers.iterator();
    }

    /**
     * @param newEntity
     */
    public void addEntity(Entity newEntity) {
        log.log(Level.TRACE, "addEntity called");
        if (!this.mySettlers.contains(newEntity)) {
            this.mySettlers.add(newEntity);
            log.log(Level.TRACE, "newEntity added to player {}.", name);
        } else {
            //NOP
            log.log(Level.TRACE, "Player {} already has newEntity", name);
        }
    }

    /**
     * @param removedEntity
     */
    //TODO refactor to boolean, check
    public void removeEntity(Entity removedEntity) {
        mySettlers.remove(removedEntity);
    }
}