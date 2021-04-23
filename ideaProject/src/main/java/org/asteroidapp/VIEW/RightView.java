package org.asteroidapp.VIEW;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.asteroidapp.entities.Settler;
import org.asteroidapp.resources.*;

public class RightView {
    VBox vbox = new VBox();
    Label ironLabel;
    Label coalLabel;
    Label frozenWaterLabel;
    Label uranLabel;
    Label infoLabel ;

    public RightView(){
        Button createGateButton = new Button(" Create gate     ");
        Button buildGateButton = new Button(" Build Gate     ");
        Button createRobotButton = new Button(" Create Robot   ");
        Button deployResourceButton = new Button(" Deploy Resource ");
        Button drillButton = new Button(" Drill           ");
        Button mineButton = new Button(" Mine            ");
        Button moveButton = new Button(" Move            ");

        vbox.getChildren().add(moveButton);
        vbox.getChildren().add(drillButton);
        vbox.getChildren().add(mineButton);
        vbox.getChildren().add(createGateButton);
        vbox.getChildren().add(buildGateButton);
        vbox.getChildren().add(createRobotButton);
        vbox.getChildren().add(deployResourceButton);

        ironLabel = new Label(" Iron: 0" );
        coalLabel = new Label(" Coal: 0");
        frozenWaterLabel = new Label(" Frozen water: 0");
        uranLabel = new Label(" Uran: 0");
        infoLabel = new Label("-------------------------------\n   Aron's Settler2's round \n   Sunflair is coming in 3 rounds");

        vbox.getChildren().add(ironLabel);
        vbox.getChildren().add(coalLabel);
        vbox.getChildren().add(uranLabel);
        vbox.getChildren().add(frozenWaterLabel);
        vbox.getChildren().add(infoLabel);
    }

    public VBox getVBox(){
        return vbox;
    }

    public void refreshRightView( Settler settler, int sunflair) {
        if (settler != null) {
            ResourceStorage storage = settler.getStorage();
            ironLabel = new Label(" Iron: " + storage.countOf(new Iron()));
            coalLabel = new Label(" Coal: " + storage.countOf(new Iron()));
            frozenWaterLabel = new Label(" Frozen water: " + storage.countOf(new Iron()));
            uranLabel = new Label(" Uran: " + storage.countOf(new Iron()));
            infoLabel = new Label(settler.getOwnerName() + "'s " + settler.getName() + "'s round\n Sunflair is coming in " + sunflair + " rounds");
        } else {
            System.out.println("Settler was null");
        }
    }
}
