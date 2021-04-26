package org.asteroidapp;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.resources.Coal;
import org.asteroidapp.resources.FrozenWater;
import org.asteroidapp.resources.ResourceStorage;
import org.asteroidapp.resources.Uran;
import org.asteroidapp.util.CallStackViewer;
import org.asteroidapp.util.ConsoleUI;
import org.asteroidapp.util.TestConfig;


import javafx.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Queue;
import javafx.scene.control.Button;


//extends Application
public class AppController extends Application {

    //JavaFX demo

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    //TODO is it necessary?
    //questionable method...
    private void startGame() {
        log.log(Level.TRACE, "Start game");
    }

    /*
     *
     */
    public void quitGame() {
        log.log(Level.TRACE, "Game quit");
    }

    /*
     *
     */
    public void pauseAndResumeGame() {
        log.log(Level.TRACE, "Game is paused / resumed");
    }

    /**
     * Logger for AppController
     */
    private static Logger log = LogManager.getLogger(AppController.class.getSimpleName());

    private static boolean quitCondition = false;


    /*public void testMode() {
        try {
            ConsoleUI.getInstance().sendMessageToConsole("Filename of testconfig:  (testconfigs/...)");
            String filename = ConsoleUI.getInstance().readLineFromConsole();
            Gson json = new Gson();
            TestConfig config = json.fromJson(new JsonReader(new FileReader(filename)), TestConfig.class);
            if (config.checkConfig()) {
                ConsoleUI.getInstance().sendMessageToConsole("config is OK");
            } else {
                log.log(Level.ERROR, "Bad config");
                System.err.println("Wrong config");
                return;
            }

            ConsoleUI.getInstance().addTestConfig(config);
            Queue<String> autoCommands = config.setConfigIntoComponents();
            ConsoleUI.getInstance().setAutoCommands(autoCommands);

            GameController.getInstance().setupGame();
            GameController.getInstance().inGame();
            config.eval();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
    }
     */

    public static void main(String[] args) {
        CallStackViewer.getInstance().methodStartsLogCall("___CALLSTACK:___");

        AppController app = new AppController();

        launch(args);

        CallStackViewer.getInstance().methodReturns();
    }
}