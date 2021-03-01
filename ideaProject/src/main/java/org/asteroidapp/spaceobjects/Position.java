package org.asteroidapp.spaceobjects;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


/**
 *
 */
public class Position {

    private static final Logger log = LogManager.getLogger(Position.class.toString());

    /**
     * Default constructor
     */
    public Position() {
        x = 0;
        y = 0;
        ownRadius = 0;
        log.log(Level.TRACE, "Empty position created (x:{} y:{} r:{})", x, y, ownRadius);
    }

    public Position(double xCoordinate, double yCoordinate) {
        x = xCoordinate;
        y = yCoordinate;
        ownRadius = 0;
        log.log(Level.TRACE, "Position created (x:{} y:{} r:{})", x, y, ownRadius);
    }

    public Position(double xCoordinate, double yCoordinate, double ownRadius) {
        this.x = xCoordinate;
        this.y = yCoordinate;
        this.ownRadius = Math.abs(ownRadius);
        log.log(Level.TRACE, "Position created (x:{} y:{} r:{})", x, y, ownRadius);
    }

    /**
     *
     */
    private double x;

    /**
     *
     */
    private double y;

    /**
     *
     */
    private double ownRadius;

    public void setRadius(double newRadius) {
        ownRadius = Math.abs(newRadius);
    }


    /**
     * @return x coordinate
     */
    public double getX() {
        log.log(Level.TRACE, "Get x coordinate ({}.)", x);
        return x;
    }

    /**
     * @return y coordinate
     */
    public double getY() {
        log.log(Level.TRACE, "Get y coordinate ({}.)", y);
        return y;
    }

    /**
     * @return ownRadius radius of an object with position
     */
    public double getRadius() {
        log.log(Level.TRACE, "Get radius ({}.)", ownRadius);
        return ownRadius;
    }

    /**
     * @param pos
     * @return
     */
    public double distanceFrom(Position pos) {
        log.log(Level.TRACE, "distanceFrom function called");

        double xQuad = Math.pow(Math.abs(this.x - pos.getX()), 2);
        log.log(Level.TRACE, "Difference square of x coordinates ({}.)", xQuad);
        double yQuad = Math.pow(Math.abs(this.y - pos.getY()), 2);
        log.log(Level.TRACE, "Difference square of y coordinates ({}.)", yQuad);
        double distance = Math.sqrt(xQuad + yQuad);
        log.log(Level.TRACE, "Distance between points ({}.)", distance);
        return distance;
    }

    /**
     * @return postion Position of the left upper corner
     */
    public Position getLeftUpperCornerCoordinates() {
        log.log(Level.TRACE, "getLeftUpperCornerCoordinates function called");
        double xLeftUpper = this.x - ownRadius;
        double yLeftUpper = this.y + ownRadius;
        log.log(Level.TRACE, "calculated coordinates: ({}.;{}.)", xLeftUpper, yLeftUpper);
        return new Position(xLeftUpper, yLeftUpper, 0);
    }


    private static double minNeighbourDistance = 0;

    public static double getMinimalNeighbourDistance() {
        log.log(Level.TRACE, "getMinimalNeighbourDistance function called ({}.)", minNeighbourDistance);
        return minNeighbourDistance;
    }

    public static void setMinimalNeighbourDistance(double minDistance) {
        log.log(Level.TRACE, "setMinimalNeighbourDistance called");
        log.log(Level.TRACE, "new value of minNeighbourDistance: ({}.)", minNeighbourDistance);
        minNeighbourDistance = Math.abs(minDistance);
    }


    private static double maxNeighbourDistance = 0;

    public static double getMaximalNeighbourDistance() {
        log.log(Level.TRACE, "getMaximalNeighbourDistance function called ({}.)", maxNeighbourDistance);
        return maxNeighbourDistance;
    }

    public static void setMaximalNeighbourDistance(double maxDistance) {
        log.log(Level.TRACE, "setMaximalNeighbourDistance function called");
        log.log(Level.TRACE, "new value of maxNeighbourDistance: ({}.)", maxNeighbourDistance);
        maxNeighbourDistance = Math.abs(maxDistance);
    }

    @Override
    public boolean equals(Object o) {
        log.log(Level.TRACE, "equals method called");

        if (this == o) {
            log.log(Level.TRACE, "object was this (references are equal -> data equal)");
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            log.log(Level.TRACE, "classes aren't matching, or one of the objects is null");
            return false;
        }

        Position position = (Position) o;
        boolean returnValue = Double.compare(position.x, x) == 0 &&
                Double.compare(position.y, y) == 0 &&
                Double.compare(position.ownRadius, ownRadius) == 0;

        log.log(Level.TRACE, "result: {}.", String.valueOf(returnValue));

        return returnValue;
    }

    @Override
    @Deprecated
    public int hashCode() {
        return Objects.hash(x, y, ownRadius);
    }
}