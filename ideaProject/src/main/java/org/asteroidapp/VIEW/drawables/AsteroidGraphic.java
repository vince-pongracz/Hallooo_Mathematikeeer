package org.asteroidapp.drawables;

import org.asteroidapp.MODELL.spaceobjects.Asteroid;
import org.asteroidapp.MODELL.spaceobjects.Position;

public class AsteroidGraphic extends Drawable {
    private final Asteroid asteroid;

    public AsteroidGraphic(Asteroid asteroidObj) {
        prior = 1;
        asteroid = asteroidObj;
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
