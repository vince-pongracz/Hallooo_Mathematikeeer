package org.asteroidapp.VIEW.drawables;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.asteroidapp.MODELL.EventType;
import org.asteroidapp.MODELL.interfaces.Observer;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;
import org.asteroidapp.VIEW.RightView;

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
        Position old = new Position(this.getX(), this.getY());

        if (isVisible()) {
            this.setImage(getLocalImage());
            this.setX(getPosition().getX());
            this.setY(getPosition().getY());
            this.refreshTooltip();

            if(prior == 2) {
                this.setX(old.getX());
                this.setY(old.getY());
                TranslateTransition transition = new TranslateTransition(Duration.seconds(2), this);
                Position v = new Position(getPosition().getX() - old.getX(), getPosition().getY() - old.getY());
                /*transition.setFromX(old.getX()); //
                transition.setFromY(old.getY());*/
                transition.setToX(v.getX());
                transition.setToY(v.getY());
                transition.playFromStart();
            }


            return this;
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
            case DELETE -> {
                MapView.getInstance().removeDrawable(this);
                reactToActionResponse();
            }
        }
    }


    public void reactToActionResponse() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.isShowing();
        alert.setTitle("Asteroid Game - Information Dialog");
        alert.setHeaderText(getName() + "  died");
        alert.setContentText("Settler died in a solarflair or explosion");
        RightView.setDialogAndButtonStyle(alert);
        alert.showAndWait();
    }
}
