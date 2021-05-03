package org.asteroidapp.VIEW.drawables;

import org.asteroidapp.MODELL.entities.Ufo;
import org.asteroidapp.MODELL.interfaces.EventType;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

public class UfoGraphic  extends Drawable {
    private final Ufo ufo;

    public UfoGraphic(Ufo ufoObj) {
        prior = 2;
        ufo = ufoObj;
        MapView.getInstance().addDrawable(this);
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
