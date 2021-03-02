package org.asteroidapp.resources;

/**
 * 
 */
public class FrozenWater implements Resource {

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