package org.asteroidapp.VIEW;

import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.asteroidapp.VIEW.drawables.Drawable;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MapView {

    public static Image ufoRemote = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/Alien.png");
    public static Image asteroidRemote = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/Asteroid_01.gif");
    public static Image portalRemote = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/Portal.gif");
    public static Image robotRemote = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/Robot.png");
    public static Image shipRemote = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/Spaceship.gif");
    public static Image sunRemote = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/Sun.gif");
    public static Image backgroundRemote = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/asteroid_game.gif");

    public static final Image alienPng = new Image("file:src/main/resources/images/Alien.png");
    public static final Image asteroidGif = new Image("file:src/main/resources/images/Asteroid_01.png");
    public static final Image portalGif = new Image("file:src/main/resources/images/Portal.gif");
    public static final Image robotPng = new Image("file:src/main/resources/images/Robot.png");
    public static final Image spaceshipGif = new Image("file:src/resources/images/Spaceship.gif");
    public static final Image sunGif = new Image("file:src/main/resources/images/Sun.gif");

    Image backgroundImage = new Image("file:src/main/resources/images/asteroid_game.gif");
    ImageView imBackground = new ImageView(backgroundImage);
    List<Drawable> drawables = new ArrayList<>();
    Pane pane = new Pane();

    private static MapView instance = null;

    private MapView() {
        imBackground.setFitHeight(900);
        imBackground.setFitWidth(1500);
    }

    public static MapView getInstance() {
        if (instance == null) {
            instance = new MapView();
        }
        return instance;
    }

    public void addDrawable(Drawable newDrawable) {
        drawables.add(newDrawable);
        refreshMap();
    }

    public void removeDrawable(Drawable removeDrawable) {
        for (int i = 0; i < drawables.size(); i++) {
            if (drawables.get(i).getName().equals(removeDrawable.getName())) {
                drawables.remove(i);
            }
        }
        refreshMap();
    }


    public void refreshMap() {
        //TODO refactor

        Collections.sort(drawables, new Comparator<Drawable>() {
            @Override
            public int compare(Drawable d1, Drawable d2) {
                if (d1.getPrior() < d2.getPrior())
                    return -1;
                if (d1.getPrior() > d2.getPrior())
                    return 1;
                return 0;
            }
        });
        imBackground.setX(0);
        imBackground.setY(0);
        Tooltip.install(imBackground, new Tooltip("BackGround"));

        pane.getChildren().clear();
        pane.setLayoutX(0);
        pane.setLayoutY(0);
        pane.getChildren().add(imBackground);
        for (var item : drawables) {
            try {
                pane.getChildren().add(item.updateGraphics());
            } catch (FileNotFoundException e) {
                //TODO log/popup
                System.err.println(e.getMessage());
            }
        }



    }

    public Pane getMapViewPane() {
        return pane;
    }
}
