package org.asteroidapp.VIEW.drawables;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.asteroidapp.MODELL.interfaces.EventType;
import org.asteroidapp.MODELL.interfaces.Observer;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This is an abstract base class for the objects that will be drawn on the field
 */
public abstract class Drawable extends ImageView implements Observer {
    protected int prior;

    public int getPrior() {
        return prior;
    }

    //TODO is it necessary to return an ImageView?
    public ImageView updateGraphics() throws FileNotFoundException {
        if (isVisible()) {
            Image image = new Image(new FileInputStream(getImagePath()));

            this.setImage(image);

            setX(getPosition().getX());
            setY(getPosition().getY());

            return this;
            //Setting the preserve ratio of the image view
            //imageView.setPreserveRatio(true);     lehet kell példában volt
        } else {
            throw new FileNotFoundException("This GUI element is not allowed (not visible)");
        }
    }

    protected abstract Position getPosition();

    protected abstract String getImagePath();

    public abstract String getName();

    @Override
    public void notify(EventType eventType) {
        if (this != null && eventType == EventType.REFRESH) {
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
        } else {
            //NOP
        }
    }
}
