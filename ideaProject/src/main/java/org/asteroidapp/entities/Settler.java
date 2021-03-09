package org.asteroidapp.entities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.AsteroidZone;
import org.asteroidapp.GameController;
import org.asteroidapp.resources.*;
import org.asteroidapp.spaceobjects.Asteroid;
import org.asteroidapp.spaceobjects.Gate;
import org.asteroidapp.Player;
import org.asteroidapp.spaceobjects.Position;
import org.asteroidapp.spaceobjects.SteppableSpaceObject;
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
    private Map<Resource, Integer> resources = new HashMap<>();

    /**
     * Default constructor
     */
    public Settler(String name, SteppableSpaceObject initPlace, Player owner) {
        super(name, initPlace);
        resources.put(new Coal(), 0);
        resources.put(new Empty(), 0);
        resources.put(new FrozenWater(), 0);
        resources.put(new Iron(), 0);
        resources.put(new Uran(), 0);

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
        } else {
            log.log(Level.FATAL, "owner is null!");
        }
        log.log(Level.TRACE, "Settler created with an empty resource list");
    }

    /**
     * Overridden function for drill event.
     * It will thicken the layer of an asteroid if possible
     */
    @Override
    public boolean drill() {
        log.log(Level.INFO, "Settler tried to drill an object");
        if (onSpaceObject.drillLayer() >= 0) {
            log.log(Level.INFO, "Drill was successful");
            return true;
        } else {
            log.log(Level.INFO, "Drill was not successful");
            return false;
        }
    }

    /**
     * Overridden function for drill event.
     * It will kill and remove the specified objects.
     */
    @Override
    protected void die() {
        log.log(Level.INFO, "Die method of player {}'s settler called", this.owner.getName());
        AsteroidZone.getInstance().getSun().checkOut(this);
        onSpaceObject.checkOut(this);
        onSpaceObject = null;
        owner.removeSettler(this);
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
        log.log(Level.INFO, "Choose a Neighbour with the name of the neighbour");
        SteppableSpaceObject selected = null;
        String name = ConsoleUI.getInstance().readLineFromConsole();

        for (SteppableSpaceObject elem : neighbours)
            if (elem.getName().equals(name))
                selected = elem;

        return selected;
    }

    /**
     * Overridden function for notifyFlairEvent.
     * It notifies the entity about a flair event is happening
     */
    @Override
    public void notifyFlairEvent() {
        log.log(Level.INFO, "NotifyFlairEvent called");
        if (onSpaceObject.drillLayer() == 0 && onSpaceObject.mineResource().equals(new Empty())) {
            log.log(Level.INFO, "You were hidden in an asteroid during the sunflair so you survived");
        } else {
            log.log(Level.INFO, "You were not hidden in an asteroid during the sunflair so you died");
            die();
        }
    }

    /**
     * Overridden function for notifyFlairDanger.
     * It notifies the entity about a coming flair event
     */
    @Override
    public void notifyFlairDanger() {
        log.log(Level.INFO, "NotifyFlairDanger called");
        log.log(Level.INFO, "Be aware, a flair is coming in 2 rounds");
    }

    /**
     * Overridden function for notifyAsteroidExplosion.
     * notifies the entity about an asteroid explosion
     */
    @Override
    public void notifyAsteroidExplosion() {
        log.log(Level.INFO, "NotifyAsteroidExplosion called");
        die();
    }

    /**
     * Overridden function for doAction.
     * For easier handle the entities in GameController
     * Decision, and interaction wit user about what he/she wants to do
     */
    @Override
    public void doAction() {
        log.log(Level.INFO, "doAction called");
        //TODO decisionmaking, and communication with user

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
                    //TODO refactor, return with boolean
                    actionOK = mine();
                    break;
                case 3:
                    //TODO refactor, return with boolean
                    actionOK = createGate();
                    break;
                case 4:
                    //TODO refactor, return with boolean
                    buildGate();
                    break;
                case 5:
                    //TODO refactor, return with boolean
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
    private Player owner;

    /**
     * This method creates a Robot if it has enough resources
     */
    public boolean createBot() {

        if (resources.get(new Coal()) >= 1 && resources.get(new Iron()) >= 1 && resources.get(new Uran()) >= 1) {

            int numOfResource = resources.get(new Coal());
            resources.put(new Coal(), numOfResource - 1);
            numOfResource = resources.get(new Iron());
            resources.put(new Iron(), numOfResource - 1);
            numOfResource = resources.get(new Uran());
            resources.put(new Uran(), numOfResource - 1);

            AIRobot bot = new AIRobot("Robot", onSpaceObject);
            GameController.getInstance().addBot(bot);
            log.log(Level.INFO, "Bot created at {} asteroid", onSpaceObject.getName());
            return true;
        } else {
            log.log(Level.INFO, "Robot can not be created, not enough resources");
            return false;
        }
    }

    /**
     * A settler tries to mine an object and it gives back a resource or a massage why it is not possible
     *
     * @return boolean success
     */
    public boolean mine() {
        Resource res = onSpaceObject.mineResource();

        //mining is successful
        if (res != null) {
            addResource(res);
            log.log(Level.INFO, "Settler mined a(n) {}", res.getName());
            return true;
        } else {
            //unsuccessful mining
            log.log(Level.INFO, "Settler could not mine a resource because either it is empty or the layer is not drilled trough");
            return false;
        }
    }

    /**
     * It creates a gatePair if the settler has enough resources and
     * does not have any gate(s) in its inventory
     */
    public boolean createGate() {
        //TODO nullcheck on resources count, or init all resource with 0 count
        //TODO when createdGates has size() == 0 --> it's also OK
        if (createdGates == null && resources.get(new FrozenWater()) >= 1 && resources.get(new Iron()) >= 2 && resources.get(new Uran()) >= 1) {

            int numOfResource = resources.get(new FrozenWater());
            resources.put(new FrozenWater(), numOfResource - 1);
            numOfResource = resources.get(new Iron());
            resources.put(new Iron(), numOfResource - 2);
            numOfResource = resources.get(new Uran());
            resources.put(new Uran(), numOfResource - 1);

            Gate gate1 = new Gate(null);
            log.log(Level.INFO, "Gate1 created for {}", getName());
            Gate gate2 = new Gate(null);
            log.log(Level.INFO, "Gate2 created for {}", getName());

            createdGates.add(gate1);
            createdGates.add(gate2);
            gate1.setPair(gate2);
            gate2.setPair(gate1);

            return true;

        } else {
            log.log(Level.INFO, "Gate can not be created. You either have 1 or more gates in your inventory or you do not have enough resources to build them.");
            return false;
        }
    }

    /**
     * It selects a resource from the player and tries to put it in the core of the asteroid
     */
    public void deployResource() {
        log.log(Level.INFO, "DeployResource called");
        listResources();
        Resource resource = chooseResource();
        if(resources.get(resource).intValue() > 0) {
            log.log(Level.INFO, "The selected resource can be chosen");
            onSpaceObject.addResourceToCore(resource);
        } else {
            log.log(Level.INFO, "The selected resource can not be chosen");
        }
    }

    /**
     * If the player has gate(s) then it will build them on a certain position
     */
    public void buildGate() {
        log.log(Level.INFO, "BuildGate called");

        if (createdGates != null) {
            Gate gate = createdGates.remove(0);
            //TODO position for gate
            gate.setMyPosition(new Position());
            AsteroidZone.getInstance().addSpaceObject(gate);
            log.log(Level.INFO, "Gate placed at x = {}, y = {}", gate.getPosition().getX(), gate.getPosition().getY());
        } else {
            log.log(Level.INFO, "You do not have any gates to place");
        }
    }

    /**
     * It lists the resources and their amount that the player collected
     *
     * @return a map where the resources and their amount is stored
     */
    public Map<Resource, Integer> listResources() {
        log.log(Level.INFO, "ListResources called");
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            System.out.println(entry.getKey().getName() + ":" + entry.getValue());
        }
        return resources;
    }

    /**
     * From the collected Resources the player chooses one
     *
     * @return the chosen resource
     */
    public Resource chooseResource() {
        log.log(Level.INFO, "chooseResource called");
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
        int numOfResource = resources.get(resource).intValue();
        resources.put(resource, numOfResource + 1);
    }
}