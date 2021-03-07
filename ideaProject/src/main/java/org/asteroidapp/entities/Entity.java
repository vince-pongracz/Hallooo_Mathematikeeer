package org.asteroidapp.entities;

<<<<<<< HEAD
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.GameController;
import org.asteroidapp.Observer;
import org.asteroidapp.spaceobjects.Position;
=======
import org.asteroidapp.interfaces.Observer;
>>>>>>> f904270095fbea7cd17daa56034d920cb1f166cf
import org.asteroidapp.spaceobjects.SteppableSpaceObject;
import org.asteroidapp.AsteroidZone;

import java.util.*;

/**
 * It is an entity that can not be instantiated and
 * it has a name and an object where it currently stands on
 * The move anf listMyNeighbours functions are just implemented
 * the others are abstract and they will be implemented in Settler or AIRobot
 */
public abstract class Entity implements Observer {

    private static final Logger log = LogManager.getLogger(GameController.class.toString());

    /**
     * Default constructor for an entity
     */
    public Entity(String name, SteppableSpaceObject droppingPlace) {
        this.name = name;
        onSpaceObject = droppingPlace;
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
    protected SteppableSpaceObject onSpaceObject;

    /**
     * It defines how the move function works. Firstly the neighbour has to be chosen from a list
     * and then it will move to that specified object
     */
    public void move() {
        var neighbours = listMyNeighbours();
        var nextSpaceObject = chooseNeighbour(neighbours);
        onSpaceObject.checkOut(this);
        setMySpaceObject(nextSpaceObject);
        nextSpaceObject.checkIn(this);
        log.log(Level.TRACE, "Entity moved to {}", nextSpaceObject.getName() );
    }

    /**
     * Abstract function for drill event. It will be implemented in AIRobot and Settler.
     * It will thicken the layer of an asteroid if possible
     */
    public abstract void drill();

    /**
     * Abstract function for die event. It will be implemented in AIRobot and Settler.
     * It will kill and remove the specified objects.
     */
    public abstract void die();

    /**
     * It returns with a list of the possible neighbours where the entity can move
     *
     * @return list of the possible neighbours where the entity can move
     */
    public Set<SteppableSpaceObject> listMyNeighbours() {
        Set<SteppableSpaceObject> neighbours = new HashSet<>();
        Iterator<SteppableSpaceObject> iter = AsteroidZone.getInstance().getIterOnSpaceObjects();
        Position sun = AsteroidZone.getInstance().getSun().getPosition();
        SteppableSpaceObject temp;

        while (iter.hasNext()) {
            temp = iter.next();
            if (temp.getPosition().distanceFrom(onSpaceObject.getPosition()) > Position.getMaximalNeighbourDistance()) {
                neighbours.add(temp);
                log.log(Level.TRACE, "Possible neighbour: ", temp.getName() );
            }
        }
        return neighbours;
    }

    /**
     * It sets the currently place of an entity to another one
     *
     * @param newOnPlace the new place that will be set
     */
    protected void setMySpaceObject(SteppableSpaceObject newOnPlace) {
        if (newOnPlace != null) {
            onSpaceObject = newOnPlace;
            log.log(Level.TRACE, "Space object set to {} ", newOnPlace.getName() );
        } else {
            //NOP
        }
    }

    /**
     * It returns with the object where the entity stands on
     *
     * @return the object where the entity stands
     */
    public SteppableSpaceObject getMySpaceObject() {
        return onSpaceObject;
    }

    /**
     * Abstract function for chooseNeighbour. It will be implemented in AIRobot and Settler.
     *
     * @param neighbours the list of neighbours from where the player can choose where to move to
     * @return the chosen neighbour
     */
    protected abstract SteppableSpaceObject chooseNeighbour(Set<SteppableSpaceObject> neighbours);

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