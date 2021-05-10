package org.asteroidapp.VIEW;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.asteroidapp.CONTROLLER.CommandInterpreter;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.util.InitMessage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

public class Menu {
    VBox vbox;
    Button start, exit, enterNames;
    Label label;
    TextArea textArea;

    public static Position mousePosition = new Position(0, 0);

    public Menu(Stage stage) {

        ArrayList<String> names = new ArrayList<>();
        final boolean[] canBeStarted = {false};

        vbox = new VBox(35);
        vbox.setAlignment(Pos.CENTER);
        label = new Label("Enter the number of the players");
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(27, 27, 36), CornerRadii.EMPTY, Insets.EMPTY)));

        start = new Button("Start");
        start.setMinWidth(180);
        exit = new Button("Exit");
        exit.setMinWidth(180);
        enterNames = new Button("Enter Names");
        enterNames.setMinWidth(180);
        textArea = new TextArea();
        textArea.setMaxHeight(20);
        textArea.setMaxWidth(80);
        textArea.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/style.css")).toExternalForm());
        textArea.getStyleClass().add("textArea");

        start.getStyleClass().add("buttonRight");
        exit.getStyleClass().add("buttonRight");
        enterNames.getStyleClass().add("buttonRight");
        label.getStyleClass().add("labelRightFont");

        vbox.getChildren().add(label);
        vbox.getChildren().add(textArea);
        vbox.getChildren().add(enterNames);
        vbox.getChildren().add(start);
        vbox.getChildren().add(exit);

        TextInputDialog td = new TextInputDialog("");
        RightView.setDialogAndButtonStyle(td);

        enterNames.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                names.clear();
                td.setHeaderText("Enter your player name");
                try {
                    Integer.parseInt(textArea.getText());
                } catch (NumberFormatException exception) {
                    td.setHeaderText("You entered an invalid number, default player number  is 1\nEnter your player name");
                    textArea.setText("1");
                }
                for (int i = 0; i < Integer.parseInt(textArea.getText()); i++) {
                    td.getEditor().setText("");
                    td.showAndWait();
                    names.add(td.getEditor().getText());
                }
                canBeStarted[0] = true;
            }
        });

        exit.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {
                System.exit(0);
            }
        });

        start.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent e) {

                if (canBeStarted[0]) {
                    String[] namesString = names.toArray(new String[0]);

                    InitMessage initMessage = new InitMessage().setPlayerNum(Integer.parseInt(textArea.getText()))
                            .setNames(namesString).setAsteroidNum(42).setHomeCapacity(6)
                            .setMaxRound(50).setSettlerCapacity(4).setSettlerNum(2).setSunFlairInEveryXRound(5).setUfoNum(3);

                    CommandInterpreter.getInstance().initGame(initMessage);

                    RightView rightView = null;
                    MapView mapView = MapView.getInstance();

                    try {
                        rightView = new RightView();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    HBox hbox = new HBox();
                    VBox vBox = rightView.getVBox();
                    Pane p = mapView.getMapViewPane();
                    p.setVisible(true);
                    p.setLayoutX(0);
                    p.setLayoutY(0);
                    hbox.getChildren().add(p);
                    hbox.getChildren().add(vBox);

                    hbox.setOnMouseClicked(new EventHandler<>() {
                        @Override
                        public void handle(MouseEvent event) {
                            mousePosition = new Position(event.getSceneX(), event.getSceneY());
                        }
                    });

                    Scene sc = new Scene(hbox, 1900, 900);
                    sc.getStylesheets().add("https://fonts.googleapis.com/css2?family=Zen+Dots&display=swap");
                    sc.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("/style.css")).toExternalForm());
                    stage.setScene(sc);

                    stage.setResizable(false);
                    stage.setX(5);
                    stage.setY(65);

                    stage.show();
                }
            }
        });
    }

    public VBox getVBox() {
        return vbox;
    }
}
