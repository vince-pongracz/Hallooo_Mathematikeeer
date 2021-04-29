package org.asteroidapp.VIEW.drawables;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.asteroidapp.MODELL.spaceobjects.Position;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This is an abstract base class for the objects that will be drawn on the field
 */
public abstract class Drawable {
    protected int prior;
    private boolean visible = true;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean setToThisState) {
        visible = setToThisState;
    }


    public int getPrior() {
        return prior;
    }

    public ImageView draw() throws FileNotFoundException {
        if (visible) {
            Image image = new Image(new FileInputStream(getImagePath()));

            ImageView imageView = new ImageView(image);

            imageView.setX(getPosition().getX());
            imageView.setY(getPosition().getY());

            return imageView;
            //Setting the preserve ratio of the image view
            //imageView.setPreserveRatio(true);     lehet kell példában volt
        } else {
            throw new FileNotFoundException("This GUI element is not allowed (not visible)");
        }
    }

    protected abstract Position getPosition();

    protected abstract String getImagePath();

    public abstract String getName();
}
