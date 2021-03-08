package org.asteroidapp.resources;

import java.util.*;

/**
 * 
 */
public abstract class Resource implements Comparable<Resource>{

	/**
	 * 
	 */
	public abstract String getName();

	/**
	 * @return
	 */
	public abstract boolean isRadioActive();

	@Override
	public int compareTo(Resource other){
		return getName().compareTo(other.getName());
	}

}