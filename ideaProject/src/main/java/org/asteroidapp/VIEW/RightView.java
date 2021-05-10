package org.asteroidapp.VIEW;

import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.asteroidapp.CONTROLLER.AsteroidZone;
import org.asteroidapp.CONTROLLER.CommandInterpreter;
import org.asteroidapp.CONTROLLER.GameController;
import org.asteroidapp.MODELL.entities.Settler;
import org.asteroidapp.MODELL.resources.*;
import org.asteroidapp.MODELL.spaceobjects.SteppableSpaceObject;
import org.asteroidapp.MODELL.spaceobjects.Sun;
import org.asteroidapp.util.ActionResponse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class RightView {
    VBox vbox = new VBox(23);
    ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(new Button(" Move "), new Button(" Drill "), new Button(" Mine "), new Button(" Create gate "), new Button(" Build Gate "), new Button(" Create Robot "), new Button(" Deploy Resource "), new Button(" Skip Round ")));
    ArrayList<Label> labels = new ArrayList<>(Arrays.asList(
            new Label(" Gate: 0\t\t Iron: 0"),
            new Label(" Coal: 0\t\t Uran: 0"),
            new Label(" Frozen water: 0"),
            new Label("Aron's Settler2's round \n   Sunflair is coming in 3 rounds")));
    ArrayList<ImageView> images = new ArrayList<>();

    //TODO method define actionListeners

    public RightView() throws FileNotFoundException {
        vbox.setAlignment(Pos.CENTER);

        vbox.setMinWidth(400);

        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(27, 27, 36), CornerRadii.EMPTY, Insets.EMPTY)));

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

        images.add(new ImageView(new Image(new FileInputStream("src/main/resources/images/Spaceship.gif"))));
        images.get(7).setPreserveRatio(true);
        images.get(7).setFitHeight(35);


        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setGraphic(images.get(i));
            buttons.get(i).setMinWidth(300);
            buttons.get(i).setMinHeight(50);
            buttons.get(i).setContentDisplay(ContentDisplay.LEFT);
            buttons.get(i).getStyleClass().add("buttonRight");
            vbox.getChildren().add(buttons.get(i));
        }

        refreshRightView(GameController.getInstance().getActualPlayer().getActualSettler());

        //move
        buttons.get(0).setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                //---crop out
                SteppableSpaceObject nextAsteroid = AsteroidZone.getInstance().getNearestObject(Menu.mousePosition);
                System.out.println(nextAsteroid.getName() + ", " + nextAsteroid.getPosition().getX() + ", " + nextAsteroid.getPosition().getY());
                //

                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("command", "move");

                JsonObject jsonCmd2 = new JsonObject();
                jsonCmd2.addProperty("targetX", Menu.mousePosition.getX());
                jsonCmd2.addProperty("targetY", Menu.mousePosition.getY());
                jsonCmd.add("target", jsonCmd2);
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //drill
        buttons.get(1).setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                //send command drill
                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("command", "drill");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //mine
        buttons.get(2).setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("command", "mine");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //create gate
        buttons.get(3).setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                //send command create gate
                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("command", "createGate");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //build gate
        buttons.get(4).setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                //send command build gate
                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("command", "buildGate");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //create robot
        buttons.get(5).setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                //send command create robot
                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("command", "createBot");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //deploy resource
        buttons.get(6).setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {

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
                setDialogAndButtonStyle(alert);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ironBT) {
                    chosenResource = "Iron";
                } else if (result.get() == coalBT) {
                    chosenResource = "Coal";
                } else if (result.get() == uranBT) {
                    chosenResource = "Uran";
                } else if (result.get() == frozenWaterBT) {
                    chosenResource = "FrozenWater";
                }
                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("command", "deploy");
                jsonCmd.addProperty("resource", chosenResource);

                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });

        //skip round
        buttons.get(7).setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                //send command create robot
                JsonObject jsonCmd = new JsonObject();
                jsonCmd.addProperty("command", "next");
                var response = CommandInterpreter.getInstance().sendCommandToModell(jsonCmd);
                reactToActionResponse(response);
            }
        });
    }

    public VBox getVBox() {
        return vbox;
    }

    public void refreshRightView(Settler settler) {
        if (settler != null) {
            ResourceStorage storage = settler.getStorage();

            vbox.getChildren().removeAll(labels);
            labels.clear();
            labels = new ArrayList<>(Arrays.asList(
                    new Label(" Gate: " + settler.getGateNum() + "\t\t Iron: " +  storage.countOf(new Iron())),
                    new Label(" Coal: " + storage.countOf(new Coal()) + "\t\t Uran: " + storage.countOf(new Uran())),
                    new Label(" FrozenWater: " + storage.countOf(new FrozenWater())),
                    new Label(" " + settler.getName() + "'s round\n Sunflair is coming in " + (Sun.sunFlairInEveryXRound - GameController.getInstance().getRound() % Sun.sunFlairInEveryXRound) + " rounds")));
            for (var labelItem : labels) {
                labelItem.getStyleClass().add("labelRightFont");
            }
            vbox.getChildren().addAll(labels);
        } else {
            System.out.println("Settler was null");
        }

        checkWin();
    }

    public void reactToActionResponse(ActionResponse response) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Asteroid Game - Information Dialog");
        if (response.isSuccessful())
            alert.setHeaderText("Action successful");
        else
            alert.setHeaderText("Action unsuccessful");
        alert.setContentText(response.getMessage());
        setDialogAndButtonStyle(alert);
        alert.showAndWait();

        refreshRightView(GameController.getInstance().getActualPlayer().getActualSettler());
    }

    public void checkWin(){
        if(GameController.playerHasWon){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Asteroid Game - Information Dialog");
            alert.setHeaderText("You Win");
            alert.setContentText("You collected all the needed resources to build a space base. Congratulations, you win. :)");
            alert.showAndWait();
            System.exit(0);
        }
    }

    public static void setDialogAndButtonStyle(Dialog alert){
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(MapView.getInstance().getClass().getResource("/style.css")).toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        ButtonBar buttonBar = (ButtonBar)alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b->b.setStyle("""
                -fx-font-family: 'Zen Dots', cursive;
                    -fx-font-weight: 100;
                    -fx-font-size: 17;
                    -fx-background-color: transparent;
                    -fx-border-width: 500%;
                    -fx-border-color: linear-gradient(from 15% 15% to 100% 100%, #12acba, #1852b4, #3b1d7b, #5C1780);
                    -fx-border-radius: 180px;
                    -fx-cursor: hand;
                    -fx-text-fill: white;
                    -fx-background-radius: 180px;"""));
    }
}
