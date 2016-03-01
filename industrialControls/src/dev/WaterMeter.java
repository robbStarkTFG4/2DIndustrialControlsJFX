/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev;

import com.controls.util.Point;
import java.util.ArrayList;
import java.util.List;
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
public class WaterMeter extends Application {

    private static int noOfNodes = 0;
    private static final double START_X = 100;
    private static final double START_Y = 170;
    private static final double AV = 1;
    private static final double EDGE = AV - 1;
    private static final double W = 190; //witdh
    private static final double H = 150; //height
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

        double restanteY = H - (2 * ((H / 8))) - (2 * (H / 12)) - (2 * (H / 14));
        System.out.println("altura: " + restanteY);
        double bX = START_X + (T * AV) - (EDGE * T) - offset;
        double bY = aY - (H / 12);

        Path lowerNeck = new Path();
        lowerNeck.getElements().add(new MoveTo(bX, bY));//B
        lowerNeck.getElements().add(new LineTo(aX - T + offset, aY - (H / 12)));//C
        lowerNeck.getElements().add(new LineTo(aX - T + offset, bY - (H / 14)));//G
        lowerNeck.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T) - offset, bY - (H / 14)));//F
        lowerNeck.getElements().add(new LineTo(START_X + (T * AV) - (EDGE * T) - offset, aY - (H / 12)));//B

        double radio = (restanteY / 2);

        Circle center = new Circle(aX - (T / 2), bY - (H / 14) - (restanteY / 2), radio, null);
        center.setStroke(Color.BLACK);

        //System.out.println("radio: "+());
        double hX = aX - T + offset;
        double hY = center.getBoundsInParent().getMinY();

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

        Circle outerRing = new Circle(center.getCenterX(), center.getCenterY(), radio * 0.85, null);
        outerRing.setStroke(Color.BLACK);

        Line noFlow = new Line(center.getCenterX() - (radio * 0.5), center.getCenterY(), center.getCenterX() + (radio * 0.5), center.getCenterY());
        noFlow.setStrokeWidth(mangaNeck.getStrokeWidth() * 4);
        noFlow.setOpacity(0);

        flowSign(center, radio, root, mangaNeck);

      

        root.getChildren().addAll(neck1, body, neck2, lowerNeck, center, upperNeck, mangaNeck, noFlow, outerRing);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        calcNoOfNodes(scene.getRoot());
        System.out.println("number of nodes: " + noOfNodes);
    }

    private void flowSign(Circle center, double radio, Group root, Path mangaNeck) {
        List<Point> points = new ArrayList<>();
        
        double a = center.getCenterX();
        double b = center.getCenterY();
        double r = (radio * 0.5);

        for (double d : angle) {
            double x = a + r * Math.cos(d);
            double y = b + r * Math.sin(d);
            points.add(new Point(x, y));
        }

        Circle test = new Circle(a, b, 2);
        root.getChildren().add(test);

        List<Point> points1 = new ArrayList<>();
        double a1 = center.getCenterX();
        double b1 = center.getCenterY() - (2 * r / 4);

        for (double d : angle) {
            double x = a1 + r * Math.cos(d);
            double y = b1 + r * Math.sin(d);
            points1.add(new Point(x, y));
        }

        Circle test1 = new Circle(a1, b1, 2);
        root.getChildren().add(test1);

        for (Point w : points) {
            Path upperWing = new Path();
            upperWing.setStrokeWidth(mangaNeck.getStrokeWidth() * 4);
            upperWing.getElements().add(new MoveTo(w.getX(), w.getY()));
            upperWing.getElements().add(new LineTo(a, b));
            upperWing.getElements().add(new LineTo(w.getX(), w.getY()));
            root.getChildren().add(upperWing);
        }

        for (Point w : points1) {
            Path upperWing = new Path();
            upperWing.setStrokeWidth(mangaNeck.getStrokeWidth() * 4);
            upperWing.getElements().add(new MoveTo(w.getX(), w.getY()));
            upperWing.getElements().add(new LineTo(a1, b1));
            upperWing.getElements().add(new LineTo(w.getX(), w.getY()));
            root.getChildren().add(upperWing);
        }
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
