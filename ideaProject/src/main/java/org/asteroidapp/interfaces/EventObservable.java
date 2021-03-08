package org.asteroidapp.interfaces;

/**
 * Interface for Observables, which has events also
 */
public interface EventObservable extends Observable {

    /**
     * notifies checked entities about flair danger
     */
    public void notifyAboutDanger();

    /**
     * notifies checked entities about die event
     */
    public void notifyAboutDieEvent();

}