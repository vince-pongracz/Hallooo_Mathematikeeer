package org.asteroidapp.drawables;

import org.asteroidapp.entities.Settler;
import org.asteroidapp.spaceobjects.Position;

public class SettlerGraphic extends Drawable {
    private final Settler settler;

    public SettlerGraphic(Settler SettlerObj) {
        prior = 2;
        settler = SettlerObj;
    }

    @Override
    protected Position getPosition() {
        return settler.getMySpaceObject().getPosition();
    }

    @Override
    protected String getImagePath() {
        return MapView.spaceshipGif;
    }

    @Override
    public String getName() {
        return settler.getName();
    }
}
