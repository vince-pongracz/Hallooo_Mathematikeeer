package org.asteroidapp.VIEW.drawables;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import org.asteroidapp.MODELL.spaceobjects.Asteroid;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

public class AsteroidGraphic extends Drawable {
    private final Asteroid asteroid;

    public AsteroidGraphic(Asteroid asteroidObj) {
        prior = 1;
        asteroid = asteroidObj;
        Tooltip.install(this, new Tooltip(asteroid.getInfo()));
        MapView.getInstance().addDrawable(this);
    }

    @Override
    protected Position getPosition() {
        return asteroid.getPosition();
    }

    @Override
    protected Image getLocalImage() {
        return MapView.asteroidGif;
    }

    @Override
    public Image getRemoteImage() {
        return MapView.asteroidRemote;
    }

    @Override
    public String getName() {
        return asteroid.getName();
    }

}
