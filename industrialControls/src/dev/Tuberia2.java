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
public class Tuberia2 extends Application {

    private static int noOfNodes = 0;
    private static final double START_X = 100;
    private static final double START_Y = 100;
    private static final double AV = 1.15;
    private static final double EDGE = AV - 1;
    private static final double W = 150; //witdh
    private static final double H = 150; //height
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
        neck1.getElements().addAll(new MoveTo(START_X, START_Y), new LineTo(START_X + (T * AV), START_Y));
        neck1.getElements().add(new QuadCurveTo(START_X + (T * AV), START_Y + (H / 8) / 2, START_X + T, START_Y + (H / 8))); // right curve
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new QuadCurveTo(START_X, START_Y + (H / 8) / 2, START_X + (1.10 * T * EDGE), START_Y + (H / 8)));
        neck1.getElements().add(new LineTo(START_X + T, START_Y + (H / 8)));
        neck1.setFill(Color.BROWN);

        Path body = new Path();
        body.getElements().add(new MoveTo(START_X + (1.10 * T * EDGE), START_Y + (H / 8)));//upperLeft clockWise

        body.getElements().add(new LineTo(START_X + T, START_Y + (H / 8)));

        body.getElements().add(new LineTo(START_X + T, START_Y + (H / 8) + (3 * H / 8)));

        double g = START_Y + (H / 8);

        body.getElements().add(new QuadCurveTo(START_X + 1.1 * T, START_Y + (H / 8) + (6.2 * H / 8), START_X + T * 2, g + 2 * (4 * H / 8) - T)); // M

        double b = START_X + T * 2;
        double restante = W - (b - START_X - (H / 8));
        System.out.println("restante: " + restante);

        body.getElements().add(new LineTo(START_X + T * 2 + restante, g + 2 * (4 * H / 8) - T));

        body.getElements().add(new LineTo(START_X + T * 2 + restante, g + 2 * (4 * H / 8)));// X
        body.getElements().add(new LineTo(START_X + T * 2, g + 2 * (4 * H / 8)));

        body.getElements().add(new QuadCurveTo(START_X + (1.5 * T * EDGE), g + (8 * H / 8), START_X + (1.10 * T * EDGE), g + (4 * H / 8)));// B
        body.getElements().add(new LineTo(START_X + (1.10 * T * EDGE), g));

        body.setFill(Color.PINK);
        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(START_X + T * 2 + restante, g + 2 * (4 * H / 8) - T));
        neck2.getElements().add(new LineTo(START_X + T * 2 + restante + (H / 8), g + 2 * (4 * H / 8) - T - (EDGE * T)));//ENDPOINT
        neck2.getElements().add(new LineTo(START_X + T * 2 + restante + (H / 8), g + 2 * (4 * H / 8) + (EDGE * T)));
        neck2.getElements().add(new LineTo(START_X + T * 2 + restante, g + 2 * (4 * H / 8)));
        neck2.getElements().add(new LineTo(START_X + T * 2 + restante, g + 2 * (4 * H / 8) - T));
        neck2.setFill(Color.AQUA);

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
