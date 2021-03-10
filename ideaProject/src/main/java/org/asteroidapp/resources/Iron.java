package org.asteroidapp.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.entities.AIRobot;

/**
 * 
 */
public class Iron extends Resource {

	/**
	 * logger for Iron
	 */

	public static final Iron iron = new Iron();

	private static final Logger log = LogManager.getLogger(Iron.class.getSimpleName());
	/**
	 * Default constructor
	 */
	public Iron() {
	}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return "Iron";
	}

	/**
	 * @return
	 */
	public boolean isRadioActive() {
		return false;
	}

}