package org.asteroidapp.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.entities.AIRobot;

/**
 * 
 */
public class FrozenWater extends Resource {

	/**
	 * logger for FrozenWater
	 */

	public static final FrozenWater frozenWater = new FrozenWater();

	private static final Logger log = LogManager.getLogger(FrozenWater.class.getSimpleName());

	/**
	 * Default constructor
	 */
	public FrozenWater() {
	}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return "FrozenWater";
	}

	/**
	 * @return
	 */
	public boolean isRadioActive() {
		return false;
	}

}