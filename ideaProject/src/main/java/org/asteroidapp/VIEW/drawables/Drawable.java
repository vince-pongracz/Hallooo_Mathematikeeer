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

    public int getPrior() {
        return prior;
    }

    public ImageView draw() throws FileNotFoundException {
        Image image = new Image(new FileInputStream(getImagePath()));

        ImageView imageView = new ImageView(image);

        imageView.setX(getPosition().getX());
        imageView.setY(getPosition().getY());

        return imageView;
        //Setting the preserve ratio of the image view
        //imageView.setPreserveRatio(true);     lehet kell példában volt
    }

    protected abstract Position getPosition();

    protected abstract String getImagePath();

    public abstract String getName();
}
