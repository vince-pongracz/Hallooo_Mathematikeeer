package org.asteroidapp.VIEW.drawables;

import org.asteroidapp.MODELL.entities.AIRobot;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

import java.io.FileNotFoundException;

public class AIRobotGraphic extends Drawable {
    private final AIRobot aiRobot;

    public AIRobotGraphic(AIRobot robotObj) {
        prior = 2;
        aiRobot = robotObj;
        MapView.getInstance().addDrawable(this);
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
