package org.asteroidapp.VIEW;

import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
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
import java.util.Optional;

public class RightView {
    VBox vbox = new VBox(20);
    ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(new Button(" Move "), new Button(" Drill "), new Button(" Mine "), new Button(" Create gate "), new Button(" Build Gate "), new Button(" Create Robot "), new Button(" Deploy Resource ")));
    ArrayList<Label> labels = new ArrayList<>(Arrays.asList(new Label("Gate: 0"), new Label(" Iron: 0"), new Label(" Coal: 0"), new Label(" Uran: 0"), new Label(" Frozen water: 0"), new Label("-------------------------------\n   Aron's Settler2's round \n   Sunflair is coming in 3 rounds")));
    ArrayList<ImageView> images = new ArrayList<>();


    //TODO method define actionListeners

    public RightView() throws FileNotFoundException {
        vbox.setAlignment(Pos.CENTER);
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));

        for (int i = 0; i < 7; i++) {
            images.add(new ImageView(new Image(new FileInputStream("src/main/resources/images/Portal.gif"))));
            images.get(i).setPreserveRatio(true);
            images.get(i).setFitHeight(35);
        }

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
                //? kattintással kiválasztani ?
            }
        });

        //drill
        buttons.get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                JsonObject jsonCmd= new JsonObject();
                jsonCmd.addProperty("command","drill");
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
                reactToActionResponse(response);
            }
        });

        //create gate
        buttons.get(3).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                JsonObject jsonCmd= new JsonObject();
                jsonCmd.addProperty("command","createGate");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //build gate
        buttons.get(4).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                JsonObject jsonCmd= new JsonObject();
                jsonCmd.addProperty("command","buildGate");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //create robot
        buttons.get(5).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                JsonObject jsonCmd= new JsonObject();
                jsonCmd.addProperty("command","createBot");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //deploy resource
        buttons.get(6).setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                String chosenResource = "iron";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog for choosing a resource");
                alert.setHeaderText("Choose a resource that you would like to deploy");
                alert.setContentText("Resources and their amount are listed above the buttons on the right");

                ButtonType ironBT = new ButtonType("Iron");
                ButtonType coalBT = new ButtonType("Coal");
                ButtonType uranBT = new ButtonType("Uran");
                ButtonType frozenWaterBT = new ButtonType("Frozen Water");

                alert.getButtonTypes().setAll(ironBT, coalBT, uranBT, frozenWaterBT);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ironBT){
                    chosenResource = "Iron";
                } else if (result.get() == coalBT) {
                    chosenResource = "Coal";
                } else if (result.get() == uranBT) {
                    chosenResource = "Uran";
                } else if (result.get() == frozenWaterBT) {
                    chosenResource = "FrozenWater";
                }
                JsonObject jsonCmd= new JsonObject();
                jsonCmd.addProperty("command","deploy");
                jsonCmd.addProperty("resource", chosenResource);

                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });
    }

    public VBox getVBox() {
        return vbox;
    }

    public void refreshRightView(Settler settler, int sunflair) {
        if (settler != null) {
            ResourceStorage storage = settler.getStorage();
            labels.set(0, new Label(" Gate: " + settler.getGateNum()));
            labels.set(1, new Label(" Iron: " + storage.countOf(new Iron())));
            labels.set(2, new Label(" Coal: " + storage.countOf(new Coal())));
            labels.set(3, new Label(" Uran: " + storage.countOf(new Uran())));
            labels.set(4, new Label(" FrozenWater: " + storage.countOf(new FrozenWater())));
            labels.set(5, new Label(" -------------------------------\n " + GameController.getInstance().getActualPlayer() + "'s " + settler.getName() + "'s round\n Sunflair is coming in " + sunflair + " rounds"));
        } else {
            System.out.println("Settler was null");
        }
    }

    public void reactToActionResponse(ActionResponse response){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Asteroid Game - Information Dialog");
        if(response.isSuccessful())
            alert.setHeaderText("Action successful");
        else
            alert.setHeaderText("Action unsuccessful");
        alert.setContentText(response.getMessage());
        alert.showAndWait();
    }
}
