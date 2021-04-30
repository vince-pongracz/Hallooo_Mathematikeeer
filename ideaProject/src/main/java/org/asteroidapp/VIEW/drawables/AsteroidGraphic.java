package org.asteroidapp.VIEW.drawables;

import org.asteroidapp.MODELL.interfaces.EventType;
import org.asteroidapp.MODELL.spaceobjects.Asteroid;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

public class AsteroidGraphic extends Drawable {
    private final Asteroid asteroid;

    public AsteroidGraphic(Asteroid asteroidObj) {
        prior = 1;
        asteroid = asteroidObj;
        MapView.getInstance().addDrawable(this);
    }

    @Override
    protected Position getPosition() {
        return asteroid.getPosition();
    }

    @Override
    protected String getImagePath() {
        return MapView.asteroidGif;
    }

    @Override
    public String getName() {
        return asteroid.getName();
    }

}
