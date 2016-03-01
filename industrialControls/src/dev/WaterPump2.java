/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev;

import com.controls.util.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.util.Duration;

/**
 *
 * @author marcoisaac
 */
public class WaterPump2 extends Application {

    private static int noOfNodes = 0;
    private static final double START_X = 200;
    private static final double START_Y = 200;
    private static final double AV = 1.15;
    private static final double EDGE = AV - 1;
    private static final double W = 30; //witdh
    private static final double H = 30; //height
    private static final double T = 7; // pipe diameter

    //circle points 
    private double d = 150;
    private double a = START_X;//centerX
    private double b = START_Y;//centerY
    private double r = d / 2;
    private double angle[] = {1.57, 0.78, 0, 5.49, 4.71, 3.92, 3.14, 2.35};
    private List<Point> points = new ArrayList<>();
    private List<Path> pathList = new ArrayList<>();
    private Timeline time;
    private int i = 0;

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

        Circle core = new Circle(a, b, 2);
        root.getChildren().add(core);

        for (double d : angle) {
            double x = a + r * Math.cos(d);
            double y = b + r * Math.sin(d);
            points.add(new Point(x, y));
        }

        /*for (Point p : points) {
            Circle circle = new Circle(p.getX(), p.getY(), 2);
            root.getChildren().add(circle);
        }*/
        for (int i = 0; i < points.size(); i++) {
            Path body = new Path();
            body.getElements().add(new MoveTo(a, b));
            Point p1 = points.get(i);
            if (i != 7) {
                Point p2 = points.get(i + 1);
                //System.out.println("p1: " + p1.getX() + " p2: " + p2.getX());
                body.getElements().add(new LineTo(p1.getX(), p1.getY()));
                body.getElements().add(new LineTo(p2.getX(), p2.getY()));

                Point mid = new Point();
                mid.setX((p1.getX() + p2.getX()) / 2);
                mid.setY((p1.getY() + p2.getY()) / 2);
                //System.out.println("midY: " + midY);
                //body.getElements().add(new QuadCurveTo(mid.getX(), mid.getY(), p2.getX(), p2.getY()));
                body.getElements().add(new LineTo(a, b));

            } else {
                Point p2 = points.get(0);
                //System.out.println("p1: " + p1.getX() + " p2: " + p2.getX());
                body.getElements().add(new LineTo(p1.getX(), p1.getY()));
                body.getElements().add(new LineTo(p2.getX(), p2.getY()));
                double midY = p2.getY() - p1.getY();
                System.out.println("midY: " + midY);
                //body.getElements().add(new QuadCurveTo(p1.getX(), p2.getY(), p2.getX(), p2.getY()));
                body.getElements().add(new LineTo(a, b));

            }
            body.setFill(Color.GRAY);
            body.setStroke(Color.GRAY);

            /*if (i == 1) {
                Circle outerRing = new Circle(p1.getX(), p1.getY(), 7, Color.RED);
                root.getChildren().add(outerRing);
            }*/
            root.getChildren().add(body);
            pathList.add(body);
        }

        KeyFrame frame = new KeyFrame(Duration.millis(70), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (i != 0) {
                    Path old1 = pathList.get(i - 1);
                    old1.setFill(Color.GRAY);
                    Path old2 = pathList.get(i - 1 + 4);
                    old2.setFill(Color.GRAY);
                } else {
                    Path old1 = pathList.get(3);
                    old1.setFill(Color.GRAY);
                    Path old2 = pathList.get(7);
                    old2.setFill(Color.GRAY);
                }

                Path p1 = pathList.get(i);
                p1.setFill(Color.GREEN);
                Path p2 = pathList.get(i + 4);
                p2.setFill(Color.GREEN);
                i++;
                if (i > 3) {
                    i = 0;
                }
            }
        });
        time = new Timeline(frame);
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();

        Circle outer = new Circle(START_X, START_Y, 1.10 * (d / 2), Color.BLACK);
        outer.setStrokeWidth(2);
        outer.setFill(null);
        outer.setStroke(Color.BLUE);

        Path armor = new Path();
        armor.getElements().add(new MoveTo(START_X, START_Y));
        armor.getElements().add(new MoveTo(START_X - (1.25 * (d / 2)), START_Y));

        double h = (1.20 * (d / 2));

        armor.getElements().add(new LineTo(START_X - (1.25 * (d / 2)), START_Y - (3 * h / 4)));//A
        armor.getElements().add(new LineTo(START_X - (1.25 * (d / 2)) + (2 * h / 4), START_Y - h));//B
        armor.getElements().add(new LineTo(START_X - (1.25 * (d / 2)) + (2 * h / 4) + (3 * 2 * h / 4), START_Y - h));//C
        armor.getElements().add(new QuadCurveTo(START_X + 1.3 * h, START_Y, START_X - (1.25 * (d / 2)) + (2 * h / 4) + (3 * 2 * h / 4), START_Y + h));//B

        armor.getElements().add(new LineTo(START_X - (1.25 * (d / 2)) + (2 * h / 4), START_Y + h));//E
        armor.getElements().add(new LineTo(START_X - (1.25 * (d / 2)), START_Y + h - (h / 4)));
        armor.getElements().add(new LineTo(START_X - (1.25 * (d / 2)), START_Y));

        root.getChildren().addAll(armor, outer);

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
