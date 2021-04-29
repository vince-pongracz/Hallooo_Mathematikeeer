package org.asteroidapp.VIEW.drawables;

import org.asteroidapp.MODELL.interfaces.EventType;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.MODELL.spaceobjects.Sun;
import org.asteroidapp.VIEW.MapView;

public class SunGraphic extends Drawable {
    private final Sun sun;

    public SunGraphic(Sun sunObj) {
        prior = 2;
        sun = sunObj;
        MapView.getInstance().addDrawable(this);
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

    @Override
    public void recieveNotification(EventType eventType) {

    }
}
