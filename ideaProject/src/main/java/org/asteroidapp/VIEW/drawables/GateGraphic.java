package org.asteroidapp.VIEW.drawables;

import org.asteroidapp.MODELL.spaceobjects.Gate;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

public class GateGraphic extends Drawable{
    private final Gate gate;

    public GateGraphic(Gate gateObj) {
        prior = 2;
        gate = gateObj;
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
    public String getName() {
        return gate.getName();
    }
}