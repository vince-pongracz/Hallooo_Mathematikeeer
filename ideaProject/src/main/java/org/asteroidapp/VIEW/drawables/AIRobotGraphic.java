package org.asteroidapp.VIEW.drawables;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import org.asteroidapp.MODELL.entities.AIRobot;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

public class AIRobotGraphic extends Drawable {
    private final AIRobot aiRobot;

    public AIRobotGraphic(AIRobot robotObj) {
        prior = 2;
        aiRobot = robotObj;
        refreshTooltip();
        MapView.getInstance().addDrawable(this);
    }

    @Override
    protected Position getPosition() {
        Position old = aiRobot.getMySpaceObject().getPosition();
        return new Position(old.getX() - 5, old.getY());
    }

    @Override
    protected Image getLocalImage() {
        return MapView.robotPng;
    }

    @Override
    protected void refreshTooltip() {
        Tooltip.uninstall(this,infoTip);
        this.infoTip = new Tooltip(aiRobot.getName());
        Tooltip.install(this,infoTip);
    }

    @Override
    public Image getRemoteImage() {
        return MapView.robotRemote;
    }

    @Override
    public String getName() {
        return aiRobot.getName();
    }
}
