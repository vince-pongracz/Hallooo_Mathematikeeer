package org.asteroidapp.CONTROLLER;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.VIEW.RightView;
import org.asteroidapp.util.CallStackViewer;

import java.io.FileNotFoundException;


public class AppController extends Application {

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Hello World!");

        //MapView mapView = new MapView();
        RightView rightView = new RightView();

        HBox hbox = new HBox();
        VBox vBox = rightView.getVBox();
        //Group root = mapView.getGroup();

        //hbox.getChildren().add(root);
        hbox.getChildren().add(vBox);

        Scene sc = new Scene(hbox, 1500, 900);
        sc.getStylesheets().add("https://fonts.googleapis.com/css2?family=VT323&display=swap");
        sc.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());

        primaryStage.setScene(sc);
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

    /*
    public void testMode() {

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

        //app.consoleDemo();

        launch(args);

        CallStackViewer.getInstance().methodReturns();
    }
}