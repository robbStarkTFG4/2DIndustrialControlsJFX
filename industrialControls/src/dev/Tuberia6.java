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
public class Tuberia6 extends Application {

    private static int noOfNodes = 0;
    private static final double START_X = 100;
    private static final double START_Y = 170;
    private static final double AV = 1.15;
    private static final double EDGE = AV - 1;
    private static final double W = 150; //witdh
    private static final double H = 140; //height
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
        neck1.getElements().add(new LineTo(START_X + (T * AV), START_Y));
        neck1.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));//E
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (EDGE * T), START_Y - (H / 8)));//D
        neck1.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));

        Path body = new Path();
        body.getElements().add(new MoveTo(START_X + (EDGE * T), START_Y - (H / 8)));//D
        body.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));//E

        double restanteY = H - (H / 8) - T;
        double eX = START_X + (T * AV) - (EDGE * T);
        double dX = START_X + (EDGE * T);
        double fY = (START_Y - (H / 8)) - (restanteY / 2);
        double cY = fY;//eje y
        double midPointX = (eX - dX) / 2;
        double throwX = (W / 2) - midPointX - (H / 8);

        double gY = cY - (restanteY / 2);
        double gX = (throwX / 2) + eX;
        double h = (throwX / 2) + gX;
        double bX = dX - (throwX / 2);
        double aX = bX - (throwX / 2);

        body.getElements().add(new LineTo(eX, fY));//F
        double mid1 = (gY - fY) / 2;
        //fY - (gY / 4)
        body.getElements().add(new QuadCurveTo(eX, gY + (mid1 / 4), gX, gY));//G
        body.getElements().add(new LineTo(h, gY));//H
        body.getElements().add(new MoveTo(dX, START_Y - (H / 8)));//D
        body.getElements().add(new LineTo(dX, cY));//C
        //cY - (gY / 4)
        body.getElements().add(new QuadCurveTo(dX, gY + (mid1 / 4), bX, gY));//B
        body.getElements().add(new LineTo(aX, gY));//A
        body.getElements().add(new LineTo(aX, gY - T));//A1
        body.getElements().add(new LineTo(h, gY - T));//H1
        body.getElements().add(new LineTo(h, gY));//H

        Path neck2 = new Path();//topRight
        neck2.getElements().add(new MoveTo(h, gY));
        neck2.getElements().add(new LineTo(h, gY - T));
        neck2.getElements().add(new LineTo(h + (H / 8), gY - T - (EDGE * T)));//PP
        neck2.getElements().add(new MoveTo(h, gY));
        neck2.getElements().add(new LineTo(h + (H / 8), gY + (EDGE * T)));//ttt
        neck2.getElements().add(new LineTo(h + (H / 8), gY - T - (EDGE * T)));

        Path neck3 = new Path();//topLeft
        neck3.getElements().add(new MoveTo(aX, gY));//A
        neck3.getElements().add(new LineTo(aX, gY - T));//A1
        neck3.getElements().add(new LineTo(aX - (H / 8), gY - T - (EDGE * T)));
        neck3.getElements().add(new MoveTo(aX, gY));//A
        neck3.getElements().add(new LineTo(aX - (H / 8), gY + (EDGE * T)));//A
        neck3.getElements().add(new LineTo(aX - (H / 8), gY - T - (EDGE * T)));//A1

        root.getChildren().addAll(neck1, body, neck2, neck3);

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
