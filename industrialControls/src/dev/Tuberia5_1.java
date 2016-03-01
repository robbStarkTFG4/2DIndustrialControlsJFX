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
import javafx.stage.Stage;

/**
 *
 * @author marcoisaac
 */
public class Tuberia5_1 extends Application {

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
        neck1.getElements().add(new LineTo(START_X, START_Y + (T * AV)));
        neck1.getElements().add(new LineTo(START_X + (H / 8), START_Y + (T * AV) - (EDGE * T)));
        neck1.getElements().add(new MoveTo(START_X, START_Y));
        neck1.getElements().add(new LineTo(START_X + (H / 8), START_Y + (EDGE * T)));

        double q = START_X + (H / 8);
        double neck1EndY = START_Y + (T * AV) - (EDGE * T);

        double restanteX = W - (H / 8);

        neck1.getElements().add(new LineTo(q, neck1EndY));

        Path body = new Path();
        body.getElements().add(new MoveTo(q, neck1EndY));
        body.getElements().add(new LineTo(q, START_Y + (EDGE * T)));
        body.getElements().add(new LineTo(q + restanteX, START_Y + (EDGE * T)));//TOP END
        body.getElements().add(new MoveTo(q, neck1EndY));
        body.getElements().add(new LineTo(q + restanteX, neck1EndY));//BOTTOM END
        body.getElements().add(new LineTo(q + restanteX, START_Y + (EDGE * T)));

        Path neck2 = new Path();
        neck2.getElements().add(new MoveTo(q + restanteX, START_Y + (EDGE * T)));
        neck2.getElements().add(new LineTo(q + restanteX, neck1EndY));
        neck2.getElements().add(new LineTo(q + restanteX + (H / 8), neck1EndY + (EDGE * T)));
        neck2.getElements().add(new MoveTo(q + restanteX, START_Y + (EDGE * T)));
        neck2.getElements().add(new LineTo(q + restanteX + (H / 8), START_Y - (EDGE * T) + (EDGE * T)));
        neck2.getElements().add(new LineTo(q + restanteX + (H / 8), neck1EndY + (EDGE * T)));

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
