package org.asteroidapp.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.entities.AIRobot;
import org.asteroidapp.spaceobjects.Position;

/**
 * 
 */
public class Iron extends Resource {

	/**
	 * logger for Iron
	 */

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
	@Override
	public String getName() {
		return "Iron";
	}

	/**
	 * @return
	 */
	@Override
	public boolean isRadioActive(Position position) {
		return false;
	}

}