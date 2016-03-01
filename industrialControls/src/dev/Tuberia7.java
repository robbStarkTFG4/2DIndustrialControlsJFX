/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.stage.Stage;

/**
 *
 * @author marcoisaac
 */
public class Tuberia7 extends Application {

    private static int noOfNodes = 0;
    private static final double START_X = 100;
    private static final double START_Y = 170;
    private static final double AV = 1.15;
    private static final double EDGE = AV - 1;
    private static final double W = 120; //witdh
    private static final double H = 100; //height
    private static final double T = 30; // pipe diameter

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        if (!(H >= 3 * T)) {
            System.out.println("error 1");
            System.exit(0);
        }

        if (!(W >= 3 * T)) {
            System.out.println("error 2");
            System.exit(0);
        }

        Path neck1 = new Path();
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (AV * T), START_Y));

        double bX = START_X + (AV * T) - (EDGE * T);
        double bY = START_Y - (H / 8);
        double aX = START_X + (EDGE * T);

        neck1.getElements().add(new LineTo(bX, bY));//B
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(aX, bY));//A
        neck1.getElements().add(new LineTo(bX, bY));

        Path body = new Path();
        body.getElements().add(new MoveTo(bX, bY));
        body.getElements().add(new LineTo(aX, bY));

        double restanteY = H - (2 * H / 8);
        double c = bY - restanteY;
        body.getElements().add(new LineTo(aX, c));//C
        body.getElements().add(new MoveTo(bX, bY));
        body.getElements().add(new LineTo(bX, c));//D
        body.getElements().add(new LineTo(aX, c));//C

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(aX, c));//C
        neck2.getElements().add(new LineTo(bX, c));//D
        neck2.getElements().add(new LineTo(bX + (EDGE * T), c - (H / 8)));//D1
        neck2.getElements().add(new MoveTo(aX, c));//C
        neck2.getElements().add(new LineTo(aX - (EDGE * T), c - (H / 8)));//C1
        neck2.getElements().add(new LineTo(bX + (EDGE * T), c - (H / 8)));//D1

        root.getChildren().addAll(neck1, body, neck2);

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
