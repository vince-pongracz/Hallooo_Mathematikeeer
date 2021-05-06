package org.asteroidapp.VIEW.drawables;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import org.asteroidapp.MODELL.spaceobjects.Gate;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

public class GateGraphic extends Drawable{
    private final Gate gate;

    public GateGraphic(Gate gateObj) {
        prior = 2;
        gate = gateObj;
        refreshTooltip();
        MapView.getInstance().addDrawable(this);
    }

    @Override
    protected Position getPosition() {
        return gate.getPosition();
    }

    @Override
    protected Image getLocalImage() {
        return MapView.portalGif;
    }

    @Override
    protected void refreshTooltip() {
        Tooltip.uninstall(this,infoTip);
        this.infoTip = new Tooltip(gate.getName() + " A: " + gate.isActive());
        Tooltip.install(this,infoTip);
    }

    @Override
    public Image getRemoteImage() {
        return MapView.portalRemote;
    }

    @Override
    public String getName() {
        return gate.getName();
    }
}
