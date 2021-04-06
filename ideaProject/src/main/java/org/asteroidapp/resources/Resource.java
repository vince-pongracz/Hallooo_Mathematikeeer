package org.asteroidapp.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.entities.AIRobot;
import org.asteroidapp.spaceobjects.Position;

import java.util.*;

/**
 *
 */
public abstract class Resource implements Comparable<Resource> {

    /**
     * logger for Resource
     */
    private static final Logger log = LogManager.getLogger(Resource.class.getSimpleName());

    /**
     *
     */
    public abstract String getName();

    /**
     * @return
     */
    public abstract boolean isRadioActive(Position place);

    @Override
    public int compareTo(Resource other) {
        return getName().compareTo(other.getName());
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof Resource) {
            //if the names are equals
            if (this.getName().equals(((Resource) obj).getName())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}