package org.asteroidapp.MODELL.interfaces;

/**
 * Interface for observer pattern (observers check in, and out form objects)
 */
public interface Observable {

	/**
	 * Checks out from object's collection, which will be notified when an event occoured
	 * @param leavingEntity entity, who checks out
	 */
	public void checkOut(MoveableObserver leavingThing);

	/**
	 * Checks in on object's collection
	 * @param newEntity entity, who checks on, and will be notified about events
	 */
	public void checkIn(MoveableObserver newThing);
}