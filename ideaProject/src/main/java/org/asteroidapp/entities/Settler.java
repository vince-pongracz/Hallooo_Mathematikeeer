package org.asteroidapp.entities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AsteroidZone;
import org.asteroidapp.spaceobjects.Gate;
import org.asteroidapp.Player;
import org.asteroidapp.spaceobjects.SteppableSpaceObject;
import org.asteroidapp.resources.Resource;

import java.util.*;

/**
 * 
 */
public class Settler extends Entity {

	private static final Logger log = LogManager.getLogger(Settler.class.toString());

	/**
	 * Default constructor
	 */
	public Settler(String name, SteppableSpaceObject initPlace) {
		super(name, initPlace);
	}

	@Override
	public void drill() {

	}

	@Override
	public void die() {
		log.log(Level.INFO, "Die method of player {}'s settler called",this.owner.getName());
		AsteroidZone.getInstance().getSun().checkOut(this);
		onSpaceObject.checkOut(this);
		onSpaceObject = null;
		owner.removeSettler(this);
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
	public List<Resource> listResources() {
		// TODO implement here
		return null;
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