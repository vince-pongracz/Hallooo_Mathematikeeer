package org.asteroidapp.VIEW;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.asteroidapp.entities.Settler;
import org.asteroidapp.resources.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class RightView {
    VBox vbox = new VBox(20);
    ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(new Button(" Move "), new Button(" Drill "), new Button(" Mine "), new Button(" Create gate "), new Button(" Build Gate "), new Button(" Create Robot "), new Button(" Deploy Resource ")));
    ArrayList<Label> labels = new ArrayList<>(Arrays.asList(new Label("Gate: 0"), new Label(" Iron: 0" ), new Label(" Coal: 0"), new Label(" Uran: 0"), new Label(" Frozen water: 0"), new Label("-------------------------------\n   Aron's Settler2's round \n   Sunflair is coming in 3 rounds")));
    ArrayList<ImageView> images = new ArrayList<>();

    public RightView() throws FileNotFoundException {
        vbox.setAlignment(Pos.CENTER);
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));

        for(int i = 0; i < 7; i++){
            images.add(new ImageView(new Image(new FileInputStream("D:\\Users\\Downloads\\Portal (1).gif"))));
            images.get(i).setPreserveRatio(true);
            images.get(i).setFitHeight(35);
        }

        for (int i = 0; i < buttons.size(); i++){
            buttons.get(i).setGraphic(images.get(i));
            buttons.get(i).setMinWidth(200);
            buttons.get(i).setMinHeight(50);
            buttons.get(i).setContentDisplay(ContentDisplay.LEFT);
            buttons.get(i).getStyleClass().add("buttonRight");
            vbox.getChildren().add(buttons.get(i));
        }

        for (int i = 0; i < labels.size(); i++){
            labels.get(i).getStyleClass().add("labelRightFont");
            vbox.getChildren().add(labels.get(i));
        }
    }

    public VBox getVBox(){
        return vbox;
    }

    public void refreshRightView( Settler settler, int sunflair) {
        if (settler != null) {
            ResourceStorage storage = settler.getStorage();
            /*ironLabel = new Label(" Iron: " + storage.countOf(new Iron()));
            coalLabel = new Label(" Coal: " + storage.countOf(new Iron()));
            frozenWaterLabel = new Label(" Frozen water: " + storage.countOf(new Iron()));
            uranLabel = new Label(" Uran: " + storage.countOf(new Iron()));
            infoLabel = new Label(settler.getOwnerName() + "'s " + settler.getName() + "'s round\n Sunflair is coming in " + sunflair + " rounds");*/
        } else {
            System.out.println("Settler was null");
        }
    }
}
