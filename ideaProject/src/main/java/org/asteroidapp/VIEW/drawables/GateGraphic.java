package org.asteroidapp.VIEW.drawables;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.asteroidapp.MODELL.spaceobjects.Gate;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

public class GateGraphic extends Drawable{
    private final Gate gate;

    public GateGraphic(Gate gateObj) {
        prior = 2;
        gate = gateObj;
        Tooltip.install(this,new Tooltip(getName()));
        MapView.getInstance().addDrawable(this);
    }

    @Override
    protected Position getPosition() {
        return gate.getPosition();
    }

    @Override
    protected String getImagePath() {
        return MapView.portalGif;
    }

    @Override
    public Image getRemoteImage() {
        return MapView.portal;
    }

    @Override
    public String getName() {
        return gate.getName();
    }
}
