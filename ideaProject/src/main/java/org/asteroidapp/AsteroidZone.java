package org.asteroidapp;

import java.util.*;

/**
 * 
 */
public class AsteroidZone {

	/**
	 * Default constructor
	 */
	public AsteroidZone() {
	}

	/**
	 * 
	 */
	public static double defOfCloseToSun;

	/**
	 * 
	 */
	private Set<SteppableSpaceObject> spaceObjects;

	/**
	 * 
	 */
	private Sun sun;

	/**
	 * 
	 */
	private Set<Gate> fullGates;

	/**
	 * 
	 */
	public void createZone() {
		// TODO implement here
	}

	/**
	 * @param spaceObj
	 */
	public void addSpaceObject(SteppableSpaceObject spaceObj) {
		// TODO implement here
	}

	/**
	 * @param onPosition
	 */
	public void removeSpaceObject(Position onPosition) {
		// TODO implement here
	}

	/**
	 * @return
	 */
	public Iterator<SteppableSpaceObject> getIterOnSpaceObjects() {
		// TODO implement here
		return spaceObjects.iterator();
	}

	/**
	 * @return
	 */
	public Position generateRandomPosition() {
		// TODO implement here
		return null;
	}

	/**
	 * @param toCheckPosition 
	 * @return
	 */
	private boolean checkDistanceAtCreate(Position toCheckPosition) {
		// TODO implement here
		return false;
	}

}