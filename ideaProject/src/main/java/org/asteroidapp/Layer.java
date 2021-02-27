package org.asteroidapp;

import java.util.*;

/**
 * 
 */
public class Layer {

	/**
	 * Default constructor
	 */
	public Layer() {
	}

	/**
	 * 
	 */
	private int thickness;


	/**
	 * @return
	 */
	public int thinIt() {
		if(thickness>0){
			thickness--;
		}
		return thickness;
	}

	/**
	 * @return
	 */
	public int getThickness() {
		return thickness;
	}

}