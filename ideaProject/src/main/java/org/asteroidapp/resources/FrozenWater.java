package org.asteroidapp.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.spaceobjects.Position;

/**
 * 
 */
public class FrozenWater extends Resource {

	/**
	 * logger for FrozenWater
	 */

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
	@Override
	public String getName() {
		return "FrozenWater";
	}

	/**
	 * @return
	 */
	@Override
	public boolean isRadioActive(Position position) {
		return false;
	}

}