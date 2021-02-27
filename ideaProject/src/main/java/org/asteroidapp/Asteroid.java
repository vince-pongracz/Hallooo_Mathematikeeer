package org.asteroidapp;

import java.util.*;

/**
 * 
 */
public class Asteroid extends SteppableSpaceObject implements EventObservable {

	/**
	 * Default constructor
	 */
	public Asteroid() {
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public boolean drillLayer() {
		return false;
	}

	@Override
	public Resource mineResource() {
		return null;
	}

	@Override
	public boolean addResourceToCore(Resource resource) {
		return false;
	}

	@Override
	public void setMyPosition(Position newPosition) {

	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public boolean setPair(Gate pairGate) {
		return false;
	}

	@Override
	public SteppableSpaceObject getPair() {
		return null;
	}

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private boolean closeToSun;



	/**
	 * 
	 */
	public Set<Gate> gatesOnMe = null;

	/**
	 * 
	 */
	private Position position;

	/**
	 * 
	 */
	private void explode() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void notifyAboutDanger() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void notifyAboutDieEvent() {
		// TODO implement here
	}

}