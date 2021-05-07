package org.asteroidapp.CONTROLLER;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.VIEW.Menu;
import org.asteroidapp.util.CallStackViewer;

import java.io.FileNotFoundException;


public class AppController extends Application {

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Asteroid 1.0 - HalloMath");

        Menu menu = new Menu(primaryStage);
        VBox hbox = menu.getVBox();

        Scene sc = new Scene(hbox, 500, 700);
        sc.getStylesheets().add("https://fonts.googleapis.com/css2?family=Zen+Dots&display=swap");
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

        //System.out.println("\uD83D\uDE00");

        AppController app = new AppController();
        //app.consoleDemo();

        launch(args);

        CallStackViewer.getInstance().methodReturns();
    }
}