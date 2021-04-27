package org.asteroidapp.drawables;

import org.asteroidapp.MODELL.entities.AIRobot;
import org.asteroidapp.MODELL.spaceobjects.Position;

public class AIRobotGraphic extends Drawable {
    private final AIRobot aiRobot;

    public AIRobotGraphic(AIRobot robotObj) {
        prior = 2;
        aiRobot = robotObj;
    }

    @Override
    protected Position getPosition() {
        return aiRobot.getMySpaceObject().getPosition();
    }

    @Override
    protected String getImagePath() {
        return MapView.robotPng;
    }

    @Override
    public String getName() {
        return aiRobot.getName();
    }
}
