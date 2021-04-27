package org.asteroidapp.drawables;

import org.asteroidapp.spaceobjects.Position;
import org.asteroidapp.spaceobjects.Sun;

public class SunGraphic extends Drawable {
    private final Sun sun;

    public SunGraphic(Sun sunObj) {
        prior = 2;
        sun = sunObj;
    }

    @Override
    protected Position getPosition() {
        return sun.getPosition();
    }

    @Override
    protected String getImagePath() {
        return MapView.sunGif;
    }

    @Override
    public String getName() {
        return "Sun";
    }
}
