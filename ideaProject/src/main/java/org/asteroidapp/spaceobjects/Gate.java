package org.asteroidapp.spaceobjects;

import org.asteroidapp.entities.Entity;
import org.asteroidapp.resources.Resource;

import java.util.*;

/**
 * 
 */
public class Gate extends SteppableSpaceObject {

	/**
	 * Default constructor
	 */
	public Gate(Position position) {
		super(position);
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
	private Set<Entity> playersOnMe;

	/**
	 * 
	 */
	private Gate gatePair;

}