package org.asteroidapp;

import java.util.*;

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