package org.asteroidapp.VIEW;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.asteroidapp.VIEW.drawables.Drawable;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MapView {

    public static final String alienPng = "file:src/main/resources/images/Alien.png";
    public static final String asteroidGif = "file:src/main/resources/images/asteroid_game.gif";
    public static final String portalGif = "file:src/main/resources/images/Portal.gif";
    public static final String robotPng = "file:src/main/resources/images/Robot.png";
    public static final String spaceshipGif = "file:src/resources/images/Spaceship.gif";
    public static final String sunGif = "file:src/main/resources/images/Sun.gif";

    Image backgroundImage = new Image("file:src/main/resources/images/asteroid_game.gif");
    ImageView imBackground = new ImageView(backgroundImage);
    List<Drawable> drawables = new ArrayList<>();
    Group mapViewGroup = new Group();

    private static MapView instance = null;
    private MapView(){
        imBackground.setFitHeight(900);
        imBackground.setFitWidth(1500);
    }
    public static MapView getInstance(){
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
                if ( d1.getPrior() < d2.getPrior())
                    return 1;
                if (d1.getPrior() > d2.getPrior())
                    return -1;
                return 0;
            }
        });
        mapViewGroup.getChildren().clear();
        mapViewGroup.getChildren().addAll(imBackground);
        for (int i = 0; i < drawables.size(); i++){
            if (drawables.get(i).getPrior() == 1) {
                try {
                    mapViewGroup.getChildren().addAll(drawables.get(i).updateGraphics());
                } catch (FileNotFoundException e){
                    //TODO log/popup
                }

            }
        }

    }


    public Group getMapViewGroup(){
        return mapViewGroup;
    }

}
