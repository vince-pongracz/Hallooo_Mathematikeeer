package org.asteroidapp.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class ConsoleUI {

    /**
     * Logger for ConsoleUI
     */
    private static Logger log = LogManager.getLogger(ConsoleUI.class.getSimpleName());

    private static ConsoleUI instance = null;

    private Scanner scanner = null;

    private Queue<String> autoCommands = null;
    private TestConfig testConfig = null;

    private ConsoleUI() {
        scanner = new Scanner(System.in);
    }


    public static ConsoleUI getInstance() {
        if (instance == null) {
            instance = new ConsoleUI();
        }
        return instance;
    }

    public void setAutoCommands(Queue<String> autoCommands) {
        this.autoCommands = autoCommands;
    }

    public void sendMessageToConsole(String message) {
        System.out.println(message);
    }

    public String readLineFromConsole() {

        if (autoCommands != null && !autoCommands.isEmpty()) {
            String command = autoCommands.remove();
            log.log(Level.INFO, "Used automatic command: " + command);

            //TODO meg nem megy.. :(
            if (command.equals("assert")) {
                testConfig.eval();
                System.exit(0);
            }

            sendMessageToConsole("------\n" + command);
            return command;
        }
        String ret = "";

        ret = scanner.nextLine();
        return ret;
    }



    public void addTestConfig(TestConfig test){
        testConfig = test;
    }

    public int readIntFromConsole() {

        if (autoCommands != null && !autoCommands.isEmpty()) {
            try {
                String command = autoCommands.remove();
                log.log(Level.INFO, "Used automatic command: " + command);
                sendMessageToConsole("------\n" + command);

                if (command.equals("assert")) {
                    testConfig.eval();
                    System.exit(0);
                }

                return Integer.parseInt(command);
            } catch (NumberFormatException e) {
                log.log(Level.WARN, "Invalid command queue element in autoCommands.");
            }
        }
        boolean valueIsOk = false;
        int returnValue = 0;
        while (!valueIsOk) {
            try {
                returnValue = Integer.parseInt(readLineFromConsole());
                valueIsOk = true;
            } catch (NumberFormatException e) {
                System.out.println("Try again, an error occurs");
            }
        }

        return returnValue;
    }

    public void sendOptionListToConsole(List<String> list) {
        if (list != null) {
            System.out.println("Choose an option:");
            int i = 0;
            for (var listElement : list) {
                System.out.println(i + " : " + listElement);
                i++;
            }
        } else {
            log.log(Level.INFO, "No message (list is null)");
        }
    }
}
