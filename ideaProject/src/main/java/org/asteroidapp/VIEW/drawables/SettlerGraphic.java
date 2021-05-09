package org.asteroidapp.VIEW.drawables;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.asteroidapp.CONTROLLER.AppController;
import org.asteroidapp.MODELL.EventType;
import org.asteroidapp.MODELL.entities.Settler;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.VIEW.MapView;
import org.asteroidapp.VIEW.Menu;
import org.asteroidapp.VIEW.RightView;
import org.controlsfx.control.Notifications;

import java.util.Map;

public class SettlerGraphic extends Drawable {
    private final Settler settler;

    public SettlerGraphic(Settler SettlerObj) {
        prior = 2;
        settler = SettlerObj;
        refreshTooltip();
        MapView.getInstance().addDrawable(this);
    }

    @Override
    protected Position getPosition() {
        Position old = settler.getMySpaceObject().getPosition();
        return new Position(old.getX() + 30, old.getY() - 25);
    }

    @Override
    protected Image getLocalImage() {
        return MapView.spaceshipGif;
    }

    @Override
    protected void refreshTooltip() {
        Tooltip.uninstall(this, infoTip);
        this.infoTip = new Tooltip(settler.getName());
        Tooltip.install(this, infoTip);
    }

    @Override
    public Image getRemoteImage() {
        return MapView.shipRemote;
    }

    @Override
    public String getName() {
        return settler.getName();
    }

    @Override
    public void notify(EventType eventType) {
        super.notify(eventType);
        //Sajni még nem megy, valószínűleg GUI thread blokkolás van (bár nem értem hogy), ezért nem jelenik meg :(

        if (eventType == EventType.DELETE) {

            /*
            Elvileg kellene neki vmi stage... :/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppController.thisStage.requestFocus();
                    AppController.thisStage.show();
                    var notification = Notifications.create()
                            .title("Notification")
                            .text("You are dead! ")
                            .hideAfter(Duration.seconds(2))
                            .position(Pos.CENTER)
                            .darkStyle();
                    notification.show();
                }
            }).start();
             */
        }
    }
}
