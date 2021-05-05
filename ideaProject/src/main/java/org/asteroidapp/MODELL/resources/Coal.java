package org.asteroidapp.MODELL.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.MODELL.spaceobjects.Position;

/**
 * 
 */
public class Coal extends Resource {

	/**
	 * logger for Coal
	 */

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
	@Override
	public String getName() {
		return "Coal";
	}

	/**
	 * @return
	 */
	@Override
	public boolean isRadioActive(Position position) {
		return false;
	}

}