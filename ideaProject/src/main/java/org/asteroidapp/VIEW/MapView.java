package org.asteroidapp.VIEW;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.asteroidapp.VIEW.drawables.Drawable;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MapView {

    public static final String alienPng = "file:src/main/java/resources/images/Alien.png";
    public static final String asteroidGif = "file:src/main/java/resources/images/asteroid_game.gif";
    public static final String portalGif = "file:src/main/java/resources/images/Portal.gif";
    public static final String robotPng = "file:src/main/java/resources/images/Robot.png";
    public static final String spaceshipGif = "file:src/main/java/resources/images/Spaceship.gif";
    public static final String sunGif = "file:src/main/java/resources/images/Sun.gif";


    Image backgroundImage = new Image("file:src/main/java/resources/images/asteroid_game.gif");
    ImageView imBackground = new ImageView(backgroundImage);
    List<Drawable> drawables = new ArrayList<>();
    Group mapViewGroup = new Group();

    private static MapView instance = null;
    private MapView(){}
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
        for (int i = 0; i < drawables.size(); i++) {
            //prior alapjan rendezze
            //background 0
            //asteroida 1
            // tobbi az ketto
            mapViewGroup.getChildren().removeAll();
            mapViewGroup.getChildren().addAll(imBackground);
            for (int j = 0; j < drawables.size(); j++){
                if (drawables.get(i).getPrior() == 1) {
                    try {
                        mapViewGroup.getChildren().addAll(drawables.get(i).draw());
                    } catch (FileNotFoundException e){
                        //TODO log/popup
                    }

                }
            }
            for (int k = 0; k < drawables.size(); k++){
                if (drawables.get(i).getPrior() == 2) {
                    try {
                        mapViewGroup.getChildren().addAll(drawables.get(i).draw());
                    } catch (FileNotFoundException e){
                        //TODO log/popup
                    }
                }
            }
        }
    }

    public Group getMapViewGroup(){
        return mapViewGroup;
    }

}
