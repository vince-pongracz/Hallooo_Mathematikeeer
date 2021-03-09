package org.asteroidapp.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.entities.AIRobot;

/**
 * 
 */
public class Empty extends Resource {

	/**
	 * logger for Empty
	 */

	public static final Empty empty = new Empty();

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
	public String getName() {
		return "Empty";
	}

	/**
	 * @return
	 */
	public boolean isRadioActive() {
		return false;
	}

}