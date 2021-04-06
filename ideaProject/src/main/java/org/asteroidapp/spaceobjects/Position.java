package org.asteroidapp.spaceobjects;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


/**
 *
 */
public class Position {

    /**
     * Logger for Position
     */
    private static final Logger log = LogManager.getLogger(Position.class.getSimpleName());

    /**
     * Default constructor
     */
    public Position() {
        x = 0;
        y = 0;
        ownRadius = 0;
        //log.log(Level.TRACE, "Empty position created (x:{} y:{} r:{})", x, y, ownRadius);
    }

    /**
     * Constructor
     *
     * @param xCoordinate x coordinate of the position
     * @param yCoordinate y coordinate of the position
     */
    public Position(double xCoordinate, double yCoordinate) {
        x = xCoordinate;
        y = yCoordinate;
        ownRadius = 0;
        //log.log(Level.TRACE, "Position created (x:{} y:{} r:{})", x, y, ownRadius);
    }

    /**
     * Constructor with radius param
     *
     * @param xCoordinate x coordinate of the position
     * @param yCoordinate y coordinate of the position
     * @param ownRadius   radius of the shape/spaceobject, which is on this position
     */
    public Position(double xCoordinate, double yCoordinate, double ownRadius) {
        this.x = xCoordinate;
        this.y = yCoordinate;
        this.ownRadius = Math.abs(ownRadius);
        //log.log(Level.TRACE, "Position created (x:{} y:{} r:{})", x, y, ownRadius);
    }

    /**
     * Store x coordinate
     */
    private double x;

    /**
     * Store y coordinate
     */
    private double y;

    /**
     * Radius of the position
     */
    private double ownRadius;

    /**
     * Setter for radius
     *
     * @param newRadius new radius to set
     */
    public void setRadius(double newRadius) {
        ownRadius = Math.abs(newRadius);
        //log.log(Level.TRACE, "setNewRadius called, new radius is: {}", ownRadius);
    }


    /**
     * Getter on x coordinate
     *
     * @return x coordinate
     */
    public double getX() {
        //log.log(Level.TRACE, "getX called, x coordinate: {}", x);
        return x;
    }

    /**
     * Getter on y coordinate
     *
     * @return y coordinate
     */
    public double getY() {
        //log.log(Level.TRACE, "getY called, y coordinate: {}", y);
        return y;
    }

    /**
     * Getter on radius
     *
     * @return ownRadius radius of an object with position
     */
    public double getRadius() {
        //log.log(Level.TRACE, "getRadius called, radius: {}", ownRadius);
        return ownRadius;
    }

    /**
     * Calculate distance between two positions (this and pos)
     *
     * @param pos
     * @return double distance
     */
    public double distanceFrom(Position pos) {
        //log.log(Level.TRACE, "distanceFrom function called");

        double xQuad = Math.pow(Math.abs(this.x - pos.getX()), 2);
        //log.log(Level.TRACE, "Difference square of x coordinates ({})", xQuad);
        double yQuad = Math.pow(Math.abs(this.y - pos.getY()), 2);
        //log.log(Level.TRACE, "Difference square of y coordinates ({})", yQuad);
        double distance = Math.sqrt(xQuad + yQuad);
        //log.log(Level.TRACE, "Distance between points: ({})", distance);
        return distance;
    }

    //TODO a gui elemek négyzetesek, vagy kerekek? mert ez am már ilyen gui segéd method

    /**
     * GUI helper method
     *
     * @return postion Position of the left upper corner
     */
    public Position getLeftUpperCornerCoordinates() {
        //log.log(Level.TRACE, "getLeftUpperCornerCoordinates function called");
        double xLeftUpper = this.x - ownRadius;
        double yLeftUpper = this.y + ownRadius;
        return new Position(xLeftUpper, yLeftUpper, 0);
    }


    private static double minNeighbourDistance = 0;

    public static double getMinimalNeighbourDistance() {
        //log.log(Level.TRACE, "getMinimalNeighbourDistance function called ({}.)", minNeighbourDistance);
        return minNeighbourDistance;
    }

    public static void setMinimalNeighbourDistance(double minDistance) {
        //log.log(Level.TRACE, "setMinimalNeighbourDistance called");
        //log.log(Level.TRACE, "new value of minNeighbourDistance: ({}.)", minNeighbourDistance);
        minNeighbourDistance = Math.abs(minDistance);
    }


    /**
     * maximal range of view, when looking for neighbours
     */
    private static double maxNeighbourDistance = 500;

    /**
     * Getter on maxNeighbourDistance
     *
     * @return maxNeighbourDistance
     */
    public static double getMaximalNeighbourDistance() {
        //log.log(Level.TRACE, "getMaximalNeighbourDistance function called ({})", maxNeighbourDistance);
        return maxNeighbourDistance;
    }

    /**
     * Setter on maxNeighbourDistance
     *
     * @param maxDistance
     */
    public static void setMaximalNeighbourDistance(double maxDistance) {
        //log.log(Level.TRACE, "setMaximalNeighbourDistance function called");
        //log.log(Level.TRACE, "new value of maxNeighbourDistance: ({})", maxNeighbourDistance);
        maxNeighbourDistance = Math.abs(maxDistance);
    }

    @Override
    public boolean equals(Object o) {
        //log.log(Level.TRACE, "equals method called (between 2 position)");

        if (this == o) {
            //log.log(Level.TRACE, "object was this (references are equal -> data equal)");
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            //log.log(Level.TRACE, "classes aren't matching, or one of the objects is null");
            return false;
        }

        Position position = (Position) o;
        boolean returnValue = Double.compare(position.x, x) == 0 &&
                Double.compare(position.y, y) == 0 &&
                Double.compare(position.ownRadius, ownRadius) == 0;

        //log.log(Level.TRACE, "result of equals: {}", String.valueOf(returnValue));

        return returnValue;
    }

    @Override
    @Deprecated
    public int hashCode() {
        return Objects.hash(x, y, ownRadius);
    }
}