package org.asteroidapp.drawables;

import org.asteroidapp.entities.Ufo;
import org.asteroidapp.spaceobjects.Position;

public class UfoGraphic  extends Drawable {
    private final Ufo ufo;

    public UfoGraphic(Ufo ufoObj) {
        prior = 2;
        ufo = ufoObj;
    }

    @Override
    protected Position getPosition() {
        return ufo.getMySpaceObject().getPosition();
    }

    @Override
    protected String getImagePath() {
        return MapView.alienPng;
    }

    @Override
    public String getName() {
        return ufo.getName();
    }

}
