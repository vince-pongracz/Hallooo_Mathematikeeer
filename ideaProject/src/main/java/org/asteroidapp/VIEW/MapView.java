package org.asteroidapp.VIEW;

import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.asteroidapp.CONTROLLER.GameController;
import org.asteroidapp.VIEW.drawables.Drawable;

import java.io.FileInputStream;
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

    public static Image alienPng = null;
    public static Image asteroidGif = null;
    public static Image portalGif = null;
    public static Image robotPng = null;
    public static Image spaceshipGif = null;
    public static Image sunGif = null;
    public static Image backgroundImage = null;

    static {
        try {
            backgroundImage = new Image(new FileInputStream("src/main/resources/images/asteroid_game.gif"));
            alienPng = new Image(new FileInputStream("src/main/resources/images/Alien.png"));
            asteroidGif = new Image(new FileInputStream("src/main/resources/images/Asteroid_01.png"));
            portalGif = new Image(new FileInputStream("src/main/resources/images/Portal.gif"));
            robotPng = new Image(new FileInputStream("src/main/resources/images/Robot.png"));
            spaceshipGif = new Image(new FileInputStream("src/main/resources/images/Spaceship.gif"));
            sunGif = new Image(new FileInputStream("src/main/resources/images/Sun.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<Drawable> drawables = new ArrayList<>();
    ImageView imBackground = new ImageView(backgroundImage);
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

    public void addDrawable(Drawable newDrawable)  {
        drawables.add(newDrawable);
        refreshMap();
    }

    public boolean removeDrawable(Drawable removeDrawable) {
        var removed = drawables.remove(removeDrawable);
        refreshMap();
        return removed;
    }


    public void refreshMap() {
        //TODO refactor

        if(GameController.getPlayerNUm() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asteroid Game - Information Dialog");
            alert.setHeaderText("Game Over");
            alert.setContentText("You lost because there is no player who has any settler alive.");
            RightView.setDialogAndButtonStyle(alert);
            alert.showAndWait();
            System.exit(0);
        }

        drawables.sort(new Comparator<Drawable>() {
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
        Tooltip.install(imBackground, new Tooltip("Zone"));

        pane.getChildren().clear();

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
