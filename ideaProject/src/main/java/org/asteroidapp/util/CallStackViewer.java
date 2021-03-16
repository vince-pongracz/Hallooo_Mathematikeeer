package org.asteroidapp.util;

/**
 * Singleton class for handle intendation
 * <p>
 * Behaviour:
 * <p>
 * - At the start of method, put a callStack log (in GameController there are some example)
 * Something like this:
 * callStack.log(Level.TRACE, "{}_methodName_ called", CallStackViewer.getInstance().printIntend());
 * You have to make a callStack log (if doesn't exist), like this:
 * private static Logger callStack = LogManager.getLogger("callStack");
 * <p>
 * - At the end of a method: call CallStackViewer.getInstance().methodReturns();
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

    /**
     * Prints intendation
     *
     * @return String intendation
     */
    public String printIntend() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < intendWith; i++) {
            builder.append("   ");
        }
        intendWith++;

        return builder.toString();
    }

    /**
     * Handle intendations, pull them back
     */
    public void methodReturns() {
        intendWith--;
    }
}
