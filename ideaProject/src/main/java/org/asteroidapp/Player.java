package org.asteroidapp;

import java.util.*;

/**
 * 
 */
public class Player {

	/**
	 * Default constructor
	 */
	public Player() {
	}

	/**
	 * 
	 */
	private String name;


	/**
	 * 
	 */
	private Set<Settler> mySettlers;

	/**
	 * @return
	 */
	public Iterator<Settler> getIterOnMyEntities() {
		return mySettlers.iterator();
	}

	/**
	 * @param newEntity
	 */
	public void addEntity(Entity newEntity) {
		// TODO implement here
	}

	/**
	 * @param removedEntity
	 */
	public void removeEntity(Entity removedEntity) {
		// TODO implement here
	}

}