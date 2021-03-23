package org.asteroidapp.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Singleton class for handle indentation
 * <p>
 * Behaviour:
 * <p>
 * - At the start of method, put a callStack log (in GameController there are some example)
 * Something like this:
 * CallStackViewer.getInstance().methodStartsLogCall(String logMessage);
 *
 * - If you want a logMessage inside the method, and it's NOT a new method call, you can call this:
 * CallStackViewer.getInstance().innerMethodLogCall(String logMessage);
 *
 * - At the end of a method:
 * call CallStackViewer.getInstance().methodReturns();
 * <p>
 * This will log the method calls in a callStack_date_.log file into ../callStacks/
 * This can't print messages to console, but when it's necessary, we can create a new Appender to the console at log4j2.xml
 */
public class CallStackViewer {
    /**
     * Intendation
     */
    private int intendWith;

    /**
     * Default constructor
     */
    private CallStackViewer() {
    }

    /**
     * For singleton behaviour
     */
    private static CallStackViewer instance = null;

    /**
     * For singleton behaviour
     *
     * @return CallStackViewer
     */
    public static CallStackViewer getInstance() {
        if (instance == null) {
            instance = new CallStackViewer();
        }
        return instance;
    }

    private static Logger callStackLog = LogManager.getLogger("callStack");

    /**
     * Create and format a callStack log message at the START of the method
     * @param logMessage
     */
    public void methodStartsLogCall(String logMessage){
        callStackLog.log(Level.TRACE, "{}{}", printIndentation(), logMessage);
    }

    /**
     * Create and format a callStack log message inside a method
     * @note no indentation
     *
     * @param logMessage
     */
    public void innerMethodCall(String logMessage){
        this.methodStartsLogCall(logMessage);
        this.methodReturns();
    }

    /**
     * Prints indentation
     *
     * @return String indentation
     */
    private String printIndentation() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < intendWith; i++) {
            builder.append("   ");
        }
        intendWith++;

        return builder.toString();
    }

    /**
     * Handle indentations, pull them back
     */
    public void methodReturns() {
        intendWith--;
    }
}