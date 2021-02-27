package org.asteroidapp;

import java.util.*;
/**
 * 
 */
public abstract class Entity implements Observer {

	/**
	 * Default constructor
	 */
	public Entity() {
	}

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private Gate onAsteroid;

	/**
	 * 
	 */
	private SteppableSpaceObject onSpaceObject;

	/**
	 * 
	 */
	public abstract void move();

	/**
	 * 
	 */
	public abstract void drill();

	/**
	 * 
	 */
	protected abstract void die();

	/**
	 * @return
	 */
	public Set<SteppableSpaceObject> listMyNeighbours() {
		// TODO implement here
		return null;
	}

	/**
	 * @param newOnPlace
	 */
	private void setMySpaceObject(SteppableSpaceObject newOnPlace) {
		// TODO implement here
	}

	/**
	 * @return
	 */
	private SteppableSpaceObject getMySpaceObject() {
		// TODO implement here
		return null;
	}

	/**
	 * @param neighbours 
	 * @return
	 */
	protected abstract SteppableSpaceObject chooseNeighbour(Set<SteppableSpaceObject> neighbours);

	/**
	 * 
	 */
	public abstract void notifyFlairEvent();

	/**
	 * 
	 */
	public abstract void notifyFlairDanger();

	/**
	 * 
	 */
	public abstract void notifyAsteroidExplosion();

}