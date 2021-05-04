package org.asteroidapp.VIEW.drawables;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.asteroidapp.MODELL.spaceobjects.Asteroid;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

public class AsteroidGraphic extends Drawable {
    private final Asteroid asteroid;

    public AsteroidGraphic(Asteroid asteroidObj) {
        prior = 1;
        asteroid = asteroidObj;
        Tooltip.install(this,new Tooltip(getName()));
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
    public Image getRemoteImage() {
        return MapView.asteroid;
    }

    @Override
    public String getName() {
        return asteroid.getName();
    }

}
