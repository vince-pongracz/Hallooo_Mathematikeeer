package org.asteroidapp.VIEW.drawables;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import org.asteroidapp.MODELL.entities.Settler;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

public class SettlerGraphic extends Drawable {
    private final Settler settler;

    public SettlerGraphic(Settler SettlerObj) {
        prior = 2;
        settler = SettlerObj;
        Tooltip.install(this,new Tooltip(getName()));
        MapView.getInstance().addDrawable(this);
    }

    @Override
    protected Position getPosition() {
        return settler.getMySpaceObject().getPosition();
    }

    @Override
    protected Image getLocalImage() {
        return MapView.spaceshipGif;
    }

    @Override
    public Image getRemoteImage() {
        return MapView.ship;
    }

    @Override
    public String getName() {
        return settler.getName();
    }
}
