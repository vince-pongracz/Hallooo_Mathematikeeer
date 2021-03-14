package org.asteroidapp.util;

import java.io.*;
import java.nio.file.OpenOption;

public class CallStackViewer {
    private int intendWith;

    private CallStackViewer() {
    }

    private static CallStackViewer instance = null;

    public static CallStackViewer getInstance() {
        if (instance == null) {
            instance = new CallStackViewer();
        }
        return instance;
    }

    public String printIntend(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < intendWith; i++) {
            builder.append("   ");
        }
        intendWith++;
        return builder.toString();
    }

    public void methodReturns() {
        intendWith--;
    }
}
