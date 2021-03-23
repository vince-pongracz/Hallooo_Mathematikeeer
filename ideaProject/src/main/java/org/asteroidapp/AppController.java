package org.asteroidapp;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Queue;


//extends Application
public class AppController {

    //JavaFX demo
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
                    //TODO wipe game data to probable restart, or rerun with current config --- solve this

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
                } else if (response.equals("test_move")) {
                    Queue<String> autoCommands= new ArrayDeque<String>();
                    autoCommands.add("1");
                    autoCommands.add("x");
                    autoCommands.add("1");
                    autoCommands.add("0");
                    ConsoleUI.getInstance().setAutoCommands(autoCommands);

                    GameController.getInstance().setupGame();
                    GameController.getInstance().inGame();
                }
                else if (response.equals("test_bot")) {
                    Queue<String> autoCommands= new ArrayDeque<String>();
                    autoCommands.add("1");
                    autoCommands.add("x");
                    autoCommands.add("1");
                    autoCommands.add("5");
                    ConsoleUI.getInstance().setAutoCommands(autoCommands);

                    GameController.getInstance().setupGame();
                    GameController.getInstance().inGame();
                } else if (response.equals("testmode")) {
                    testMode();
                    quitCondition = true;
                } else {
                    //NOP
                }
            }

        }
    }

    public void testMode() {
        TestConfig config = new TestConfig();
        try {
            ConsoleUI.getInstance().sendMessageToConsole("Filename of testconfig:  (testconfigs/...)");
            String filename = ConsoleUI.getInstance().readLineFromConsole();
            Gson json = new Gson();
            config = json.fromJson(new JsonReader(new FileReader(filename)), TestConfig.class);
            if (config.checkConfig()) {
                ConsoleUI.getInstance().sendMessageToConsole("config is OK");
            } else {
                log.log(Level.ERROR, "Bad config");
                System.err.println("Wrong config");
                return;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Queue<String> autoCommands = config.setConfigIntoComponents();
        ConsoleUI.getInstance().setAutoCommands(autoCommands);

        GameController.getInstance().setupGame();
        GameController.getInstance().inGame();
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
        app.consoleDemo();

        CallStackViewer.getInstance().methodReturns();
    }
}