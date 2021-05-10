package org.asteroidapp.MODELL.interfaces;

import org.asteroidapp.MODELL.EventType;

public interface Observer {
	/**
	 * This method will inform the observer
	 */
	void notify(EventType eventType);
}