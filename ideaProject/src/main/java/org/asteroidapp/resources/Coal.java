package org.asteroidapp.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.entities.AIRobot;

/**
 * 
 */
public class Coal extends Resource {

	/**
	 * logger for Coal
	 */

	public static final Coal coal = new Coal();

	private static final Logger log = LogManager.getLogger(Coal.class.getSimpleName());

	/**
	 * Default constructor
	 */
	public Coal() {
	}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return "Coal";
	}

	/**
	 * @return
	 */
	public boolean isRadioActive() {
		return false;
	}

}