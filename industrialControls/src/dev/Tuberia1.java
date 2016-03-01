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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.stage.Stage;

/**
 *
 * @author marcoisaac
 */
public class Tuberia1 extends Application {

    private static int noOfNodes = 0;
    private static final double START_X = 100;
    private static final double START_Y = 100;
    private static final double AV = 1.15;
    private static final double EDGE = AV - 1;
    private static final double W = 180; //witdh
    private static final double H = 160; //height
    private static final double T = 40; // pipe diameter

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
        neck1.getElements().add(new LineTo(START_X, START_Y + (AV * T)));
        neck1.getElements().add(new QuadCurveTo(START_X + (H / 8) / 2, START_Y + (AV * T) - (EDGE * T) / 4, START_X + (H / 8), START_Y + (AV * T) - (EDGE * T) / 2));
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new QuadCurveTo(START_X + (H / 8) / 2, START_Y + (EDGE * T) / 4, START_X + (H / 8), START_Y + (EDGE * T) / 2));
        neck1.getElements().add(new LineTo(START_X + (H / 8), START_Y + (AV * T) - (EDGE * T) / 2));
        neck1.setFill(Color.RED);

        double restanteX = W - (H / 8);
        double d = 0.60 * restanteX;
        double a = 0.40 * restanteX;
        double m = d * 0.9;

        Path body = new Path();
        body.getElements().add(new MoveTo(START_X + (H / 8), START_Y + (EDGE * T) / 2));// upNeckPoint
        body.getElements().add(new LineTo(START_X + (H / 8), START_Y + (AV * T) - (EDGE * T) / 2)); //bottom neckPoint
        body.getElements().add(new LineTo(START_X + (H / 8) + m, START_Y + (AV * T) - (EDGE * T) / 2));

        double mid = ((START_Y + (EDGE * T) / 2) + (2 * T)) - (START_Y + (AV * T) - (EDGE * T) / 2);

        body.getElements().add(new QuadCurveTo(START_X + (H / 8) + d + a - T, (START_Y + (AV * T) - (EDGE * T) / 2) + (mid / 6), START_X + (H / 8) + d + a - T, (START_Y + (EDGE * T) / 2) + (2.4 * T)));

        double alpha = (START_Y + (EDGE * T) / 2) + (2.4 * T);
        double freeLeft = H - (alpha - START_Y - (H / 8));
        double bottomNeckXLeft = START_X + (H / 8) + d + a - T;
        double bottomNeckYLeft = (START_Y + (EDGE * T) / 2) + (2.4 * T) + freeLeft;

        body.getElements().add(new LineTo(bottomNeckXLeft, bottomNeckYLeft));//

        double teta = (START_Y + (EDGE * T) / 2) + (2 * T);
        double freeRight = H - (teta - START_Y - (H / 8));
        double bottomNeckXRight = START_X + (H / 8) + d + a;
        double bottomNeckYRight = (START_Y + (EDGE * T) / 2) + (2 * T) + freeRight;

        body.getElements().add(new LineTo(bottomNeckXRight, bottomNeckYRight));
        body.getElements().add(new LineTo(START_X + (H / 8) + d + a, (START_Y + (EDGE * T) / 2) + (2 * T)));

        double midA = (((START_Y + (EDGE * T) / 2) + (2 * T)) - (START_Y + (EDGE * T) / 2)) / 2; // buena tecnica, calcular distancia media en y

        body.getElements().add(new QuadCurveTo(START_X + (H / 8) + d * 1.6, (START_Y + (EDGE * T) / 2) - (midA / 6), START_X + (H / 8) + d, START_Y + (EDGE * T) / 2));// A
        body.getElements().add(new LineTo(START_X + (H / 8), START_Y + (EDGE * T) / 2));
        body.setFill(Color.BLUEVIOLET);

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(bottomNeckXLeft, bottomNeckYRight));
        neck2.getElements().add(new LineTo(bottomNeckXLeft - (T * EDGE), bottomNeckYLeft + (H / 8)));
        neck2.getElements().add(new LineTo(bottomNeckXRight + (T * EDGE), bottomNeckYRight + (H / 8)));
        neck2.getElements().add(new LineTo(bottomNeckXRight, bottomNeckYRight));
        neck2.getElements().add(new LineTo(bottomNeckXLeft, bottomNeckYRight));
        neck2.setFill(Color.BROWN);

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
