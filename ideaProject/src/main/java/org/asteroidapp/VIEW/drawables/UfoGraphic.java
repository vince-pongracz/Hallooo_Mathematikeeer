package org.asteroidapp.VIEW.drawables;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.asteroidapp.MODELL.entities.Ufo;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

import java.io.FileNotFoundException;

public class UfoGraphic extends Drawable {
    private final Ufo ufo;

    public UfoGraphic(Ufo ufoObj) {
        prior = 2;
        ufo = ufoObj;
        refreshTooltip();
        MapView.getInstance().addDrawable(this);
    }

    @Override
    protected Position getPosition() {
        Position old = ufo.getMySpaceObject().getPosition();
        return new Position(old.getX() + 5, old.getY() - 20);
    }

    @Override
    protected Image getLocalImage() {
        return MapView.alienPng;
    }

    @Override
    protected void refreshTooltip() {
        Tooltip.uninstall(this, infoTip);
        this.infoTip = new Tooltip(ufo.getName());
        Tooltip.install(this, infoTip);
    }

    @Override
    public String getName() {
        return ufo.getName();
    }

    @Override
    public Image getRemoteImage() {
        return MapView.ufoRemote;
    }

    @Override
    public ImageView updateGraphics() throws FileNotFoundException {
        super.updateGraphics();
        this.setFitHeight(56);
        this.setFitWidth(56);
        this.setPreserveRatio(true);
        return this;
    }
}
