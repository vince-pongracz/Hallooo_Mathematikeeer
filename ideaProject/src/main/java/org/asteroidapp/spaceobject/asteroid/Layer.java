package org.asteroidapp.spaceobject.asteroid;

import java.util.*;

/**
 *
 */
public class Layer {

    /**
     * Default constructor
     */
    public Layer(int initThickness) {
        thickness = Math.abs(initThickness);
    }

    /**
     *
     */
    private int thickness;


    /**
     * @return
     */
    public int thinIt() {
        if (thickness > 0) {
            thickness--;
        }else{
        	//TODO write log here
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