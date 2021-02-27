package org.asteroidapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


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

    private static Logger log = LogManager.getLogger(AppController.class.toString());

    public static void main(String[] args){

        log.log(Level.INFO,"app started");
        System.out.println("Hello Team! :)");
        log.log(Level.INFO,"Hello team message written");
        launch(args);
        log.log(Level.INFO,"window opened, gui app launched");
        log.log(Level.WARN, "Shutdown");
        log.log(Level.TRACE,"tracing");
        log.log(Level.FATAL,"fatal");
        log.log(Level.DEBUG,"debug");
        log.log(Level.ERROR,"error");

    }

    /*
     *
     */
    private void startGame(){

    }
    /*
     *
     */
    private void quitGame(){

    }

    /*
     *
     */
    private void pauseAndResumeGame(){

    }
}