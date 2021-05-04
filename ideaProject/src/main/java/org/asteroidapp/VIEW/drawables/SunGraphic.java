package org.asteroidapp.VIEW.drawables;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.MODELL.spaceobjects.Sun;
import org.asteroidapp.VIEW.MapView;

public class SunGraphic extends Drawable {
    private final Sun sun;

    public SunGraphic(Sun sunObj) {
        prior = 0;
        sun = sunObj;
        Tooltip.install(this,new Tooltip(getName()));
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
    public Image getRemoteImage() {
        return MapView.sun;
    }
}
