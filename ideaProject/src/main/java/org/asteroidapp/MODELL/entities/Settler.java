package org.asteroidapp.MODELL.entities;

import javafx.geometry.Pos;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asteroidapp.CONTROLLER.AsteroidZone;
import org.asteroidapp.CONTROLLER.GameController;
import org.asteroidapp.MODELL.interfaces.Drill;
import org.asteroidapp.MODELL.EventType;
import org.asteroidapp.MODELL.interfaces.Mine;
import org.asteroidapp.MODELL.resources.*;
import org.asteroidapp.MODELL.spaceobjects.Gate;
import org.asteroidapp.CONTROLLER.Player;
import org.asteroidapp.MODELL.spaceobjects.Position;
import org.asteroidapp.MODELL.spaceobjects.SteppableSpaceObject;
import org.asteroidapp.VIEW.drawables.GateGraphic;
import org.asteroidapp.VIEW.drawables.SettlerGraphic;
import org.asteroidapp.util.ActionResponse;
import org.asteroidapp.util.CallStackViewer;

import java.util.*;

/**
 * Settlers are Entities who can mine, create Robots and Gates
 * They also can manage their resources.
 */
public class Settler extends Entity implements Drill, Mine {

    /**
     * Logger for Settler class
     */
    private static final Logger log = LogManager.getLogger(Settler.class.getSimpleName());

    /**
     * It stores the name of the resource and how many does the player possess from that.
     */
    private ResourceStorage resources;
    public static int settlerCapacity = 10;

    /**
     * Default constructor
     */
    public Settler(String name, SteppableSpaceObject initPlace, Player owner) {
        super(name, initPlace);

        log.log(Level.INFO, "Settler constructor called");
        CallStackViewer.getInstance().methodStartsLogCall("Settler constructor called");

        if (owner != null) {
            this.owner = owner;
        } else {
            log.log(Level.FATAL, "owner is null!");
        }
        log.log(Level.TRACE, "Settler created with an empty resource list");

        resources = new ResourceStorage();
        resources.setAllCapacity(settlerCapacity);

        //Fot the testing the create bot and portal functions
        resources.pushMore(2, new Coal());
        resources.pushMore(2, new Uran());
        resources.pushMore(2, new FrozenWater());
        resources.pushMore(3, new Iron());

        this.checkIn(new SettlerGraphic(this));

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

        int oldThickness = onAsteroid.getLayerThickness();

        onAsteroid.drillLayer();
        boolean ret = false;

        if (oldThickness >= 1) {
            log.log(Level.INFO, "Drill was successful");
            GameController.response.setMessage("Drilled!");
            ret = true;
        } else if (onAsteroid.getLayerThickness() == 0) {
            log.log(Level.INFO, "SpaceObject is already drilled: mineable");
            GameController.response.setMessage("Already drilled through!");
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

        onAsteroid.checkOut(this);
        onAsteroid = null;
        AsteroidZone.getInstance().getSun().checkOut(this);
        owner.removeSettler(this);

        //if this settler is the owner's last
        //kill the owner, because he won't play anymore (all his/her settlers are died)
        if (!owner.getIterOnMySettlers().hasNext()) {
            owner.killPlayer();
        }

        this.signalizeUpdate(EventType.DELETE);

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Overridden function for notifyFlairEvent.
     * It notifies the entity about a flair event is happening
     */
    private void notifyFlairEvent() {
        log.log(Level.INFO, "notifyFlairEvent called");
        CallStackViewer.getInstance().methodStartsLogCall("notifyFlairEvent() called (Settler)");

        boolean settlerIsFarFromSun = onAsteroid.getPosition().distanceFrom(AsteroidZone.getInstance().getSun().getPosition()) >= AsteroidZone.defOfCloseToSun;

        if (onAsteroid.getLayerThickness() == 0 && onAsteroid.mineResource().equals(new Empty())
                || settlerIsFarFromSun
                || this.onAsteroid.equals(AsteroidZone.getInstance().findHome())) {
            log.log(Level.INFO, "You were hidden in an asteroid during the sunflair or you were far away from the sun, so you survived");
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
    private void notifyFlairDanger() {
        log.log(Level.INFO, "NotifyFlairDanger called");
        CallStackViewer.getInstance().methodStartsLogCall("notifyFlairDanger() called (Settler)");

        //TODO send popup?
        log.log(Level.INFO, "Be aware, a flair is coming in 2 rounds");

        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * Do nothing in a round
     */
    public void waitingSettler() {
        log.log(Level.INFO, "Do nothing in this round with settler: {}", this.getName());
        CallStackViewer.getInstance().methodStartsLogCall("waitingSettler() called (Settler)");
        CallStackViewer.getInstance().methodReturns();
    }

    /**
     * It stores the gates that the player created
     */
    private List<Gate> createdGates = new ArrayList<>();

    /**
     * It stores the player where the settler belongs to
     */
    private Player owner = null;

    /**
     * This method creates a Robot if it has enough resources
     *
     * @warn 2 return point
     */
    public boolean createBot() {
        log.log(Level.INFO, "CreateBot called");
        CallStackViewer.getInstance().methodStartsLogCall("createBot() called (Settler)");

        if (resources.countOf(new Coal()) >= 1 && resources.countOf(new Iron()) >= 1 && resources.countOf(new Uran()) >= 1) {

            resources.popResource(new Coal());
            resources.popResource(new Iron());
            resources.popResource(new Uran());

            AIRobot bot = new AIRobot("Robot", onAsteroid);

            GameController.getInstance().addBot(bot);
            log.log(Level.INFO, "Bot created at {} asteroid", onAsteroid.getName());

            this.signalizeUpdate(EventType.REFRESH);

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
    @Override
    public boolean mine() {
        log.log(Level.INFO, "Mine called");
        CallStackViewer.getInstance().methodStartsLogCall("mine() called (Settler)");

        Resource res = onAsteroid.mineResource();
        boolean mineSuccess = false;

        //mining is successful
        if (res != null) {
            //push res when: res is not Empty AND settler has enough place to take it.
            if (!res.equals(new Empty()) && this.resources.pushResource(res)) {
                log.log(Level.INFO, "Settler mined a(n) {}", res.getName());
                GameController.response.setMessage(res.getName() + " mined");
                mineSuccess = true;
            } else {
                //when res is Empty or storage is full
                log.log(Level.INFO, "Settler could not mine a resource because it is empty, or storage is full");
                onAsteroid.addResourceToCore(res);
                mineSuccess = false;
            }
        } else {
            //unsuccessful mining
            log.log(Level.INFO, "Settler could not mine a resource because the layer is not drilled trough");
            mineSuccess = false;
        }

        CallStackViewer.getInstance().methodReturns();
        this.signalizeUpdate(EventType.REFRESH);
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
        if (createdGates.size() <= 1 && resources.countOf(new FrozenWater()) >= 1 && resources.countOf(new Iron()) >= 2 && resources.countOf(new Uran()) >= 1) { ////CHANGED CREATEDGATES NULLCHECK  TO SIZE == 0

            resources.popResource(new FrozenWater());
            resources.popMore(2, new Iron());
            resources.popResource(new Uran());

            Gate gate1 = new Gate();
            log.log(Level.INFO, "Gate1 created for {}", getName());
            Gate gate2 = new Gate();
            log.log(Level.INFO, "Gate2 created for {}", getName());

            createdGates.add(gate1);
            createdGates.add(gate2);
            CallStackViewer.getInstance().innerMethodCall("The 2 gates were added to the player");
            gate1.setPair(gate2);
            gate2.setPair(gate1);

            createSuccess = true;
        } else {
            log.log(Level.INFO, "Gate can not be created. You either have 1 or more gates in your inventory or you do not have enough resources to build them.");
            createSuccess = false;
        }

        CallStackViewer.getInstance().methodReturns();
        this.signalizeUpdate(EventType.REFRESH);
        return createSuccess;
    }

    /**
     * It selects a resource from the player and tries to put it in the core of the asteroid
     */
    public boolean deployResource(String resource) {
        log.log(Level.INFO, "DeployResource called");
        CallStackViewer.getInstance().methodStartsLogCall("deployResource() called (Settler)");

        boolean deploySuccess = false;
        var resourceIterator = resources.getResourceList().iterator();
        while (resourceIterator.hasNext()) {
            var tmp = resourceIterator.next();
            if (tmp.getName().equals(resource)) {
                if (onAsteroid.addResourceToCore(resources.popResource(tmp))) {
                    log.log(Level.INFO, "The selected resource can be chosen");
                    tmp.isRadioActive(onAsteroid.getPosition());
                    deploySuccess = false;
                    continue;
                } else {
                    resources.pushResource(tmp);
                    deploySuccess = true;
                    continue;
                }
            }
        }
        log.log(Level.INFO, "The selected resource can not be chosen");
        CallStackViewer.getInstance().methodReturns();
        this.signalizeUpdate(EventType.REFRESH);
        return deploySuccess;
    }

    /**
     * If the player has gate(s) then it will build them on a certain position
     */
    public boolean buildGate() {
        boolean success = false;
        log.log(Level.INFO, "BuildGate called");
        CallStackViewer.getInstance().methodStartsLogCall("buildGate() called (Settler)");

        if (createdGates.size() != 0) {     /////////////// SIZE CHECK INSTEAD OF NULL CHECK
            Gate gate = createdGates.remove(0);

            gate.setMyAsteroid(this.onAsteroid);
            onAsteroid.checkIn(gate);

            AsteroidZone.getInstance().addSpaceObject(gate);
            gate.checkIn(new GateGraphic(gate));
            gate.signalizeUpdate(EventType.REFRESH);
            success = true;
        } else {
            log.log(Level.INFO, "You do not have any gates to place");
            success = false;
        }

        CallStackViewer.getInstance().methodReturns();
        return success;
    }

    /**
     * It lists the resources and their amount that the player collected
     *
     * @return a map where the resources and their amount is stored
     */
    public List<Resource> listResources() {
        log.log(Level.INFO, "ListResources called");
        CallStackViewer.getInstance().methodStartsLogCall("listResources() called (Settler)");

        //ConsoleUI.getInstance().sendMessageToConsole(resources.toString());

        CallStackViewer.getInstance().methodReturns();
        return resources.getResourceList();
    }

    public final ResourceStorage getStorage() {
        return resources;
    }

    @Override
    public void notify(EventType eventType) {
        switch (eventType) {
            case EXPLOSION -> die();
            case FLAIREVENT -> notifyFlairEvent();
            case FLAIRWARN -> notifyFlairDanger();
        }
    }

    public int getGateNum() {
        return createdGates.size();
    }
}

