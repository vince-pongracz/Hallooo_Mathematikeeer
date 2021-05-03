package org.asteroidapp.VIEW;

import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.asteroidapp.CONTROLLER.CommandInterpreter;
import org.asteroidapp.CONTROLLER.GameController;
import org.asteroidapp.MODELL.entities.Settler;
import org.asteroidapp.MODELL.resources.*;
import org.asteroidapp.util.ActionResponse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class RightView {
    VBox vbox = new VBox(20);
    ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(new Button(" Move "), new Button(" Drill "), new Button(" Mine "), new Button(" Create gate "), new Button(" Build Gate "), new Button(" Create Robot "), new Button(" Deploy Resource ")));
    ArrayList<Label> labels = new ArrayList<>(Arrays.asList(new Label("Gate: 0"), new Label(" Iron: 0"), new Label(" Coal: 0"), new Label(" Uran: 0"), new Label(" Frozen water: 0"), new Label("-------------------------------\n   Aron's Settler2's round \n   Sunflair is coming in 3 rounds")));
    ArrayList<ImageView> images = new ArrayList<>();

    //TODO method define actionListeners

    public RightView() throws FileNotFoundException {
        vbox.setAlignment(Pos.CENTER);
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));

            images.add(new ImageView(new Image(new FileInputStream("src/main/resources/images/Spaceship.gif"))));
            images.get(0).setPreserveRatio(true);
            images.get(0).setFitHeight(35);

        images.add(new ImageView(new Image(new FileInputStream("src/main/resources/images/Asteroid_01.png"))));
        images.get(1).setPreserveRatio(true);
        images.get(1).setFitHeight(35);

        images.add(new ImageView(new Image(new FileInputStream("src/main/resources/images/Asteroid_01.png"))));
        images.get(2).setPreserveRatio(true);
        images.get(2).setFitHeight(35);

        images.add(new ImageView(new Image(new FileInputStream("src/main/resources/images/Portal.gif"))));
        images.get(3).setPreserveRatio(true);
        images.get(3).setFitHeight(35);

        images.add(new ImageView(new Image(new FileInputStream("src/main/resources/images/Portal.gif"))));
        images.get(4).setPreserveRatio(true);
        images.get(4).setFitHeight(35);

        images.add(new ImageView(new Image(new FileInputStream("src/main/resources/images/Robot.png"))));
        images.get(5).setPreserveRatio(true);
        images.get(5).setFitHeight(35);

        images.add(new ImageView(new Image(new FileInputStream("src/main/resources/images/Asteroid_01.png"))));
        images.get(6).setPreserveRatio(true);
        images.get(6).setFitHeight(35);



        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setGraphic(images.get(i));
            buttons.get(i).setMinWidth(200);
            buttons.get(i).setMinHeight(50);
            buttons.get(i).setContentDisplay(ContentDisplay.LEFT);
            buttons.get(i).getStyleClass().add("buttonRight");
            vbox.getChildren().add(buttons.get(i));
        }

        for (int i = 0; i < labels.size(); i++) {
            labels.get(i).getStyleClass().add("labelRightFont");
            vbox.getChildren().add(labels.get(i));
        }

        //move
        buttons.get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //? kattintással kiválasztani
            }
        });

        //drill
        buttons.get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //send command drill
                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("command", "drill");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //mine
        buttons.get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                JsonObject jsonCmd= new JsonObject();
                jsonCmd.addProperty("command","mine");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);

                //popup for response
                //isSuccess, getMessage
            }
        });

        //create gate
        buttons.get(3).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //send command create gate
                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("command", "createGate");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //build gate
        buttons.get(4).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //send command build gate
                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("command", "buildGate");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //create robot
        buttons.get(5).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //send command create robot
                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("command", "createBot");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //deploy resource
        buttons.get(6).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                // ? 
                //Rena
            }
        });
    }

    public VBox getVBox() {
        return vbox;
    }

    public void refreshRightView(Settler settler, int sunflair) {
        if (settler != null) {
            ResourceStorage storage = settler.getStorage();
            //a gate-eket valahogy mashogy kellene lekerni
            //TODO getGateNum - Rena
            labels.set(0, new Label(" Gate: "));
            labels.set(1, new Label(" Iron: " + storage.countOf(new Iron())));
            labels.set(2, new Label(" Coal: " + storage.countOf(new Coal())));
            labels.set(3, new Label(" Uran: " + storage.countOf(new Uran())));
            labels.set(4, new Label(" FrozenWater: " + storage.countOf(new FrozenWater())));
            labels.set(5, new Label(" -------------------------------\n " + GameController.getInstance().getActualPlayer() + "'s " + settler.getName() + "'s round\n Sunflair is coming in " + sunflair + " rounds"));
        } else {
            System.out.println("Settler was null");
        }
    }

    public void reactToActionResponse(ActionResponse respone){
        return;
    }
}
