package org.asteroidapp.MODELL.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.MODELL.spaceobjects.Position;

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
            return this.getName().equals(((Resource) obj).getName());
        } else {
            return false;
        }
    }
}