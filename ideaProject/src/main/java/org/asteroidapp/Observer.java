package org.asteroidapp;

import java.util.*;

/**
 * 
 */
public interface Observer {

	/**
	 * 
	 */
	public void notifyFlairEvent();

	/**
	 * 
	 */
	public void notifyFlairDanger();

	/**
	 * 
	 */
	public void notifyAsteroidExplosion();

}