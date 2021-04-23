package org.asteroidapp.drawables;

import org.asteroidapp.spaceobjects.Gate;
import org.asteroidapp.spaceobjects.Position;

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
