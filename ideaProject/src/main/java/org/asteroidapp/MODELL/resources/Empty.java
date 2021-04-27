package org.asteroidapp.MODELL.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.MODELL.spaceobjects.Position;

/**
 * 
 */
public class Empty extends Resource {

	/**
	 * logger for Empty
	 */

	private static final Logger log = LogManager.getLogger(Empty.class.getSimpleName());
	/**
	 * Default constructor
	 */
	public Empty() {
	}

	/**
	 *
	 * @return
	 */
	//TODO discuss (what else can we do here?)
	@Override
	public String getName() {
		return "Empty";
	}

	/**
	 * @return
	 */
	@Override
	public boolean isRadioActive(Position position) {
		return false;
	}

}