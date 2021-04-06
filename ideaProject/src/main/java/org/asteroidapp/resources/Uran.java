package org.asteroidapp.resources;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AsteroidZone;
import org.asteroidapp.entities.AIRobot;
import org.asteroidapp.resources.Resource;
import org.asteroidapp.spaceobjects.Position;

/**
 *
 */
public class Uran extends Resource {

    /**
     * logger for Uran
     */

    private static final Logger log = LogManager.getLogger(Uran.class.getSimpleName());

    private int expositionCounter = 0;
    private static final int uranExpositionCoeff = 3;

    /**
     * Default constructor
     */
    public Uran() {
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return "Uran";
    }

    /**
     * @return
     */
    @Override
    public boolean isRadioActive(Position position) {
        log.log(Level.INFO, "Uran.isRadioActive called");

        boolean realRadioActive = false;

        if (position.distanceFrom(AsteroidZone.getInstance().getSun().getPosition()) < AsteroidZone.defOfCloseToSun) {

            ++expositionCounter;
            log.log(Level.TRACE, "Uran is close to Sun. --> increment expositionCounter, current value: {}",expositionCounter);

            if (expositionCounter % uranExpositionCoeff == 0) {
                log.log(Level.INFO, "(expositionCounter % uranExpositionCoeff == 0) is TRUE --> activated: isRadioActive will return: TRUE");
                realRadioActive = true;
                expositionCounter = 0;
            } else {
                //NOP
            }
        } else {
            //NOP
        }

        return realRadioActive;
    }
}