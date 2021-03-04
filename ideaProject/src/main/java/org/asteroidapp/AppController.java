package org.asteroidapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.util.ConsoleUI;

//extends Application
public class AppController {

    //Demo
    /*
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
     */


    /*
     *
     */

    //questionable method...
    private void startGame() {

    }

    /*
     *
     */
    public void quitGame() {

    }

    /*
     *
     */
    public void pauseAndResumeGame() {

    }

    private static Logger log = LogManager.getLogger(AppController.class.toString());

    private static boolean quitCondition = false;

    private void consoleDemo() {

        log.log(Level.INFO, "app started");
        log.log(Level.INFO, "Hello team :)");

        while (!quitCondition) {
            ConsoleUI.getInstance().sendMessageToConsole("Type start to start!");
            String response = ConsoleUI.getInstance().readLineFromConsole();
            if (response != null) {
                if (response.equals("start")) {
                    //startGame();
                    GameController.getInstance().setupGame();
                    GameController.getInstance().inGame();


                } else if (response.equals("help")) {
                    ConsoleUI.getInstance().sendMessageToConsole("help --> help msg");
                    ConsoleUI.getInstance().sendMessageToConsole("start --> start and config game");
                    ConsoleUI.getInstance().sendMessageToConsole("quit --> close app");
                } else if (response.equals("quit")) {
                    quitCondition = true;
                    //delete resources
                } else {
                    //NOP
                }
            }

        }
    }

    public static void main(String[] args) {
        AppController app = new AppController();
        app.consoleDemo();
    }
}