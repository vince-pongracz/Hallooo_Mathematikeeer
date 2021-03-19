package org.asteroidapp;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.entities.Settler;
import org.asteroidapp.util.CallStackViewer;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 */
public class Player {

    /**
     * Logger for Player
     */
    private static final Logger log = LogManager.getLogger(Player.class.getSimpleName());

    /**
     * Default constructor
     */
    //TODO throw an exception, when name is incorrect
    public Player(String name) {
        log.log(Level.INFO, "Player constructor called");

        this.name = "";
        this.setName(name);

        log.log(Level.TRACE, "Player created with name: {}", this.name);

        this.mySettlers = new CopyOnWriteArrayList<Settler>();
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
     * Getter on name attribute
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    private List<Settler> mySettlers = null;

    /**
     * @return
     */
    public Iterator<Settler> getIterOnMySettlers() {
        log.log(Level.TRACE, "getIterOnMySettlers called");
        if (mySettlers == null || mySettlers.size() == 0) {
            return Collections.emptyIterator();
        } else {
            return mySettlers.iterator();
        }
    }

    /**
     * @param newSettler
     */
    public void addSettler(Settler newSettler) {
        log.log(Level.TRACE, "addSettler called");
        if (!this.mySettlers.contains(newSettler)) {
            this.mySettlers.add(newSettler);
            log.log(Level.TRACE, "newSettler added to player {}.", name);
        } else {
            //NOP
            log.log(Level.TRACE, "Player {} already has newSettler", name);
        }
    }

    /**
     * @param removedEntity
     */
    //TODO refactor to boolean, check
    public void removeSettler(Settler removedEntity) {
        mySettlers.remove(removedEntity);
    }

    public void killPlayer() {
        log.log(Level.INFO, "killPlayer called");
        CallStackViewer.getInstance().methodStartsLogCall("killPlayer() called");

        GameController.getInstance().leaveGame(this);
        for (var settler : mySettlers) {
            settler.die();
        }

        CallStackViewer.getInstance().methodReturns();
    }
}