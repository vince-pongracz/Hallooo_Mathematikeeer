package org.asteroidapp;

import org.asteroidapp.entities.Entity;

/**
 * 
 */
public interface Observable {

	/**
	 * @param leavingEntity
	 */
	public void checkOut(Entity leavingEntity);

	/**
	 * @param newEntity
	 */
	public void checkIn(Entity newEntity);

}