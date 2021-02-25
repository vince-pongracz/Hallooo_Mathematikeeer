package org.asteroidapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class AppController extends Application{

    public void init(){

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button1 = new Button("Hi team! :)");
        StackPane root = new StackPane();
        root.getChildren().add(button1);
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Asteroids - beta");
        primaryStage.show();
    }

    public static void main(String[] args){
        System.out.println("Hello Team! :)");
        launch(args);
    }
}