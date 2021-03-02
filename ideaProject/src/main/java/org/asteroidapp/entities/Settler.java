package org.asteroidapp.entities;

import org.asteroidapp.spaceobjects.Gate;
import org.asteroidapp.Player;
import org.asteroidapp.spaceobjects.SteppableSpaceObject;
import org.asteroidapp.resources.Resource;

import java.util.*;

/**
 * 
 */
public class Settler extends Entity {

	/**
	 * Default constructor
	 */
	public Settler(String name) {
		super(name);
	}

	@Override
	public void move() {

	}

	@Override
	public void drill() {

	}

	@Override
	protected void die() {

	}

	@Override
	protected SteppableSpaceObject chooseNeighbour(Set<SteppableSpaceObject> neighbours) {
		return null;
	}

	@Override
	public void notifyFlairEvent() {

	}

	@Override
	public void notifyFlairDanger() {

	}

	@Override
	public void notifyAsteroidExplosion() {

	}

	/**
	 * 
	 */
	private List<Resource> resources;

	/**
	 * 
	 */
	private Gate createdGates = null;

	/**
	 * 
	 */
	private Player owner;

	/**
	 * 
	 */
	public void createBot() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void mine() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void createGate() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void deployResource() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void buildGate() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void listResources() {
		// TODO implement here
	}

	/**
	 * @return
	 */
	public Resource chooseResource() {
		// TODO implement here
		return null;
	}

	/**
	 * @param resource
	 */
	private void addResource(Resource resource) {
		// TODO implement here
	}

}