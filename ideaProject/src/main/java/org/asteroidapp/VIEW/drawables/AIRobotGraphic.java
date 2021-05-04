package org.asteroidapp.VIEW.drawables;

import javafx.scene.image.Image;
import org.asteroidapp.MODELL.entities.AIRobot;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

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
    protected Image getLocalImage() {
        return MapView.robotPng;
    }

    @Override
    public Image getRemoteImage() {
        return MapView.robot;
    }

    @Override
    public String getName() {
        return aiRobot.getName();
    }
}
