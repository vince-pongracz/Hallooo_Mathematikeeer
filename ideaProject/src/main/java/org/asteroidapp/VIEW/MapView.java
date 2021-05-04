package org.asteroidapp.VIEW;

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

    public static Image ufo = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/Alien.png");
    public static Image asteroid = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/Asteroid_01.gif");
    public static Image portal = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/Portal.gif");
    public static Image robot = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/Robot.png");
    public static Image ship = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/Spaceship.gif");
    public static Image sun = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/Sun.gif");
    public static Image background = new Image("https://github.com/vince-pongracz/Hallooo_Mathematikeeer/blob/beta-plans/ideaProject/src/main/resources/images/asteroid_game.gif");

    public static final Image alienPng = new Image("file:src/main/resources/images/Alien.png");
    public static final Image asteroidGif = new Image("file:src/main/resources/images/asteroid_game.gif");
    public static final Image portalGif = new Image("file:src/main/resources/images/Portal.gif");
    public static final Image robotPng = new Image("file:src/main/resources/images/Robot.png");
    public static final Image spaceshipGif = new Image("file:src/resources/images/Spaceship.gif");
    public static final Image sunGif = new Image("file:src/main/resources/images/Sun.gif");

    Image backgroundImage = new Image("file:src/main/resources/images/asteroid_game.gif");
    ImageView imBackground = new ImageView(backgroundImage);
    List<Drawable> drawables = new ArrayList<>();
    Group mapViewGroup = new Group();
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
        mapViewGroup.getChildren().clear();
        imBackground.setX(0);
        imBackground.setY(0);
        mapViewGroup = null;
        mapViewGroup = new Group();
        mapViewGroup.getChildren().add(imBackground);
        Tooltip.install(imBackground, new Tooltip("BackGround"));

        for (var drawItem : drawables) {
            try {
                mapViewGroup.getChildren().add(drawItem.updateGraphics());
            } catch (FileNotFoundException e) {
                //TODO log/popup
                System.err.println(e.getMessage());
            }
        }

        /* pane try
        pane.getChildren().clear();
        pane.setLayoutX(1200);
        pane.setLayoutY(900);
        for (var item : drawables) {
            try {
                pane.getChildren().add(item.updateGraphics());
            } catch (FileNotFoundException e) {
                //TODO log/popup
                System.err.println(e.getMessage());
            }
        }
        */


    }


    public Group getMapViewGroup() {
        return mapViewGroup;
    }

    public Pane getMapViewPane() {
        return pane;
    }
}
