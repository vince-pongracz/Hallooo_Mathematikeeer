package org.asteroidapp.VIEW.drawables;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.asteroidapp.MODELL.entities.AIRobot;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

import java.io.FileNotFoundException;

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
        return new Position(old.getX() - 5, old.getY() + 5);
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

    @Override
    public ImageView updateGraphics() throws FileNotFoundException {
        super.updateGraphics();
        this.setFitHeight(50);
        this.setFitWidth(50);
        this.setPreserveRatio(true);
        return this;
    }
}
