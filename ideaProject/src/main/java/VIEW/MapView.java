package VIEW;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MapView {


    public static final String alienPng = "file:src/main/java/org/asteroidapp/images/Alien.png";
    public static final String asteroidGif = "file:src/main/java/org/asteroidapp/images/asteroid_game.gif";
    public static final String portalGif = "file:src/main/java/org/asteroidapp/images/Portal.gif";
    public static final String robotPng = "file:src/main/java/org/asteroidapp/images/Robot.png";
    public static final String spaceshipGif = "file:src/main/java/org/asteroidapp/images/Spaceship.gif";
    public static final String sunGif = "file:src/main/java/org/asteroidapp/images/Sun.gif";


    Image backgroundImage = new Image("file:src/main/java/org/asteroidapp/images/asteroid_game.gif");
    ImageView imBackground = new ImageView(backgroundImage);
    List<Drawable> drawables = new ArrayList<Drawable>();
    Group mapViewGroup = new Group();


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
        for (int i = 0; i < drawables.size(); i++) {
            //prior alapjan rendezze
            //background 0
            //asteroida 1
            // tobbi az ketto
            mapViewGroup.getChildren().removeAll();
            mapViewGroup.getChildren().addAll(imBackground);
            for (int j = 0; j < drawables.size(); j++){
                if (drawables.get(i).getPrior() == 1) {
                    mapViewGroup.getChildren().addAll(drawables.get(i).draw());
                }
            }
            for (int k = 0; k < drawables.size(); k++){
                if (drawables.get(i).getPrior() == 2) {
                    mapViewGroup.getChildren().addAll(drawables.get(i).draw());
                }
            }
        }
    }

    public Group getMapViewGroup(){
        return mapViewGroup;
    }

}
