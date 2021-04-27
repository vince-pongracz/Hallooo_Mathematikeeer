package org.asteroidapp.CONTROLLER;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.util.ActionResponse;
import org.asteroidapp.util.InitMessage;


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

    private ActionResponse initActionResponse(ActionResponse response) {
        response = null;
        response = new ActionResponse();
        return response;
    }

    public ActionResponse initGame(InitMessage initDescription) {

        GameController.response = initActionResponse(GameController.response);

        if (initDescription.check()) {
            GameController.getInstance().setupGame(initDescription);
            GameController.response.setMessage("Init successful").setSuccess(true);
        } else {
            GameController.response.setSuccess(false).setMessage("Unsuccessful init, try it again");
        }
        return GameController.response;
    }

    public ActionResponse sendCommandToModell(JsonObject command) {

        GameController.response = initActionResponse(GameController.response);
        var actualSettler = GameController.getInstance().getActualPlayer().getActualSettler();

        if (command.has("command")) {

            var str = command.get("command").getAsString();

            if (str.equals("move") && command.has("target")) {
                var target = command.get("target").getAsString();
                var targetSteppable = AsteroidZone.getInstance().getObjectByName(target);
                if (targetSteppable != null && actualSettler.listMyNeighbours().contains(targetSteppable)) {
                    actualSettler.move(targetSteppable);
                    GameController.response.setSuccess(true).addRefreshObjects(actualSettler.getName());
                }
            } else if (str.equals("deploy") && command.has("resource")) {
                var res = command.get("resource").getAsString();
                GameController.response.setSuccess(actualSettler.deployResource(res));

            } else if (str.equals("drill")) {
                GameController.response.setSuccess(actualSettler.drill());

            } else if (str.equals("mine")) {
                GameController.response.setSuccess(actualSettler.mine());

            } else if (str.equals("next")) {
                actualSettler.waitingSettler();
                GameController.response.setSuccess(true);

            } else if (str.equals("buildGate")) {
                GameController.response.setSuccess(actualSettler.buildGate());

            } else if (str.equals("createGate")) {
                GameController.response.setSuccess(actualSettler.createGate());

            } else if (str.equals("createBot")) {
                GameController.response.setSuccess(actualSettler.createBot());

            }

            //optional -- for console
            else if (str.equals("neighbours")) {
                var neighbours = actualSettler.listMyNeighbours();
            } else if (str.equals("resources")) {
                var resources = actualSettler.listResources();
            }
        }

        if (GameController.response.isSuccessful()) {
            GameController.getInstance().getActualPlayer().nextSettler();
        }
        return GameController.response.setDefaultMessage();
        //success?
        //state
        //refresh game state

    }
}
