/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.devices.pump1;

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

        Pump1 meter = new Pump1(RectDirection.DOWN_TOP, 300, 300, 50, 50, 40, 1);

        Button btn = new Button("start pump");
        btn.setOnMouseClicked(d -> {
            meter.startPump();
        });

        Button btn2 = new Button("stop pump");
        btn2.setOnMouseClicked(d -> {
            meter.stopPump();
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
