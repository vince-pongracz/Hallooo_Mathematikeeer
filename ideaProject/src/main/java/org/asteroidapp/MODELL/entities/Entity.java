package org.asteroidapp.MODELL.entities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.CONTROLLER.GameController;
import org.asteroidapp.MODELL.interfaces.MoveableObserver;
import org.asteroidapp.MODELL.spaceobjects.Asteroid;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.MODELL.spaceobjects.SteppableSpaceObject;
import org.asteroidapp.CONTROLLER.AsteroidZone;
import org.asteroidapp.util.CallStackViewer;

import java.util.*;

/**
 * It is an entity that can not be instantiated and
 * it has a name and an object where it currently stands on
 * The move anf listMyNeighbours functions are just implemented
 * the others are abstract and they will be implemented in Settler or AIRobot
 */
public abstract class Entity implements MoveableObserver {

    /**
     * Logger for Entity
     */
    private static final Logger log = LogManager.getLogger(Entity.class.getSimpleName());

    /**
     * Default constructor for an entity
     */
    public Entity(String name, SteppableSpaceObject droppingPlace) {
        this.name = name;
        onAsteroid = droppingPlace.getTarget();
        onAsteroid.checkIn(this);
    }

    /**
     * The name of the given entity
     */
    private String name;

    /**
     * Getter function for the name of the entity
     */
    public String getName() {
        return name;
    }

    /**
     * It stores the object where the entity currently stands on
     */
    protected Asteroid onAsteroid;

    /**
     * It defines how the move function works. Firstly the neighbour has to be chosen from a list
     * and then it will move to that specified object
     */
    public void move(SteppableSpaceObject nextSpaceObject) {
        log.log(Level.INFO, "move called");
        CallStackViewer.getInstance().methodStartsLogCall("move() called (Entity)");

        var neighbours = listMyNeighbours();

        if (nextSpaceObject != null && neighbours.contains(nextSpaceObject)) {
            onAsteroid.checkOut(this);
            onAsteroid = nextSpaceObject.getTarget();
            nextSpaceObject.checkIn(this);
            log.log(Level.TRACE, "Entity moved to {}", nextSpaceObject.getName());

            GameController.response.addRefreshObjects(this.getName());
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Abstract function for die event. It will be implemented in AIRobot and Settler.
     * It will kill and remove the specified objects.
     */
    protected abstract void die();

    /**
     * It returns with a list of the possible neighbours where the entity can move
     *
     * @return list of the possible neighbours where the entity can move
     */
    public Set<SteppableSpaceObject> listMyNeighbours() {
        log.log(Level.INFO, "listMyNeighbours called");
        CallStackViewer.getInstance().methodStartsLogCall("listMyNeighbours() called (Entity)");

        Set<SteppableSpaceObject> neighbours = new HashSet<>();

        var iter = AsteroidZone.getInstance().getIterOnSpaceObjects();
        //Position sun = AsteroidZone.getInstance().getSun().getPosition();
        SteppableSpaceObject temp = null;

        while (iter.hasNext()) {
            temp = iter.next();
            if(temp.isActive() && temp.getPosition() != this.onAsteroid.getPosition()){
                double distance = temp.getPosition().distanceFrom(onAsteroid.getPosition());
                if (distance < Position.getMaximalNeighbourDistance() && !temp.getName().equals(onAsteroid.getName())) {
                    neighbours.add(temp);
                    log.log(Level.INFO, "Possible neighbour: {}", temp.getInfo());
                }
            }
        }

        CallStackViewer.getInstance().methodReturns();
        return neighbours;
    }


    /**
     * It returns with the object where the entity stands on
     *
     * @return the object where the entity stands
     */
    public SteppableSpaceObject getMySpaceObject() {
        return onAsteroid;
    }

    /**
     * Abstract function for chooseNeighbour. It will be implemented in AIRobot and Settler.
     *
     * @param neighbours the list of neighbours from where the player can choose where to move to
     * @return the chosen neighbour
     */
    public abstract SteppableSpaceObject chooseNeighbour(Set<SteppableSpaceObject> neighbours);

    /**
     * Abstract function for notifyFlairEvent. It will be implemented in AIRobot and Settler.
     * It notifies the entity about a flair event is happening
     */
    public abstract void notifyFlairEvent();

    /**
     * Abstract function for notifyFlairDanger. It will be implemented in AIRobot and Settler.
     * It notifies the entity about a coming flair event
     */
    public abstract void notifyFlairDanger();

    /**
     * Abstract function for notifyAsteroidExplosion. It will be implemented in AIRobot and Settler.
     * notifies the entity about an asteroid explosion
     */
    public abstract void notifyAsteroidExplosion();
}