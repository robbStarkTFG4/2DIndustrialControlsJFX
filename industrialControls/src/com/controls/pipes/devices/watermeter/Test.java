/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.devices.watermeter;

import com.controls.util.RectDirection;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 *
 * @author marcoisaac
 */
public class Test extends Application {

    private static int noOfNodes = 0;
    private boolean llena = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        WaterMeter meter = new WaterMeter(RectDirection.DOWN_TOP, 200, 300, 150, 180, 40, 1);

        Button btn = new Button("no flujo");
        btn.setOnMouseClicked(d -> {
            meter.fadeFlowSign();
        });

        Button btn2 = new Button("flujo");
        btn2.setOnMouseClicked(d -> {
            meter.fadeFlowInSign();
        });

        FlowPane pane = new FlowPane(Orientation.HORIZONTAL, btn, btn2);
        pane.setTranslateX(0);
        pane.setTranslateY(0);

        root.getChildren().addAll(meter, pane);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        calcNoOfNodes(scene.getRoot());
        System.out.println("number of nodes: " + noOfNodes);

    }

    public static void main(String[] args) {
        launch(args);
    }

    // ******************** Misc **********************************************
    private static void calcNoOfNodes(Node node) {
        if (node instanceof Parent) {
            if (((Parent) node).getChildrenUnmodifiable().size() != 0) {
                ObservableList<Node> tempChildren = ((Parent) node).getChildrenUnmodifiable();
                noOfNodes += tempChildren.size();
                tempChildren.stream().forEach((n) -> {
                    calcNoOfNodes(n);
                });
            }
        }
    }
}
