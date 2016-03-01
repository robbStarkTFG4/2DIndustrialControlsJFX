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
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

/**
 *
 * @author marcoisaac
 */
public class Compuerta extends Application {

    private static int noOfNodes = 0;
    private static final double START_X = 100;
    private static final double START_Y = 170;
    private static final double AV = 1;
    private static final double EDGE = AV - 1;
    private static final double W = 190; //witdh
    private static final double H = 159; //height
    private static final double T = 40; // pipe diameter

    ///
    private double angle[] = {2.35, 0.78};

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

        Path neck1 = new Path();//Bottom
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (T * AV), START_Y));
        neck1.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (EDGE * T), START_Y - (H / 8)));
        neck1.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));//A

        //Circle circle = new Circle(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8), 7, Color.RED);
        double offset = T * .2;
        double aX = START_X + (T * AV) - (EDGE * T);
        double aY = START_Y - (H / 8);
        double offsetCircle = T * .10;

        Path body = new Path();
        body.getElements().add(new MoveTo(aX, aY));
        body.getElements().add(new LineTo(aX - T, aY));//D
        body.getElements().add(new LineTo(aX - T + offset, aY - (H / 12)));//C
        body.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T) - offset, aY - (H / 12)));//B
        body.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));//A

        final double controlHeight = H / 14;

        double restanteY = H - (2 * ((H / 8))) - (2 * (H / 12)) - (2 * controlHeight);
        System.out.println("altura: " + restanteY);
        double bX = START_X + (T * AV) - (EDGE * T) - offset;
        double bY = aY - (H / 12);

        Path lowerNeck = new Path();
        lowerNeck.getElements().add(new MoveTo(bX, bY));//B
        lowerNeck.getElements().add(new LineTo(aX - T + offset, aY - (H / 12)));//C
        lowerNeck.getElements().add(new LineTo(aX - T + offset, bY - controlHeight));//G
        lowerNeck.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T) - offset, bY - controlHeight));//F
        lowerNeck.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T) - offset, aY - (H / 12)));//B

        double gX = aX - T + offset;
        double gY = bY - controlHeight;

        double distance = (bX - gX) / 2;
        double gateWidth = (1.5 * T) - distance;
        double twoBase = 2 * (H / 8);

        Path gateBody = new Path();
        gateBody.getElements().add(new MoveTo(gX, gY));
        gateBody.getElements().add(new LineTo(gX - gateWidth, gY));//1
        gateBody.getElements().add(new LineTo(gX - gateWidth, gY - twoBase));//2
        gateBody.getElements().add(new LineTo(gX, gY - twoBase));//G1
        gateBody.getElements().add(new LineTo(bX, gY - twoBase));//B1
        gateBody.getElements().add(new LineTo(bX + gateWidth, gY - twoBase));//3
        gateBody.getElements().add(new LineTo(bX + gateWidth, gY));//4
        gateBody.getElements().add(new LineTo(gX, gY));//GX

        Line midLine = new Line(gX - gateWidth, gY - (twoBase / 2), bX + gateWidth, gY - (twoBase / 2));

        Circle centerMid = new Circle(gX + distance, gY - (twoBase / 2), twoBase / 4);

        /// BEGIN state indicator
        Path indicatorBase = new Path();
        indicatorBase.getElements().add(new MoveTo(gX - gateWidth, gY - (4 * twoBase / 12)));//s1
        indicatorBase.getElements().add(new LineTo(gX - gateWidth, gY - (8 * twoBase / 12)));//s2
        indicatorBase.getElements().add(new LineTo(gX - gateWidth - distance * 0.7, gY - (8 * twoBase / 12)));//S2LEFT
        indicatorBase.getElements().add(new LineTo(gX - gateWidth - distance * 0.7, gY - (4 * twoBase / 12)));//S1LEFT
        indicatorBase.getElements().add(new LineTo(gX - gateWidth, gY - (4 * twoBase / 12)));//S1

        double baseX = gX - gateWidth - distance * 0.7;
        double baseY = (gY - (2 * twoBase / 8)) - (((gY - (2 * twoBase / 8)) - (gY - (6 * twoBase / 8))) / 2);

        Circle indi = new Circle(baseX - (T / 2), baseY, T / 2, Color.RED);

        //END state
        //System.out.println("radio: "+());
        double hX = aX - T + offset;
        double hY = gY - twoBase;

        Path upperNeck = new Path();
        upperNeck.getElements().add(new MoveTo(hX, hY));//H
        upperNeck.getElements().add(new LineTo(bX, hY));//I
        upperNeck.getElements().add(new LineTo(bX, hY - (H / 14)));//J
        upperNeck.getElements().add(new LineTo(hX, hY - (H / 14)));//K
        upperNeck.getElements().add(new LineTo(hX, hY));//H

        Path mangaNeck = new Path();
        mangaNeck.getElements().add(new MoveTo(hX, hY - (H / 14)));//K
        mangaNeck.getElements().add(new LineTo(bX, hY - (H / 14)));//J
        mangaNeck.getElements().add(new LineTo(aX, hY - (H / 14) - (H / 12)));//L

        double lX = aX;
        double lY = hY - (H / 14) - (H / 12);

        mangaNeck.getElements().add(new LineTo(aX - T, lY));//M
        mangaNeck.getElements().add(new LineTo(hX, hY - (H / 14)));//K

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(aX - T, lY));//M
        neck2.getElements().add(new LineTo(aX, lY));//L
        neck2.getElements().add(new LineTo(aX, lY - (H / 8)));
        neck2.getElements().add(new LineTo(aX - T, lY - (H / 8)));
        neck2.getElements().add(new LineTo(aX - T, lY));
        root.getChildren().addAll(neck1, body, lowerNeck, gateBody, midLine, centerMid, indicatorBase, indi, upperNeck, mangaNeck, neck2);

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
