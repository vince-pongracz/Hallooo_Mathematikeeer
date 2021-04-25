package org.asteroidapp.interfaces;

import org.asteroidapp.spaceobjects.SteppableSpaceObject;

public interface Moveable {
    public void move(SteppableSpaceObject nextSpaceObject);
}
