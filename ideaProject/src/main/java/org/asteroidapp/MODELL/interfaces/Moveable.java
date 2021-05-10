package org.asteroidapp.MODELL.interfaces;

import org.asteroidapp.MODELL.spaceobjects.SteppableSpaceObject;

public interface Moveable {
    void move(SteppableSpaceObject nextSpaceObject);
}
