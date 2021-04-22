package org.asteroidapp.interfaces;

/**
 * Observer interface to realize observer pattern
 */
public interface Observer {

	/**
	 * This method will inform the player/entity, that a flair event occours
	 */
	public void notifyFlairEvent();

	/**
	 * This method will inform the player/entity, that a flair event will come in the near future
	 */
	public void notifyFlairDanger();

	/**
	 * This method will inform the player/entity, that his/her asteroid exploded, also she/he died
	 */
	public void notifyAsteroidExplosion();
}