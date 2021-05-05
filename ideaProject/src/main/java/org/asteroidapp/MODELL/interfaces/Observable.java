package org.asteroidapp.MODELL.interfaces;

import org.asteroidapp.MODELL.EventType;

/**
 * Interface for observer pattern (observers check in, and out form objects)
 */
public interface Observable {

	/**
	 * Checks out from object's collection, which will be notified when an event occoured
	 * @param leavingObserver entity, who checks out
	 */
	void checkOut(Observer leavingObserver);

	/**
	 * Checks in on object's collection
	 * @param newObserver entity, who checks on, and will be notified about events
	 */
	void checkIn(Observer newObserver);

	void signalizeUpdate(EventType event);
}