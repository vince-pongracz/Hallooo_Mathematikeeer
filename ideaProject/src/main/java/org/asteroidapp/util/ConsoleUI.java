package org.asteroidapp.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private static Logger log = LogManager.getLogger(ConsoleUI.class.toString());

    private static ConsoleUI instance = null;

    private Scanner scanner = null;

    private ConsoleUI() {
        scanner = new Scanner(System.in);
    }


    public static ConsoleUI getInstance() {
        if (instance == null) {
            instance = new ConsoleUI();
        }
        return instance;
    }

    public void sendMessageToConsole(String message) {
        System.out.println(message);
    }

    public String readLineFromConsole() {
        String ret = "";

        ret = scanner.nextLine();
        return ret;
    }

    public int readIntFromConsole() {
        boolean valueIsOk = false;
        int returnValue = 0;
        while (!valueIsOk) {
            try {
                returnValue = Integer.parseInt(readLineFromConsole());
                valueIsOk = true;
            } catch (NumberFormatException e) {
                System.out.println("Try again, error occours");
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
