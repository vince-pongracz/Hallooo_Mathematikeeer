package org.asteroidapp.MODELL.interfaces;

/**
 * Interface for Observables, which has events also
 */
public interface EventObservable extends Observable {

    /**
     * notifies checked entities about flair danger
     */
    void notifyAboutDanger();

    /**
     * notifies checked entities about die event
     */
    void notifyAboutDieEvent();

}