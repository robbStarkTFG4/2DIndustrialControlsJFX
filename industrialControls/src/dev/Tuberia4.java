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
public class Tuberia4 extends Application {

    private static int noOfNodes = 0;
    private static final double START_X = 100;
    private static final double START_Y = 170;
    private static final double AV = 1.15;
    private static final double EDGE = AV - 1;
    private static final double W = 30; //witdh
    private static final double H = 30; //height
    private static final double T = 7; // pipe diameter

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
        neck1.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (EDGE * T), START_Y - (H / 8)));
        neck1.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), START_Y - (H / 8)));

        double E = START_X + (EDGE * T);
        double K = START_Y - (H / 8);
        double restanteY = H - (H / 8);

        Path body = new Path();
        body.getElements().add(new MoveTo(E, K));
        body.getElements().add(new LineTo(E, K - (3 * restanteY / 4)));

        double midLeft = (K - (3 * restanteY / 4)) - (K - (3 * restanteY / 4) - (restanteY / 4));

        body.getElements().add(new QuadCurveTo(E, K - (3 * restanteY / 4) - (2 * restanteY / 4), E + (3 * T), K - (3 * restanteY / 4) - (2 * restanteY / 4)));//J

        double restanteX = W - ((E + (3 * T)) - START_X - (H / 8));
        double c = (K - (3 * restanteY / 4) - (2 * restanteY / 4)) + T;
        System.out.println("restanteX: " + restanteX);

        body.getElements().add(new LineTo(E + (3 * T) + restanteX, K - (3 * restanteY / 4) - (2 * restanteY / 4)));

        double neck2StartX = E + (3 * T) + restanteX;
        double neck2StartY = K - (3 * restanteY / 4) - (2 * restanteY / 4);

        body.getElements().add(new MoveTo(E, K));
        body.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T), K));

        double ERight = START_X + (T * AV) - (EDGE * T);
        double f = K - (3 * restanteY / 4) * .75;

        double midRight = f - c;
        body.getElements().add(new LineTo(ERight, f));
        body.getElements().add(new QuadCurveTo(START_X + (T * AV), c, E + (3 * T), c));
        body.getElements().add(new LineTo(E + (3 * T) + restanteX, c));
        body.getElements().add(new LineTo(E + (3 * T) + restanteX, K - (3 * restanteY / 4) - (2 * restanteY / 4)));

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(neck2StartX, neck2StartY));
        neck2.getElements().add(new LineTo(neck2StartX, c));
        neck2.getElements().add(new LineTo(neck2StartX + (H / 8), c + (EDGE * T)));
        neck2.getElements().add(new MoveTo(neck2StartX, neck2StartY));
        neck2.getElements().add(new LineTo(neck2StartX + (H / 8), neck2StartY - (EDGE * T)));
        neck2.getElements().add(new LineTo(neck2StartX + (H / 8), c + (EDGE * T)));

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
