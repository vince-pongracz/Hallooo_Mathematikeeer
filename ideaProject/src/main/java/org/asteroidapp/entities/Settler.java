package org.asteroidapp.entities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AsteroidZone;
import org.asteroidapp.GameController;
import org.asteroidapp.resources.*;
import org.asteroidapp.spaceobjects.Gate;
import org.asteroidapp.Player;
import org.asteroidapp.spaceobjects.Position;
import org.asteroidapp.spaceobjects.SteppableSpaceObject;
import org.asteroidapp.util.CallStackViewer;
import org.asteroidapp.util.ConsoleUI;

import java.util.*;

/**
 * Settlers are Entities who can mine, create Robots and Gates
 * They also can manage their resources.
 */
public class Settler extends Entity {

    List<String> options = new ArrayList<>();

    /**
     * Logger for Settler class
     */
    private static final Logger log = LogManager.getLogger(Settler.class.getSimpleName());

    /**
     * It stores the name of the resource and how many does the player possess from that.
     */
    private ResourceStorage resources = new ResourceStorage();


    /**
     * Default constructor
     */
    public Settler(String name, SteppableSpaceObject initPlace, Player owner) {
        super(name, initPlace);

        log.log(Level.INFO, "Settler constructor called");
        CallStackViewer.getInstance().methodStartsLogCall("Settler constructor called");

        options.add("move");
        options.add("drill");
        options.add("mine");
        options.add("create gate");
        options.add("build gate");
        options.add("create robot");
        options.add("deploy resource");
        options.add("list neighbours");

        if (owner != null) {
            this.owner = owner;
            createdGates = new ArrayList<Gate>();     ////// INITIALIZED GATE INVENTORY
        } else {
            log.log(Level.FATAL, "owner is null!");
        }
        log.log(Level.TRACE, "Settler created with an empty resource list");

        //Fot the testing the create bot and portal functions
        resources.pushMore(2, new Coal());
        resources.pushMore(2, new Uran());
        resources.pushMore(1, new FrozenWater());
        resources.pushMore(3, new Iron());

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Overridden function for drill event.
     * It will thicken the layer of an asteroid if possible
     */
    @Override
    public boolean drill() {
        log.log(Level.INFO, "Drill called");
        CallStackViewer.getInstance().methodStartsLogCall("drill() called (Settler)");
        log.log(Level.INFO, "Settler tried to drill an object");

        int oldThickness = onSpaceObject.getLayerThickness();
        int newThickness = onSpaceObject.drillLayer();
        boolean ret = false;

        if (newThickness > 0 || (oldThickness == 1 && newThickness == 0)) {
            log.log(Level.INFO, "Drill was successful");
            ret = true;
        } else if (newThickness == 0) {
            log.log(Level.INFO, "NO more drill needed, it's ready, you can mine");
            ret = false;
        } else {
            log.log(Level.INFO, "Drill was not successful");
            ret = false;
        }

        CallStackViewer.getInstance().methodReturns();
        return ret;
    }

    /**
     * Overridden function for drill event.
     * It will kill and remove the specified objects.
     */
    @Override
    public void die() {
        log.log(Level.INFO, "Die method of player {}'s settler called", this.owner.getName());
        CallStackViewer.getInstance().methodStartsLogCall("die() called (Settler)");

        onSpaceObject.checkOut(this);
        onSpaceObject = null;
        owner.removeSettler(this);
        AsteroidZone.getInstance().getSun().checkOut(this);

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Overridden function for chooseNeighbour. It chooses a neighbour where the player can move to
     *
     * @param neighbours the list of neighbours from where the player can choose where to move to
     * @return the chosen neighbour
     */
    @Override
    protected SteppableSpaceObject chooseNeighbour(Set<SteppableSpaceObject> neighbours) {
        log.log(Level.INFO, "ChooseNeighbour called");
        CallStackViewer.getInstance().methodStartsLogCall("chooseNeighbour() called (Settler)");
        log.log(Level.INFO, "Choose a Neighbour with the name of the neighbour");

        if (neighbours != null) {

            //refactor this with a single ConsoleUI call.
            for (var item : neighbours) {
                ConsoleUI.getInstance().sendMessageToConsole(item.getName());
            }

            //TODO refactor: what's happening, when a wrong name is typed?
            SteppableSpaceObject selected = null;
            String name = ConsoleUI.getInstance().readLineFromConsole();

            for (SteppableSpaceObject element : neighbours) {
                if (element.getName().equals(name)) {
                    selected = element;

                    //returns here
                    CallStackViewer.getInstance().methodReturns();
                    return selected;
                }
            }
        } else {
            log.log(Level.WARN, "neighbours is null, cannot choose form empty neighbour list");
        }

        //or return here
        CallStackViewer.getInstance().methodReturns();
        return null;
    }

    /**
     * Overridden function for notifyFlairEvent.
     * It notifies the entity about a flair event is happening
     */
    @Override
    public void notifyFlairEvent() {
        log.log(Level.INFO, "notifyFlairEvent called");
        CallStackViewer.getInstance().methodStartsLogCall( "notifyFlairEvent() called (Settler)");

        //TODO refactor: one Asteroid can hide just one entity..
        if (onSpaceObject.getLayerThickness() == 0 && onSpaceObject.mineResource().equals(new Empty())) {
            log.log(Level.INFO, "You were hidden in an asteroid during the sunflair so you survived");
        } else {
            log.log(Level.INFO, "You were not hidden in an asteroid during the sunflair so you died");
            die();
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Overridden function for notifyFlairDanger.
     * It notifies the entity about a coming flair event
     */
    @Override
    public void notifyFlairDanger() {
        log.log(Level.INFO, "NotifyFlairDanger called");
        CallStackViewer.getInstance().methodStartsLogCall( "notifyFlairDanger() called (Settler)");

        log.log(Level.INFO, "Be aware, a flair is coming in 2 rounds");

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Overridden function for notifyAsteroidExplosion.
     * notifies the entity about an asteroid explosion
     */
    @Override
    public void notifyAsteroidExplosion() {
        log.log(Level.INFO, "NotifyAsteroidExplosion called");
        CallStackViewer.getInstance().methodStartsLogCall("notifyAsteroidExplosion() called (Settler)");

        //so you have to die
        die();
        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Overridden function for doAction.
     * For easier handle the entities in GameController
     * Decision, and interaction wit user about what he/she wants to do
     */
    @Override
    public void doAction() {
        log.log(Level.INFO, "doAction called");
        //TODO kell ide callStack log?

        //choose an action.
        boolean actionOK = false;
        while (!actionOK) {
            ConsoleUI.getInstance().sendOptionListToConsole(options);
            var answer = ConsoleUI.getInstance().readIntFromConsole();

            switch (answer) {
                case 0:
                    move();
                    actionOK = true;
                    break;
                case 1:
                    actionOK = drill();
                    break;
                case 2:
                    actionOK = mine();
                    break;
                case 3:
                    actionOK = createGate();
                    break;
                case 4:
                    //TODO refactor, return with boolean
                    buildGate();
                    break;
                case 5:
                    actionOK = createBot();
                    break;
                case 6:
                    //TODO refactor, return with boolean
                    deployResource();
                    break;
                case 7:
                    //always have to list the neighbours - no excuse
                    listMyNeighbours();
                    actionOK = true;
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * It stores the gates that the player created
     */
    private List<Gate> createdGates = null;

    /**
     * It stores the player where the settler belongs to
     */
    private Player owner = null;

    /**
     * This method creates a Robot if it has enough resources
     * @warn 2 return point
     */
    public boolean createBot() {
        log.log(Level.INFO, "CreateBot called");
        CallStackViewer.getInstance().methodStartsLogCall("createBot() called (Settler)");

        if (resources.countOf(new Coal()) >= 1 && resources.countOf(new Iron()) >= 1 && resources.countOf(new Uran()) >= 1) {

            resources.popResource(new Coal());
            resources.popResource(new Iron());
            resources.popResource(new Uran());

            AIRobot bot = new AIRobot("Robot", onSpaceObject);
            GameController.getInstance().addBot(bot);
            log.log(Level.INFO, "Bot created at {} asteroid", onSpaceObject.getName());

            CallStackViewer.getInstance().methodReturns();
            return true;

        } else {
            log.log(Level.INFO, "Robot can not be created, not enough resources");

            CallStackViewer.getInstance().methodReturns();
            return false;
        }
    }

    /**
     * A settler tries to mine an object and it gives back a resource or a massage why it is not possible
     *
     * @return boolean success
     */
    public boolean mine() {
        log.log(Level.INFO, "Mine called");
        CallStackViewer.getInstance().methodStartsLogCall("mine() called (Settler)");

        Resource res = onSpaceObject.mineResource();
        boolean mineSuccess = false;

        //mining is successful
        if (res != null) {
            if (!res.equals(new Empty())) {
                addResource(res);
                log.log(Level.INFO, "Settler mined a(n) {}", res.getName());
                mineSuccess = true;
            } else {
                //but can't mine "Empty", so it will be denied
                log.log(Level.INFO, "Settler could not mine a resource because it is empty");
                mineSuccess = false;
            }
        } else {
            //unsuccessful mining
            log.log(Level.INFO, "Settler could not mine a resource because the layer is not drilled trough");
            mineSuccess = false;
        }

        CallStackViewer.getInstance().methodReturns();
        return mineSuccess;
    }

    /**
     * It creates a gatePair if the settler has enough resources and
     * does not have any gate(s) in its inventory
     */
    public boolean createGate() {
        log.log(Level.INFO, "CreateGate called");
        CallStackViewer.getInstance().methodStartsLogCall("createGate() called (Settler)");

        boolean createSuccess = false;
        //TODO nullcheck on resources count, or init all resource with 0 count
        //TODO when createdGates has size() == 0 --> it's also OK
        if (createdGates.size() == 0 && resources.countOf(new FrozenWater()) >= 1 && resources.countOf(new Iron()) >= 2 && resources.countOf(new Uran()) >= 1) { ////CHANGED CREATEDGATES NULLCHECK  TO SIZE == 0

            resources.popResource(new FrozenWater());
            resources.popMore(2, new Iron());
            resources.popResource(new Uran());

            Gate gate1 = new Gate(new Position(400, 500));
            log.log(Level.INFO, "Gate1 created for {}", getName());
            Gate gate2 = new Gate(new Position(700, 700));
            log.log(Level.INFO, "Gate2 created for {}", getName());


            //TODO Gate class is not ready          This looks OK now -Abel
            createdGates.add(gate1);
            createdGates.add(gate2);
            gate1.setPair(gate2);
            gate2.setPair(gate1);

            createSuccess = true;
        } else {
            log.log(Level.INFO, "Gate can not be created. You either have 1 or more gates in your inventory or you do not have enough resources to build them.");
            createSuccess = false;
        }

        CallStackViewer.getInstance().methodReturns();
        return createSuccess;
    }

    /**
     * It selects a resource from the player and tries to put it in the core of the asteroid
     */
    public void deployResource() {
        log.log(Level.INFO, "DeployResource called");
        CallStackViewer.getInstance().methodStartsLogCall("deployResource() called (Settler)");

        listResources();
        var resource = chooseResource();

        if (resources.countOf(resource) > 0) {
            log.log(Level.INFO, "The selected resource can be chosen");
            onSpaceObject.addResourceToCore(resource);
        } else {
            log.log(Level.INFO, "The selected resource can not be chosen");
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * If the player has gate(s) then it will build them on a certain position
     */
    public void buildGate() {
        log.log(Level.INFO, "BuildGate called");
        CallStackViewer.getInstance().methodStartsLogCall("buildGate() called (Settler)");

        if (createdGates.size() != 0) {     /////////////// SIZE CHECK INSTEAD OF NULL CHECK
            Gate gate = createdGates.remove(0);
            //TODO position for gate
            gate.setMyPosition(this.onSpaceObject.getPosition());  ////////ALTERED EMPTY POSITION TO SETTLERS (ASTEROIDS) POSITION FOR TESTING, SHOULD BE SOMEWHERE AROUND THE SETTLER
            AsteroidZone.getInstance().addSpaceObject(gate);
            log.log(Level.INFO, "Gate placed at x = {}, y = {}", gate.getPosition().getX(), gate.getPosition().getY());
        } else {
            log.log(Level.INFO, "You do not have any gates to place");
        }

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * It lists the resources and their amount that the player collected
     *
     * @return a map where the resources and their amount is stored
     */
    public List<Resource> listResources() {
        log.log(Level.INFO, "ListResources called");
        CallStackViewer.getInstance().methodStartsLogCall("listResources() called (Settler)");

        resources.getResourceList().forEach((temp) -> System.out.println(temp.getName()));

        CallStackViewer.getInstance().methodReturns();
        return resources.getResourceList();
    }

    /**
     * From the collected Resources the player chooses one
     *
     * @return the chosen resource
     */
    public Resource chooseResource() {
        log.log(Level.INFO, "chooseResource called");
        CallStackViewer.getInstance().methodStartsLogCall("chooseResource() called (Settler)");
        log.log(Level.INFO, "Write the number of the resource you would like to choose : 1 - Coal, 2 - FrozenWater, 3 - Iron, 4 - Uran");

        int resourceNum = ConsoleUI.getInstance().readIntFromConsole();
        Resource resource = null;

        switch (resourceNum) {
            case (1):
                resource = new Coal();
                break;
            case (2):
                resource = new FrozenWater();
                break;
            case (3):
                resource = new Iron();
                break;
            case (4):
                resource = new Uran();
                break;
        }
        log.log(Level.TRACE, "Choosen resource: {}", resource.getName());

        CallStackViewer.getInstance().methodReturns();
        return resource;
    }

    /**
     * It ads a resource to the players resources
     *
     * @param resource the resource that will be added
     */
    private void addResource(Resource resource) {
        log.log(Level.INFO, "addResource called");

        //TODO this can return null.. :/
        if (resource != null) {
            resources.pushResource(resource);
            log.log(Level.INFO, "{} added to settler successfully", resource.getName());
        } else {
            log.log(Level.INFO, "Nothing can be added");
        }
    }
}