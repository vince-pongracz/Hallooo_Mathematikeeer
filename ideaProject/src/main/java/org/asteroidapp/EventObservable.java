package org.asteroidapp;

import java.util.*;

/**
 * 
 */
public interface EventObservable extends Observable {

	/**
	 * 
	 */
	public void notifyAboutDanger();

	/**
	 * 
	 */
	public void notifyAboutDieEvent();

}