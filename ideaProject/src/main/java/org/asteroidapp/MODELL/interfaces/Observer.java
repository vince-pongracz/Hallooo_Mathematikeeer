package org.asteroidapp.MODELL.interfaces;

public interface Observer {
	/**
	 * This method will inform the observer
	 */
	public void notify(EventType eventType);
}