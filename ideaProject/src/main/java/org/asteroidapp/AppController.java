package org.asteroidapp;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.VIEW.RightView;
import org.asteroidapp.resources.Coal;
import org.asteroidapp.resources.FrozenWater;
import org.asteroidapp.resources.ResourceStorage;
import org.asteroidapp.resources.Uran;
import org.asteroidapp.util.CallStackViewer;
import org.asteroidapp.util.ConsoleUI;
import org.asteroidapp.util.TestConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Queue;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


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


    /*
     *
     */
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

    private void consoleDemo() {

        log.log(Level.INFO, "console demo started");
        log.log(Level.INFO, "Hello team :)");

        while (!quitCondition) {
            ConsoleUI.getInstance().sendMessageToConsole(
                    "Type start to start," +
                            " help to show options, or quit to close application!");
            ConsoleUI.getInstance().sendMessageToConsole("Type testmode for entry to test mode");

            String response = ConsoleUI.getInstance().readLineFromConsole();
            if (response != null) {
                if (response.equals("start")) {
                    //startGame();
                    GameController.getInstance().setupGame();
                    GameController.getInstance().inGame();

                    quitCondition = true;
                } else if (response.equals("help")) {
                    ConsoleUI.getInstance().sendMessageToConsole("help --> help msg");
                    ConsoleUI.getInstance().sendMessageToConsole("start --> start and config game");
                    ConsoleUI.getInstance().sendMessageToConsole("quit --> close app");
                    ConsoleUI.getInstance().sendMessageToConsole("test1 --> run pre-defined config: test1");

                } else if (response.equals("quit")) {
                    quitCondition = true;

                    //delete/free resources
                } else if (response.equals("test1")) {
                    Queue<String> autoCommands = new ArrayDeque<String>();
                    autoCommands.add("1");
                    autoCommands.add("test1");
                    autoCommands.add("1");
                    ConsoleUI.getInstance().setAutoCommands(autoCommands);

                    GameController.getInstance().setupGame();
                    GameController.getInstance().inGame();

                    quitCondition = true;

                } else if (response.equals("test_move")) {
                    Queue<String> autoCommands = new ArrayDeque<String>();
                    autoCommands.add("1");
                    autoCommands.add("x");
                    autoCommands.add("1");
                    autoCommands.add("0");
                    ConsoleUI.getInstance().setAutoCommands(autoCommands);

                    GameController.getInstance().setupGame();
                    GameController.getInstance().inGame();

                    quitCondition = true;
                } else if (response.equals("test_bot")) {
                    Queue<String> autoCommands = new ArrayDeque<String>();
                    autoCommands.add("1");
                    autoCommands.add("x");
                    autoCommands.add("1");
                    autoCommands.add("5");
                    ConsoleUI.getInstance().setAutoCommands(autoCommands);

                    GameController.getInstance().setupGame();
                    GameController.getInstance().inGame();

                    quitCondition = true;
                } else if (response.equals("testmode")) {
                    testMode();
                    quitCondition = true;
                }
            } else if (response.equals("mapView")) {

            } else {
                //NOP
            }
        }

    }


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

    public void containerTest() {
        ResourceStorage storage = new ResourceStorage();
        storage.setAllCapacity(3);
        storage.pushResource(new FrozenWater());
        storage.pushMore(2, new Coal());
        storage.popResource(new Coal());
        storage.setAllCapacity(6);
        storage.pushMore(3, new Uran());
        storage.popMore(2, new Uran());
        storage.countOf(new Uran());
        storage.popMore(4, new Uran());
        storage.setAllCapacity(2);
        storage.pushResource(new FrozenWater());
    }

    public static void main(String[] args) {
        CallStackViewer.getInstance().methodStartsLogCall("___CALLSTACK:___");

        AppController app = new AppController();
        //app.consoleDemo();

        launch(args);

        CallStackViewer.getInstance().methodReturns();
    }
}