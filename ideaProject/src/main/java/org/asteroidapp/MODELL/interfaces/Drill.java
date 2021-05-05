package org.asteroidapp.MODELL.interfaces;

public interface Drill {
    /**
     * Abstract function for drill event. It will be implemented in AIRobot and Settler.
     * It will thicken the layer of an asteroid if possible
     */
    public boolean drill();
}
