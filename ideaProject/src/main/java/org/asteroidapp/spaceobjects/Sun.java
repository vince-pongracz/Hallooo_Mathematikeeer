package org.asteroidapp.spaceobjects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.interfaces.EventObservable;
import org.asteroidapp.GameController;
import org.asteroidapp.entities.Entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Sun class
 */
public class Sun implements EventObservable {

    /**
     * Logger for Sun class
     */
    private static final Logger log = LogManager.getLogger(Sun.class.getSimpleName());

    /**
     * own set from entities, they will be notified
     */
    private Set<Entity> entities = null;

    /**
     * Default constructor
     */
    public Sun(Position position) {
        log.log(Level.INFO, "Sun constructor called");

        if (position != null) {
            this.position = position;
            this.entities = new HashSet<>();

            log.log(Level.TRACE, "Sun created at position: ({};{})", position.getX(), position.getY());
        } else {
            log.log(Level.FATAL, "Wrong position! (position is null)");
        }
    }

    /**
     * Store position if the sun
     */
    private Position position = null;

    /**
     * Getter on sun's position
     */
    public Position getPosition() {
        log.log(Level.INFO, "getPosition called");
        return position;
    }

    /**
     * Call sunFlair event
     */
    private void doSunFlair() {
        log.log(Level.INFO, "doSunFlair called");

        var playerIterator = GameController.getInstance().getIterOnPlayers();
        while (playerIterator.hasNext()) {

            var playerOn = playerIterator.next();
            var settlerIterator = playerOn.getIterOnMySettlers();

            while (settlerIterator.hasNext()) {

                var settlerItem = settlerIterator.next();
                settlerIterator.remove();
                settlerItem.notifyFlairEvent();
            }

            //if playerOn has empty collection
            //-> returns an emptyIterator, which has not next element
            //-> hasNext() returns 'false'
            if(!playerOn.getIterOnMySettlers().hasNext()){
                //remove playerIterator - won't throw ConcurrentModificationException
                playerIterator.remove();

                //if player has no settlers -> the player must die
                //so kill him/her
                playerOn.killPlayer();
            }
        }
    }

    /**
     * Notify about the danger of sunFlair
     */
    public void notifyAboutDanger() {
        log.log(Level.INFO, "notifyAboutDanger called");

        log.log(Level.TRACE, "Iterate on players, and his/her settlers");
        var playerIterator = GameController.getInstance().getIterOnPlayers();
        while (playerIterator.hasNext()) {
            var settlerIterator = playerIterator.next().getIterOnMySettlers();
            while (settlerIterator.hasNext()) {
                settlerIterator.next().notifyFlairDanger();
            }
        }
    }

    /**
     * Notify about the danger of sunFlair
     * also call sunFlair
     */
    public void notifyAboutDieEvent() {
        log.log(Level.INFO, "notifyAboutDieEvent called");
        doSunFlair();
    }

    @Override
    public void checkOut(Entity leavingEntity) {
        log.log(Level.INFO, "checkOut called");
        if (leavingEntity != null) {
            this.entities.remove(leavingEntity);
        } else {
            log.log(Level.FATAL, "leavingEntity is null");
        }
    }

    @Override
    public void checkIn(Entity newEntity) {
        log.log(Level.INFO, "checkIn called");
        if (newEntity != null) {
            this.entities.add(newEntity);
        } else {
            log.log(Level.FATAL, "newEntity is null");
        }
    }
}