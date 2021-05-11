package org.asteroidapp.VIEW.drawables;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.MODELL.spaceobjects.Sun;
import org.asteroidapp.VIEW.MapView;

public class SunGraphic extends Drawable {
    private final Sun sun;

    public SunGraphic(Sun sunObj) {
        prior = 0;
        sun = sunObj;
        refreshTooltip();
        MapView.getInstance().addDrawable(this);
    }

    @Override
    protected Position getPosition() {
        return sun.getPosition();
    }

    @Override
    protected Image getLocalImage() {
        return MapView.sunGif;
    }

    @Override
    protected void refreshTooltip() {
        Tooltip.uninstall(this,infoTip);
        this.infoTip = new Tooltip(sun.getName());
        Tooltip.install(this,infoTip);
    }

    @Override
    public String getName() {
        return "Sun";
    }
    @Override
    public Image getRemoteImage() {
        return MapView.sunRemote;
    }
}
