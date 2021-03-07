package org.asteroidapp.spaceobject.asteroid;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AppController;

import java.util.*;

/**
 * It depicts the cortex of an asteroid, knows how thick it is and can reduce it.
 */
public class Layer {

    private static Logger log = LogManager.getLogger(AppController.class.toString());

    /**
     * Default constructor
     */
    public Layer(int initThickness) {
        thickness = Math.abs(initThickness);
    }

    /**
     * The thickness of the asteroid's crust.
     */
    private int thickness;


    /**
     * It's getting thinner. Call DrillLayer.
     * @return thickness
     */
    public int thinIt() {
        if (thickness > 0) {
            thickness--;
        } else {
            log.log(Level.TRACE, "Thickness is already 0");
        }
        return thickness;
    }

    /**
     * The function returns with the thickness of the crust.
     * @return thickness
     */
    public int getThickness() {
        return thickness;
    }

}