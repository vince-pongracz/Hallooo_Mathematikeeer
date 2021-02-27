package org.asteroidapp;

import java.util.*;

/**
 * 
 */
public abstract class SteppableSpaceObject implements Observable {

	/**
	 * Default constructor
	 */
	public SteppableSpaceObject() {
	}

	/**
	 * 
	 */
	private Set<Entity> playersOnMe;

	/**
	 * 
	 */
	private Position position;

	/**
	 * @return
	 */
	public Position getPosition() {
		// TODO implement here
		return null;
	}

	/**
	 * @return
	 */
	public abstract String getName();

	/**
	 * @return
	 */
	public abstract boolean drillLayer();

	/**
	 * @return
	 */
	public abstract Resource mineResource();

	/**
	 * @param resource 
	 * @return
	 */
	public abstract boolean addResourceToCore(Resource resource);

	/**
	 * @param newPosition
	 */
	public abstract void setMyPosition(Position newPosition);

	/**
	 * @return
	 */
	public abstract boolean isActive();

	/**
	 * @param pairGate 
	 * @return
	 */
	public abstract boolean setPair(Gate pairGate);

	/**
	 * @return
	 */
	public abstract SteppableSpaceObject getPair();

	/**
	 * @param leavingEntity
	 */
	public void checkOut(Entity leavingEntity) {
		// TODO implement here
	}

	/**
	 * @param newEntity
	 */
	public void checkIn(Entity newEntity) {
		// TODO implement here
	}

}