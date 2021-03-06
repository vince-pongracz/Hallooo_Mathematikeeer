package org.asteroidapp.MODELL.spaceobjects.asteroid;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.util.CallStackViewer;

/**
 * It depicts the cortex of an asteroid, knows how thick it is and can reduce it.
 */
public class Layer {

    /**
     * Logger for Layer
     */
    private static Logger log = LogManager.getLogger(Layer.class.getSimpleName());

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
        CallStackViewer.getInstance().methodStartsLogCall("thinIt() called (Layer)");

        if (thickness > 0) {
            --thickness;
        } else {
            log.log(Level.TRACE, "Thickness is already 0");
        }

        CallStackViewer.getInstance().innerMethodCall("The layer thickness is after the drilling: " + thickness);
        CallStackViewer.getInstance().methodReturns();
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