/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controls.pipes.curves;

import com.controls.util.CurveDirection;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author marcoisaac
 */
public class Test extends Application {

    private static int noOfNodes = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        Curve curve = new Curve(null, CurveDirection.TOP_LEFT, 200, 200, 150, 120, 35, 1.15);
        Circle circle = new Circle(curve.getEndPoint().getX(), curve.getEndPoint().getY(), 7, Color.BROWN);
        root.getChildren().addAll(curve, circle);
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
