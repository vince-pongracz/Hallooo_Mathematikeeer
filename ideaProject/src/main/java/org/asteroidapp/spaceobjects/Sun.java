package org.asteroidapp.spaceobjects;

import org.asteroidapp.interfaces.EventObservable;
import org.asteroidapp.GameController;
import org.asteroidapp.entities.Entity;

import java.util.Set;

/**
 *
 */
public class Sun implements EventObservable {

    private Set<Entity> entities;

    /**
     * Default constructor
     */
    public Sun(Position position) {
        this.position = position;
    }

    /**
     *
     */
    private Position position;

    /**
     *
     */
    public Position getPosition() {
        return position;
    }

    /**
     *
     */
    public void doSunFlair() {
        notifyAboutDieEvent();
    }

    /**
     *
     */
    public void notifyAboutDanger() {

        var playerIterator = GameController.getInstance().getIterOnPlayers();
        while (playerIterator.hasNext()) {
            var settlerIterator = playerIterator.next().getIterOnMySettlers();
            while (settlerIterator.hasNext()) {
                settlerIterator.next().notifyFlairDanger();
            }
        }
    }

    /**
     *
     */
    public void notifyAboutDieEvent() {
        var playerIterator = GameController.getInstance().getIterOnPlayers();
        while (playerIterator.hasNext()) {
            var settlerIterator = playerIterator.next().getIterOnMySettlers();
            while (settlerIterator.hasNext()) {
                settlerIterator.next().notifyFlairEvent();
            }
        }
    }

    @Override
    public void checkOut(Entity leavingEntity) {

    }

    @Override
    public void checkIn(Entity newEntity) {

    }
}