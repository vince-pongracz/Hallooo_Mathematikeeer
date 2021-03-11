package org.asteroidapp.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.entities.AIRobot;
import org.asteroidapp.resources.Resource;

/**
 * 
 */
public class Uran extends Resource {

	/**
	 * logger for Uran
	 */

	private static final Logger log = LogManager.getLogger(Uran.class.getSimpleName());

	/**
	 * Default constructor
	 */
	public Uran() {

	}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return "Uran";
	}

	/**
	 * @return
	 */
	public boolean isRadioActive() {
		return true;
	}

}