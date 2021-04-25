package org.asteroidapp;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CommandInterpreter {
    private static Logger log = LogManager.getLogger(CommandInterpreter.class.getSimpleName());
    private static CommandInterpreter instance = null;

    private CommandInterpreter() {
    }

    public static CommandInterpreter getInstance() {
        if (instance == null) {
            instance = new CommandInterpreter();
        }
        return instance;
    }

    public boolean sendMessageToModell(JsonObject command) {
        boolean success = false;

        var actualSettler = GameController.getInstance().getActualPlayer().getActualSettler();

        if (command.has("command")) {

            var str = command.get("command").getAsString();

            if (str.equals("move") && command.has("target")) {
                var target = command.get("target").getAsString();
                var targetSteppable = AsteroidZone.getInstance().getObjectByName(target);
                if (targetSteppable != null && actualSettler.listMyNeighbours().contains(targetSteppable)) {
                    actualSettler.move(targetSteppable);
                    success = true;
                }
            } else if (str.equals("deploy") && command.has("resource")) {
                var res = command.get("resource").getAsString();
                success = actualSettler.deployResource(res);
            } else if (str.equals("drill")) {
                success = actualSettler.drill();
            } else if (str.equals("mine")) {
                success = actualSettler.mine();
            } else if (str.equals("next")) {
                actualSettler.waitingSettler();
                success = true;
            } else if (str.equals("buildGate")) {
                success = actualSettler.buildGate();
            } else if (str.equals("createGate")) {
                success = actualSettler.createGate();
            } else if (str.equals("createBot")) {
                success = actualSettler.createBot();
            }

            //optional -- for console
            else if (str.equals("neighbours")) {
                var neighbours = actualSettler.listMyNeighbours();
            } else if (str.equals("resources")) {
                var resources = actualSettler.listResources();
            }
        }

        if (success) {
            GameController.getInstance().getActualPlayer().nextSettler();
        }
        return success;
        //success?
        //state
        //refresh game state
        
    }
}
