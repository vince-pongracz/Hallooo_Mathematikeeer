package org.asteroidapp.VIEW.drawables;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.asteroidapp.MODELL.EventType;
import org.asteroidapp.MODELL.interfaces.Observer;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;
import java.io.FileNotFoundException;

/**
 * This is an abstract base class for the objects that will be drawn on the field
 */
public abstract class Drawable extends ImageView implements Observer {
    protected int prior;
    protected Tooltip infoTip = new Tooltip("Default Drawable Tooltip");

    public int getPrior() {
        return prior;
    }

    public ImageView updateGraphics() throws FileNotFoundException {
        this.setVisible(true);

        if (isVisible()) {
            this.setImage(getLocalImage());
            this.setX(getPosition().getX());
            this.setY(getPosition().getY());
            this.refreshTooltip();

            //this.setPreserveRatio(true);

            return (ImageView) this;
            //Setting the preserve ratio of the image view
            //imageView.setPreserveRatio(true);     lehet kell példában volt
        } else {
            throw new FileNotFoundException("This GUI element is not allowed (not visible)");
        }
    }

    protected abstract Position getPosition();

    protected abstract Image getLocalImage();

    protected abstract void refreshTooltip();

    public abstract Image getRemoteImage();

    public abstract String getName();

    @Override
    public void notify(EventType eventType) {
        switch (eventType) {
            case REFRESH -> {
                try {
                    updateGraphics();
                } catch (FileNotFoundException e) {
                    //log
                } catch (NullPointerException e) {
                    //log -- fetch info from modell wasn't successful
                    // --> it probably doesn't exist
                    // --> delete graphical representation
                    MapView.getInstance().removeDrawable(this);
                }
            }
            case DELETE -> MapView.getInstance().removeDrawable(this);
        }
    }
}
